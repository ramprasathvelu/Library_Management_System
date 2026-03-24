package db;

public class book {
    public String bookName;
    public String isbn;
    public int quantity;      
    public int availableQty;    
    public String author;
    public double price;
    public int totalBorrowed;   

    public book(String bookName, String isbn, int quantity, String author, double price) {
        this.bookName    = bookName;
        this.isbn        = isbn;
        this.quantity    = quantity;
        this.availableQty = quantity;
        this.author      = author;
        this.price       = price;
        this.totalBorrowed = 0;
    }

    public void display() {
        System.out.printf("%-30s | %-10s | Author: %-20s | Price: %6.2f | Avail: %d/%d | Borrowed: %d%n",
            bookName, isbn, author, price, availableQty, quantity, totalBorrowed);
    }

    public void displayShort() {
        System.out.printf("%-30s | %-10s | Avail: %d | Price: %.2f%n",
            bookName, isbn, availableQty, price);
    }
}
