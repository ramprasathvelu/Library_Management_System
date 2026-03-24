package borrower;

import db.*;
import java.util.*;

public class Borrower {
    static Scanner sc = new Scanner(System.in);

    public void borrowerMain(String name, String email) {
        System.out.println("------------------Student------------------");
        while (true) {
            BorrowerAccount acct = borrowerDB.getAccount(email);
            System.out.println("\n\t\t Welcome " + name + " \t\t");
            if (acct != null && acct.pendingFine > 0)
                System.out.printf("  [!] You have a pending fine of Rs %.2f%n", acct.pendingFine);
            System.out.println("1. View All Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Manage Borrowed Books (Return / Extend / Exchange / Lost)");
            System.out.println("4. View My Fine History");
            System.out.println("5. View My Borrow History");
            System.out.println("6. Account Status");
            System.out.println("7. Pay Pending Fine");
            System.out.println("8. Logout");
            System.out.print("Choose: ");
            int op;
            try { op = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); continue; }

            switch (op) {
                case 1 -> {
                    System.out.println("\n===== Books Available in Library =====");
                    bookDB.viewBooks();
                }
                case 2 -> BorrowBook.borrowFlow(email);
                case 3 -> BorrowBook.manageActiveBorrows(email);
                case 4 -> viewFineHistory(email);
                case 5 -> viewBorrowHistory(email);
                case 6 -> {
                    if (acct != null) {
                        System.out.println("\n===== Account Status =====");
                        acct.displayStatus();
                        List<BorrowRecord> active = BorrowDB.getActiveByEmail(email);
                        System.out.println("Active Borrows: " + active.size());
                        active.forEach(BorrowRecord::display);
                    }
                }
                case 7 -> {
                    if (acct != null) BorrowBook.handleFinePayment(acct);
                }
                case 8 -> {
                    System.out.println("Logged out: " + name);
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewFineHistory(String email) {
        BorrowerAccount acct = borrowerDB.getAccount(email);
        if (acct == null) return;
        System.out.println("\n===== Fine History =====");
        if (acct.fineHistory.isEmpty()) {
            System.out.println("No fines recorded. Keep it up!");
        } else {
            acct.fineHistory.forEach(FineRecord::display);
        }
        if (acct.pendingFine > 0)
            System.out.printf("%nTotal Pending Fine: Rs %.2f%n", acct.pendingFine);
    }

    private void viewBorrowHistory(String email) {
        List<BorrowRecord> history = BorrowDB.getHistoryByEmail(email);
        System.out.println("\n===== Borrow History =====");
        if (history.isEmpty()) {
            System.out.println("No borrow history found.");
        } else {
            history.forEach(BorrowRecord::display);
        }
    }
}
