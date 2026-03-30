package db;

import java.util.*;

public class BorrowDB {
    public static List<BorrowRecord> allRecords = new ArrayList<>();

    public static void addRecord(BorrowRecord r) {
        allRecords.add(r);
    }

    public static List<BorrowRecord> getActiveByEmail(String email) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : allRecords)
            if (r.borrowerEmail.equalsIgnoreCase(email) && !r.returned)
                result.add(r);
        return result;
    }

    public static List<BorrowRecord> getHistoryByEmail(String email) {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : allRecords)
            if (r.borrowerEmail.equalsIgnoreCase(email))
                result.add(r);
        return result;
    }
    

    public static BorrowRecord getActiveByISBN(String isbn) {
        for (BorrowRecord r : allRecords)
            if (r.bookISBN.equalsIgnoreCase(isbn) && !r.returned)
                return r;
        return null;
    }

    public static List<BorrowRecord> getAllActive() {
        List<BorrowRecord> result = new ArrayList<>();
        for (BorrowRecord r : allRecords)
            if (!r.returned)
                result.add(r);
        return result;
    }

    public static int activeCount(String email) {
        return getActiveByEmail(email).size();
    }

    public static boolean hasBorrowed(String email, String isbn) {
        for (BorrowRecord r : allRecords)
            if (r.borrowerEmail.equalsIgnoreCase(email)
                && r.bookISBN.equalsIgnoreCase(isbn)
                && !r.returned)
                return true;
        return false;
    }
}
