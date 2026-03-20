package borrower;
import db.*;
import java.util.*;
public class Borrower {
    static Scanner sc = new Scanner(System.in);
    public void borrowerMain(String name){
        borrowerDB b = new borrowerDB();
        adminDB a = new adminDB(); 
        System.out.println("------------------Student------------------");  
        while (true) { 
            System.out.println("\t \t Welcome "+name+" \t \t");
            System.out.println("1. View All Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. View My Borrowed Books");
            System.out.println("4. View My Fines");
            System.out.println("5. Logout from Student");
            int op;
            try {
                op = sc.nextInt();
                sc.nextLine();
            } 
            catch (Exception e) {
                System.out.println("Invalid, Exception occured:"+e);
                continue;
            }
            if(op==1){
                System.out.println("Books Available in Library");
                bookDB.viewBooks();
            }
            else if (op==2){
                
            }
            else if(op==3){

            }
            else if(op==4){

            }
            else if(op==5){
                System.out.println("Logged out from Student: "+name);
                break;
            }
            else{
                System.out.println("Invalid Option");
            }
        }
    }
}
