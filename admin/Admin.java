package admin;

import db.*;
import java.util.*;

public class Admin {
    static Scanner sc = new Scanner(System.in);

    public void adminMain(String name, String email) {
        System.out.println("------------------Admin------------------");
        while (true) {
            System.out.println("\n\t\t Welcome " + name + " \t\t");
            System.out.println("1.  View Books");
            System.out.println("2.  Add New Book");
            System.out.println("3.  Modify Book");
            System.out.println("4.  Delete Book");
            System.out.println("5.  Search Book");
            System.out.println("6.  Add New Admin");
            System.out.println("7.  Add New Student");
            System.out.println("8.  Manage Borrower Account / Fine");
            System.out.println("9.  Reports");
            System.out.println("10. Logout");
            System.out.print("Choose: ");
            int op;
            try { op = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); continue; }

            switch (op) {
                case 1  -> viewBooksMenu();
                case 2  -> addBook();
                case 3  -> modifyBook();
                case 4  -> deleteBook();
                case 5  -> searchBook();
                case 6  -> addAdmin();
                case 7  -> addStudent();
                case 8  -> manageBorrower();
                case 9  -> reportsMenu();
                case 10 -> { System.out.println("Logged out: " + name); return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // ---- 1. VIEW BOOKS ----
    private void viewBooksMenu() {
        System.out.println("1. View All (default order)");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by Available Quantity");
        System.out.print("Choose: ");
        int ch;
        try { ch = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
        System.out.println();
        if (ch == 2)      bookDB.viewSortedByName();
        else if (ch == 3) bookDB.viewSortedByAvailQty();
        else              bookDB.viewBooks();
    }

    // ---- 2. ADD BOOK ----
    private void addBook() {
        System.out.println("===== Add New Book =====");
        System.out.print("Book Name : "); String bookName = sc.nextLine();
        System.out.print("ISBN      : "); String isbn     = sc.nextLine();
        System.out.print("Author    : "); String author   = sc.nextLine();
        System.out.print("Quantity  : ");
        int qty; try { qty = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); System.out.println("Invalid qty."); return; }
        System.out.print("Price (Rs): ");
        double price; try { price = sc.nextDouble(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); System.out.println("Invalid price."); return; }
        bookDB.addBook(new book(bookName, isbn, qty, author, price));
        System.out.println("Book added successfully!");
    }

    // ---- 3. MODIFY BOOK ----
    private void modifyBook() {
        System.out.println("===== Modify Book =====");
        System.out.print("Enter ISBN to modify: "); String isbn = sc.nextLine();
        book b = bookDB.searchByISBN(isbn);
        if (b == null) { System.out.println("Book not found."); return; }
        b.display();
        System.out.print("New Name  (Enter to skip): "); String newName   = sc.nextLine();
        System.out.print("New Author(Enter to skip): "); String newAuthor = sc.nextLine();
        System.out.print("New Total Qty (-1 to skip): ");
        int newQty; try { newQty = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); newQty = -1; }
        System.out.print("New Price  (-1 to skip)  : ");
        double newPrice; try { newPrice = sc.nextDouble(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); newPrice = -1; }
        bookDB.modifyBook(isbn, newName.isBlank() ? null : newName,
                               newAuthor.isBlank() ? null : newAuthor, newQty, newPrice);
        System.out.println("Book updated.");
    }

    // ---- 4. DELETE BOOK ----
    private void deleteBook() {
        System.out.println("===== Delete Book =====");
        System.out.print("Enter ISBN: "); String isbn = sc.nextLine();
        if (bookDB.deleteBook(isbn)) System.out.println("Book deleted.");
        else System.out.println("Book not found or copies still on loan.");
    }

    // ---- 5. SEARCH BOOK ----
    private void searchBook() {
        System.out.println("1. Search by Name");
        System.out.println("2. Search by ISBN");
        System.out.print("Choose: ");
        int ch; try { ch = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
        book result = null;
        if (ch == 1) {
            System.out.print("Enter name: "); result = bookDB.searchByName(sc.nextLine());
        } else if (ch == 2) {
            System.out.print("Enter ISBN: "); result = bookDB.searchByISBN(sc.nextLine());
        }
        if (result != null) result.display();
        else System.out.println("Book not found.");
    }

    // ---- 6. ADD ADMIN ----
    private void addAdmin() {
        System.out.println("===== New Admin =====");
        System.out.print("Email   : "); String email = sc.nextLine();
        System.out.print("Password: "); String pass  = sc.nextLine();
        System.out.print("Name    : "); String aName = sc.nextLine();
        adminDB.addAdmin(email, pass, aName);
        System.out.println("Admin added.");
    }

    // ---- 7. ADD STUDENT ----
    private void addStudent() {
        System.out.println("===== New Student =====");
        System.out.print("Email   : "); String email = sc.nextLine();
        System.out.print("Password: "); String pass  = sc.nextLine();
        System.out.print("Name    : "); String sName = sc.nextLine();
        borrowerDB.addBorrower(email, pass, sName);
        System.out.println("Student added. Default deposit: Rs 1500.");
    }

    // ---- 8. MANAGE BORROWER ----
    private void manageBorrower() {
        System.out.println("===== Borrower Management =====");
        System.out.println("All Borrowers:");
        for (BorrowerAccount a : borrowerDB.getAllAccounts()) a.displayStatus();
        System.out.print("\nEnter borrower email to manage (blank to go back): ");
        String email = sc.nextLine().trim();
        if (email.isBlank()) return;
        BorrowerAccount acct = borrowerDB.getAccount(email);
        if (acct == null) { System.out.println("Not found."); return; }
        System.out.println("1. Top-up deposit");
        System.out.println("2. Clear pending fine (admin override)");
        System.out.println("3. View fine history");
        System.out.println("4. View active borrows");
        System.out.print("Choose: ");
        int ch; try { ch = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
        switch (ch) {
            case 1 -> {
                System.out.print("Amount to add: Rs "); double amt;
                try { amt = sc.nextDouble(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }
                acct.deposit += amt;
                System.out.printf("Deposit updated. New balance: Rs %.2f%n", acct.deposit);
            }
            case 2 -> { acct.pendingFine = 0; System.out.println("Fine cleared by admin."); }
            case 3 -> {
                if (acct.fineHistory.isEmpty()) System.out.println("No fine history.");
                else acct.fineHistory.forEach(FineRecord::display);
            }
            case 4 -> {
                List<BorrowRecord> active = BorrowDB.getActiveByEmail(email);
                if (active.isEmpty()) System.out.println("No active borrows.");
                else active.forEach(BorrowRecord::display);
            }
        }
    }

    // ---- 9. REPORTS ----
    private void reportsMenu() {
        System.out.println("\n===== Admin Reports =====");
        System.out.println("1. Books with Low Stock");
        System.out.println("2. Books Never Borrowed");
        System.out.println("3. Heavily Borrowed Books");
        System.out.println("4. Students with Outstanding Borrows (as of a date)");
        System.out.println("5. Book Status by ISBN");
        System.out.print("Choose: ");
        int ch; try { ch = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); return; }

        switch (ch) {
            case 1 -> {
                System.out.print("Low stock threshold (default 2): ");
                int thr; try { thr = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); thr = 2; }
                List<book> low = bookDB.getLowStockBooks(thr);
                System.out.println("\n--- Low Stock Books (avail <= " + thr + ") ---");
                if (low.isEmpty()) System.out.println("None.");
                else low.forEach(book::display);
            }
            case 2 -> {
                List<book> never = bookDB.getNeverBorrowedBooks();
                System.out.println("\n--- Books Never Borrowed ---");
                if (never.isEmpty()) System.out.println("All books have been borrowed at least once.");
                else never.forEach(book::display);
            }
            case 3 -> {
                System.out.print("Show top N books: "); int n;
                try { n = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); n = 5; }
                List<book> heavy = bookDB.getHeavilyBorrowedBooks(n);
                System.out.println("\n--- Top " + n + " Heavily Borrowed Books ---");
                heavy.forEach(book::display);
            }
            case 4 -> {
                System.out.print("Enter date (DD/MM/YYYY): ");
                String ds = sc.nextLine().trim();
                java.time.LocalDate asOf;
                try {
                    String[] p = ds.split("/");
                    asOf = java.time.LocalDate.of(Integer.parseInt(p[2]), Integer.parseInt(p[1]), Integer.parseInt(p[0]));
                } catch (Exception e) { System.out.println("Invalid date."); return; }
                System.out.println("\n--- Students with Outstanding Borrows as of " + asOf + " ---");
                boolean found = false;
                for (BorrowRecord r : BorrowDB.getAllActive()) {
                    if (!r.borrowDate.isAfter(asOf)) {
                        BorrowerAccount acct = borrowerDB.getAccount(r.borrowerEmail);
                        book bk = bookDB.searchByISBN(r.bookISBN);
                        String bName = (acct != null) ? acct.name : r.borrowerEmail;
                        String bkName = (bk != null) ? bk.bookName : r.bookISBN;
                        System.out.printf("Student: %-20s | Book: %-30s | Borrowed: %s | Due: %s%n",
                            bName, bkName, r.borrowDate, r.dueDate);
                        found = true;
                    }
                }
                if (!found) System.out.println("No outstanding borrows as of that date.");
            }
            case 5 -> {
                System.out.print("Enter ISBN: "); String isbn = sc.nextLine();
                book bk = bookDB.searchByISBN(isbn);
                if (bk == null) { System.out.println("Book not found."); return; }
                System.out.println("\n--- Book Status ---");
                bk.display();
                BorrowRecord active = BorrowDB.getActiveByISBN(isbn);
                if (active == null) {
                    System.out.println("Status: Available on shelf.");
                } else {
                    BorrowerAccount acct = borrowerDB.getAccount(active.borrowerEmail);
                    String sName = (acct != null) ? acct.name : active.borrowerEmail;
                    System.out.println("Status: Currently on loan.");
                    System.out.println("Borrower  : " + sName + " (" + active.borrowerEmail + ")");
                    System.out.println("Borrowed  : " + active.borrowDate);
                    System.out.println("Expected Return: " + active.dueDate);
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }
}
