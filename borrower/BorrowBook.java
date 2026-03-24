package borrower;

import db.*;
import java.util.*;

public class BorrowBook {
    private static Scanner sc = new Scanner(System.in);
    public static void borrowFlow(String email) {
        BorrowerAccount acct = borrowerDB.getAccount(email);
        if (acct == null) { System.out.println("Account not found."); return; }

        // Security deposit check
        if (acct.deposit < 500) {
            System.out.printf("Insufficient security deposit (Rs %.2f). Minimum Rs 500 required to borrow.%n",
                acct.deposit);
            return;
        }

        List<book> cart = new ArrayList<>();
        System.out.println("\n========= Borrow Books (max 3) =========");

        while (true) {
            System.out.println("\nCart size: " + (BorrowDB.activeCount(email) + cart.size()) + "/3");
            System.out.println("1. Search by Name");
            System.out.println("2. Search by ISBN");
            System.out.println("3. Remove from cart");
            System.out.println("4. View cart");
            System.out.println("5. Checkout");
            System.out.println("6. Cancel");
            System.out.print("Choose: ");
            int choice;
            try { choice = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); continue; }

            if (choice == 1 || choice == 2) {
                String key;
                book found;
                if (choice == 1) {
                    System.out.print("Enter book name: ");
                    key = sc.nextLine();
                    found = bookDB.searchByName(key);
                } else {
                    System.out.print("Enter ISBN: ");
                    key = sc.nextLine();
                    found = bookDB.searchByISBN(key);
                }
                if (found == null) { System.out.println("Book not found."); continue; }
                addToCart(cart, found, email);

            } else if (choice == 3) {
                if (cart.isEmpty()) { System.out.println("Cart is empty."); continue; }
                System.out.println("Enter ISBN to remove from cart:");
                String rem = sc.nextLine();
                cart.removeIf(b -> b.isbn.equalsIgnoreCase(rem));
                System.out.println("Removed (if it was in cart).");

            } else if (choice == 4) {
                if (cart.isEmpty()) { System.out.println("Cart is empty."); }
                else { System.out.println("--- Cart ---"); cart.forEach(book::displayShort); }

            } else if (choice == 5) {
                checkout(cart, email, acct);
                break;

            } else if (choice == 6) {
                System.out.println("Borrow cancelled.");
                break;
            }
        }
    }

    private static void addToCart(List<book> cart, book found, String email) {
        int totalBooks = BorrowDB.activeCount(email) + cart.size();
        if (totalBooks >= 3) {
            System.out.println("You already have 3 books. Cannot add more."); return;
        }
        if (BorrowDB.hasBorrowed(email, found.isbn)) {
            System.out.println("You already have an active loan for this book."); return;
        }
        for (book c : cart) {
            if (c.isbn.equalsIgnoreCase(found.isbn)) {
                System.out.println("Book already in cart."); return;
            }
        }
        if (found.availableQty <= 0) {
            System.out.println("Sorry, no copies available right now."); return;
        }
        cart.add(found);
        System.out.println("'" + found.bookName + "' added to cart.");
    }

    private static void checkout(List<book> cart, String email, BorrowerAccount acct) {
        if (cart.isEmpty()) { System.out.println("Cart is empty. Nothing to checkout."); return; }
        java.time.LocalDate today = java.time.LocalDate.now();
        for (book b : cart) {
            BorrowRecord rec = new BorrowRecord(email, b.isbn, today);
            BorrowDB.addRecord(rec);
            bookDB.decrementAvailable(b.isbn);
        }
        System.out.println("\n===== Checkout Successful =====");
        System.out.printf("Borrowed %d book(s). Due date: %s%n", cart.size(), today.plusDays(15));
        cart.forEach(b -> System.out.println("  - " + b.bookName + " (" + b.isbn + ")"));
    }

    public static void manageActiveBorrows(String email) {
        List<BorrowRecord> active = BorrowDB.getActiveByEmail(email);
        if (active.isEmpty()) { System.out.println("You have no active borrows."); return; }

        System.out.println("\n===== Your Borrowed Books =====");
        for (int i = 0; i < active.size(); i++) {
            System.out.print((i + 1) + ". ");
            active.get(i).display();
        }
        System.out.println("\nSelect a book to act on (0 to go back):");
        int sel;
        try { sel = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
        if (sel <= 0 || sel > active.size()) return;

        BorrowRecord rec = active.get(sel - 1);
        book bk = bookDB.searchByISBN(rec.bookISBN);
        BorrowerAccount acct = borrowerDB.getAccount(email);

        System.out.println("\n1. Return Book");
        System.out.println("2. Extend Tenure");
        System.out.println("3. Exchange Book");
        System.out.println("4. Mark Book as Lost");
        System.out.println("5. Report Card Lost");
        System.out.println("6. Back");
        System.out.print("Choose: ");
        int action;
        try { action = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }

        switch (action) {
            case 1 -> returnBook(rec, bk, acct);
            case 2 -> extendTenure(rec, acct);
            case 3 -> exchangeBook(rec, bk, acct, email);
            case 4 -> markLost(rec, bk, acct);
            case 5 -> reportCardLost(acct);
            default -> System.out.println("Back.");
        }
    }

    // ----------- RETURN -----------
    private static void returnBook(BorrowRecord rec, book bk, BorrowerAccount acct) {
        System.out.print("Enter return date (DD/MM/YYYY): ");
        String dateStr = sc.nextLine().trim();
        java.time.LocalDate returnDate;
        try {
            String[] parts = dateStr.split("/");
            returnDate = java.time.LocalDate.of(
                Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
        } catch (Exception e) {
            System.out.println("Invalid date format. Using today's date.");
            returnDate = java.time.LocalDate.now();
        }

        double fine = calculateFine(rec, bk, returnDate);
        rec.returned = true;
        bookDB.incrementAvailable(rec.bookISBN);

        if (fine > 0) {
            System.out.printf("Late return fine: Rs %.2f%n", fine);
            acct.pendingFine += fine;
            acct.fineHistory.add(new FineRecord(
                "Late return of '" + bk.bookName + "'", fine, returnDate, false));
            handleFinePayment(acct);
        } else {
            System.out.println("Book returned on time. No fine. Thank you!");
        }
    }

    /** Fine: 2 Rs/day after 15 days; doubles every 10 extra days; cap at 80% of book price */
    public static double calculateFine(BorrowRecord rec, book bk, java.time.LocalDate returnDate) {
        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(rec.dueDate, returnDate);
        if (daysOverdue <= 0) return 0;

        // Exponential: 2 * 2^(floor(daysOverdue/10)) per day
        int bands = (int)(daysOverdue / 10);
        double ratePerDay = 2.0 * Math.pow(2, bands);
        double fine = ratePerDay * daysOverdue;

        double cap = 0.8 * bk.price;
        return Math.min(fine, cap);
    }

    // --------- EXTEND TENURE ---------
    private static void extendTenure(BorrowRecord rec, BorrowerAccount acct) {
        if (!rec.extend()) {
            System.out.println("Maximum 2 extensions already used for this book. Cannot extend further.");
        } else {
            System.out.println("Tenure extended. New due date: " + rec.dueDate);
            System.out.println("Extensions used: " + rec.extensionCount + "/2");
        }
    }

    // --------- EXCHANGE BOOK ---------
    private static void exchangeBook(BorrowRecord rec, book bk, BorrowerAccount acct, String email) {
        System.out.println("Exchange: returning '" + bk.bookName + "' and borrowing a new one.");
        returnBook(rec, bk, acct);   // first return current book
        // then open borrow flow for one new book
        List<book> cart = new ArrayList<>();
        System.out.print("Enter ISBN of new book to borrow: ");
        String isbn = sc.nextLine();
        book newBook = bookDB.searchByISBN(isbn);
        if (newBook == null) { System.out.println("Book not found."); return; }
        addToCart(cart, newBook, email);
        checkout(cart, email, acct);
    }

    // --------- LOST BOOK ---------
    private static void markLost(BorrowRecord rec, book bk, BorrowerAccount acct) {
        double fine = 0.5 * bk.price;
        rec.returned = true;
        rec.lostFlag = true;
        // Don't increment available – copy is gone
        System.out.printf("Book marked as lost. Fine: 50%% of Rs %.2f = Rs %.2f%n", bk.price, fine);
        acct.pendingFine += fine;
        acct.fineHistory.add(new FineRecord(
            "Lost book '" + bk.bookName + "'", fine, java.time.LocalDate.now(), false));
        handleFinePayment(acct);
    }

    // --------- CARD LOST ---------
    private static void reportCardLost(BorrowerAccount acct) {
        double fine = 10.0;
        acct.cardLost = true;
        acct.pendingFine += fine;
        acct.fineHistory.add(new FineRecord("Membership card lost", fine, java.time.LocalDate.now(), false));
        System.out.printf("Membership card loss reported. Fine: Rs %.2f%n", fine);
        handleFinePayment(acct);
    }

    // --------- FINE PAYMENT ---------
    public static void handleFinePayment(BorrowerAccount acct) {
        if (acct.pendingFine <= 0) return;
        System.out.printf("\nTotal pending fine: Rs %.2f%n", acct.pendingFine);
        System.out.println("Pay by: 1. Cash  2. Deduct from Security Deposit  3. Pay Later");
        System.out.print("Choose: ");
        int ch;
        try { ch = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
        if (ch == 1)      acct.payByCash();
        else if (ch == 2) acct.payByDeposit();
        else System.out.println("Fine will remain pending. Please settle before closing the account.");
    }
}
