package db;

import java.time.LocalDate;

public class FineRecord {
    public String reason;
    public double amount;
    public LocalDate date;
    public boolean paidByCash;    // true = cash, false = deducted from deposit

    public FineRecord(String reason, double amount, LocalDate date, boolean paidByCash) {
        this.reason      = reason;
        this.amount      = amount;
        this.date        = date;
        this.paidByCash  = paidByCash;
    }

    public void display() {
        String mode = paidByCash ? "Cash" : "Deposit Deduction";
        System.out.printf("Date: %s | Reason: %-40s | Amount: %.2f | Paid via: %s%n",
            date, reason, amount, mode);
    }
}
