package db;

import java.time.LocalDate;

public class BorrowRecord {
    public String borrowerEmail;
    public String bookISBN;
    public LocalDate borrowDate;
    public LocalDate dueDate;       // initially borrowDate + 15 days
    public int extensionCount;      // max 2
    public boolean returned;
    public boolean lostFlag;

    public BorrowRecord(String borrowerEmail, String bookISBN, LocalDate borrowDate) {
        this.borrowerEmail  = borrowerEmail;
        this.bookISBN       = bookISBN;
        this.borrowDate     = borrowDate;
        this.dueDate        = borrowDate.plusDays(15);
        this.extensionCount = 0;
        this.returned       = false;
        this.lostFlag       = false;
    }

    /** Extend tenure by 15 more days. Returns false if already extended twice. */
    public boolean extend() {
        if (extensionCount >= 2) return false;
        dueDate = dueDate.plusDays(15);
        extensionCount++;
        return true;
    }

    public void display() {
        book b = bookDB.searchByISBN(bookISBN);
        String bookTitle = (b != null) ? b.bookName : bookISBN;
        String status = returned ? (lostFlag ? "LOST" : "Returned") : "Active";
        System.out.printf("Book: %-30s | ISBN: %-10s | Borrowed: %s | Due: %s | Extensions: %d | Status: %s%n",
            bookTitle, bookISBN, borrowDate, dueDate, extensionCount, status);
    }
}
