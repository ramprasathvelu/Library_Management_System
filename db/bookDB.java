package db;
import java.util.*;
public class bookDB {
    static List<book> bookList = new ArrayList<>();
    static HashMap<String,book> isbnMap = new HashMap<>();
    static HashMap<String,book> nameMap = new HashMap<>(); 
    static{
        addBook(new book("Java Basics", "ISBN101", 10));
        addBook(new book("Atomic Habits", "ISBN102", 5));
        addBook(new book("Digital Electronics", "ISBN103", 7));
    }
    public static void addBook(book b){
        bookList.add(b);
        isbnMap.put(b.isbn,b);
        nameMap.put(b.bookName,b);
    }
    public static void viewBooks(){
        for(book b : bookList){
            b.display();
        }
    }
    public static book searchByISBN(String isbn){
        return isbnMap.get(isbn);
    }
    public static book searchByName(String name){
        return nameMap.get(name);
    }
}