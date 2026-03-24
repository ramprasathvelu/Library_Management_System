package db;

import java.util.*;

public class BorrowerAccount {
    public String name;
    public String email;
    public String password;
    public double deposit;         
    public double pendingFine;      
    public boolean cardLost;
    public List<FineRecord> fineHistory;

    public BorrowerAccount(String name, String email, String password) {
        this.name        = name;
        this.email       = email;
        this.password    = password;
        this.deposit     = 1500.0;
        this.pendingFine = 0.0;
        this.cardLost    = false;
        this.fineHistory = new ArrayList<>();
    }

    /** Add a fine to history and to pending amount */
    public void addFine(String reason, double amount) {
        pendingFine += amount;
        // record will be settled when paid
    }

    /** Pay pending fine by cash */
    public boolean payByCash() {
        if (pendingFine <= 0) {
            System.out.println("No pending fine.");
            return false;
        }
        fineHistory.add(new FineRecord("Fine settled (cash)", pendingFine,
            java.time.LocalDate.now(), true));
        System.out.printf("Fine of Rs %.2f paid by cash.%n", pendingFine);
        pendingFine = 0;
        return true;
    }

    /** Deduct pending fine from security deposit */
    public boolean payByDeposit() {
        if (pendingFine <= 0) {
            System.out.println("No pending fine.");
            return false;
        }
        if (deposit - pendingFine < 0) {
            System.out.printf("Insufficient deposit (%.2f). Fine is %.2f. Please pay by cash or top-up deposit.%n",
                deposit, pendingFine);
            return false;
        }
        deposit -= pendingFine;
        fineHistory.add(new FineRecord("Fine settled (deposit deduction)", pendingFine,
            java.time.LocalDate.now(), false));
        System.out.printf("Fine of Rs %.2f deducted from deposit. Remaining deposit: Rs %.2f%n",
            pendingFine, deposit);
        pendingFine = 0;
        return true;
    }

    public void displayStatus() {
        System.out.printf("Name: %-20s | Email: %-30s | Deposit: Rs %.2f | Pending Fine: Rs %.2f | Card Lost: %s%n",
            name, email, deposit, pendingFine, cardLost ? "Yes" : "No");
    }
}
