package db;

import java.util.*;

public class bookDB {
    public static List<book> bookList = new ArrayList<>();
    public static HashMap<String, book> isbnMap = new HashMap<>();
    public static HashMap<String, book> nameMap = new HashMap<>();

    static {
        addBook(new book("Java Basics",           "ISBN101", 10, "James Gosling",    350.00));
        addBook(new book("Atomic Habits",          "ISBN102",  5, "James Clear",      499.00));
        addBook(new book("Digital Electronics",    "ISBN103",  7, "Morris Mano",      599.00));
        addBook(new book("Data Structures in Java","ISBN104",  4, "Robert Lafore",    749.00));
        addBook(new book("Clean Code",             "ISBN105",  3, "Robert C. Martin", 699.00));
        addBook(new book("Design Patterns",        "ISBN106",  2, "Gang of Four",     849.00));
        addBook(new book("The Pragmatic Programmer","ISBN107", 6, "David Thomas",     599.00));
    }

    public static void addBook(book b) {
        bookList.add(b);
        isbnMap.put(b.isbn.toUpperCase(), b);
        nameMap.put(b.bookName.toLowerCase(), b);
    }

    public static void viewBooks() {
        if (bookList.isEmpty()) { System.out.println("No books in library."); return; }
        System.out.printf("%-30s | %-10s | %-20s | %8s | %5s | %5s | %s%n",
            "Book Name","ISBN","Author","Price","Avail","Total","Borrowed");
        System.out.println("-".repeat(105));
        for (book b : bookList) b.display();
    }

    /** View books sorted by name (ascending) */
    public static void viewSortedByName() {
        List<book> sorted = new ArrayList<>(bookList);
        sorted.sort(Comparator.comparing(b -> b.bookName.toLowerCase()));
        System.out.printf("%-30s | %-10s | %-20s | %8s | %5s%n","Book Name","ISBN","Author","Price","Avail");
        System.out.println("-".repeat(85));
        for (book b : sorted) b.display();
    }

    /** View books sorted by available quantity (ascending) */
    public static void viewSortedByAvailQty() {
        List<book> sorted = new ArrayList<>(bookList);
        sorted.sort(Comparator.comparingInt(b -> b.availableQty));
        System.out.printf("%-30s | %-10s | %-20s | %8s | %5s%n","Book Name","ISBN","Author","Price","Avail");
        System.out.println("-".repeat(85));
        for (book b : sorted) b.display();
    }

    public static book searchByISBN(String isbn) {
        return isbnMap.get(isbn.toUpperCase());
    }

    public static book searchByName(String name) {
        // exact match first, then partial
        book exact = nameMap.get(name.toLowerCase());
        if (exact != null) return exact;
        for (book b : bookList)
            if (b.bookName.toLowerCase().contains(name.toLowerCase())) return b;
        return null;
    }

    /** Modify book details: pass null / -1 to keep existing values */
    public static boolean modifyBook(String isbn, String newName, String newAuthor,
                                     int newTotalQty, double newPrice) {
        book b = searchByISBN(isbn);
        if (b == null) return false;
        if (newName != null && !newName.isBlank()) {
            nameMap.remove(b.bookName.toLowerCase());
            b.bookName = newName;
            nameMap.put(newName.toLowerCase(), b);
        }
        if (newAuthor != null && !newAuthor.isBlank())  b.author = newAuthor;
        if (newTotalQty > 0) {
            int diff = newTotalQty - b.quantity;
            b.quantity    = newTotalQty;
            b.availableQty = Math.max(0, b.availableQty + diff);
        }
        if (newPrice > 0) b.price = newPrice;
        return true;
    }

    /** Delete a book (only if availableQty == quantity, i.e., none on loan) */
    public static boolean deleteBook(String isbn) {
        book b = searchByISBN(isbn);
        if (b == null) return false;
        if (b.availableQty < b.quantity) {
            System.out.println("Cannot delete – some copies are currently on loan.");
            return false;
        }
        bookList.remove(b);
        isbnMap.remove(isbn.toUpperCase());
        nameMap.remove(b.bookName.toLowerCase());
        return true;
    }

    /** Called when a copy goes out on loan */
    public static void decrementAvailable(String isbn) {
        book b = searchByISBN(isbn);
        if (b != null && b.availableQty > 0) {
            b.availableQty--;
            b.totalBorrowed++;
        }
    }

    /** Called when a copy is returned */
    public static void incrementAvailable(String isbn) {
        book b = searchByISBN(isbn);
        if (b != null) b.availableQty = Math.min(b.quantity, b.availableQty + 1);
    }

    /** Books with available qty <= threshold */
    public static List<book> getLowStockBooks(int threshold) {
        List<book> result = new ArrayList<>();
        for (book b : bookList) if (b.availableQty <= threshold) result.add(b);
        return result;
    }

    /** Books never borrowed (totalBorrowed == 0) */
    public static List<book> getNeverBorrowedBooks() {
        List<book> result = new ArrayList<>();
        for (book b : bookList) if (b.totalBorrowed == 0) result.add(b);
        return result;
    }

    /** Books sorted by totalBorrowed descending (top N) */
    public static List<book> getHeavilyBorrowedBooks(int topN) {
        List<book> sorted = new ArrayList<>(bookList);
        sorted.sort((a, b2) -> Integer.compare(b2.totalBorrowed, a.totalBorrowed));
        return sorted.subList(0, Math.min(topN, sorted.size()));
    }
}