import admin.*;
import borrower.*;
import db.*;
import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Borrower b = new Borrower();
        Admin a = new Admin();
        System.out.println("=========================================");
        System.out.println("     WELCOME TO THE LIBRARY SYSTEM       ");
        System.out.println("=========================================");
        while (true) {
            System.out.println("\n  1. Admin Login");
            System.out.println("  2. Student Login");
            System.out.println("  3. Exit");
            System.out.print("  Choose: ");
            int op;
            try { op = sc.nextInt(); sc.nextLine(); } catch (Exception e) { sc.nextLine(); continue; }

            if (op == 1) {
                System.out.print("Email   : "); String email = sc.nextLine();
                System.out.print("Password: "); String pass  = sc.nextLine();
                if (adminDB.adminInfo.containsKey(email) && adminDB.adminInfo.get(email).equals(pass)) {
                    a.adminMain(adminDB.adminName.get(email), email);
                } else {
                    System.out.println("Invalid credentials. Try again.");
                }

            } else if (op == 2) {
                System.out.print("Email   : "); String email = sc.nextLine();
                System.out.print("Password: "); String pass  = sc.nextLine();
                if (borrowerDB.borrowerInfo.containsKey(email) && borrowerDB.borrowerInfo.get(email).equals(pass)) {
                    b.borrowerMain(borrowerDB.borrowerName.get(email), email);
                } else {
                    System.out.println("Invalid credentials. Try again.");
                }

            } else if (op == 3) {
                System.out.println("\n----- Thank you for visiting the Library! -----");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}