package admin;
import db.*;
import java.util.*;
public class Admin {
    static Scanner sc = new Scanner(System.in);
    public void adminMain(String name){
        System.out.println("------------------Admin------------------");  
        while (true) { 
            System.out.println("\t \t Welcome "+name+" \t \t");
            System.out.println("1. View Books");
            System.out.println("2. Add new Books");
            System.out.println("3. Search Book");
            System.out.println("4. Add New Admin");
            System.out.println("5. Add New Student");
            System.out.println("6. Fine Details");
            System.out.println("7. Report");
            System.out.println("8. Exit");
            int op;
            try {
                op = sc.nextInt();
            } 
            catch (Exception e) {
                System.out.println("Invalid, Exception occured:"+e);
                continue;
            }
            if(op==1){
                System.out.println("Books Available in Library");
                bookDB.viewBooks();
            }
            else if(op==2){
                System.out.println("Enter the Book Name: ");
                String bookName = sc.nextLine();
                System.out.println("Enter the Book INBS No: ");
                String inbs = sc.nextLine();
                System.out.println("Enter the Book Quantity: ");
                int q = sc.nextInt();
                book b = new book(bookName,inbs,q);
                bookDB.addBook(b);
                System.out.println("Book added successfully!");
            }
            else if(op==3){
                int s;
                System.out.println("1. Search By Name");
                System.out.println("2. Search By ISBN");
                try {
                    s = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Exception occur "+e);
                }
                if(s==1){
                    System.out.println("Enter the Name of the Book");
                    bookDB.searchByISBN(sc.nextLine());
                }
                else if(s==2){
                    System.out.println("Enter the ISBN No of the Book");
                    bookDB.searchByName(sc.nextLine());
                }
                else{
                    System.out.println("Invalid Option");
                }
            }
            else if(op==4){
                System.out.println("---------------New Admin---------------");
                System.out.println("Enter Email ID: ");
                String email = sc.nextLine();
                System.out.println("Enter Password: ");
                String password = sc.nextLine();
                System.out.println("Enter New Admin Name: ");
                String userName = sc.nextLine();
                adminDB.addAdmin(email, password, userName);

            }
            else if(op==5){
                System.out.println("---------------New Student---------------");
                System.out.println("Enter Email ID: ");
                String email = sc.nextLine();
                System.out.println("Enter Password: ");
                String password = sc.nextLine();
                System.out.println("Enter New Student Name: ");
                String userName = sc.nextLine();
                adminDB.addAdmin(email, password, userName);
            }
            else if(op==6){
                System.out.println("progress");
            }
            else if(op==7){
                System.out.println("progress");
            }
            else if(op==8){
                System.out.println("Logged out from Admin: "+name);
                break;
            }
            else{
                System.out.println("Invalid Option");
            }
        }
    }
}
