package db;

public class book{
    String bookName;
    String isbn;
    int quantity;

    public book(String bookName, String isbn, int quantity){
        this.bookName = bookName;
        this.isbn = isbn;
        this.quantity = quantity;
    }
    public void display() {
        System.out.println(bookName + " | " + isbn + " | Qty: " + quantity);
    }
}
