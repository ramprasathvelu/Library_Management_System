import admin.*;
import db.*;
import borrower.*;
import java.util.*;
public class Main{
    static Scanner sc = new Scanner(System.in);
    public static void main(String args[]){
        Borrower b = new Borrower();
        Admin a = new Admin();
        while(true){
            System.out.println("Library");
            System.out.println("1. Admin"); 
            System.out.println("2. Borrower"); 
            System.out.println("3. Exit"); 
            int op;
            try {
                op = sc.nextInt();
                sc.nextLine();
            } 
            catch (Exception e) {
                System.out.println("Exception: "+e);
                continue;
            }
            if(op==1){
                System.out.println("Enter Admin Email ID: ");
                String email = sc.nextLine();
                System.out.println("Enter Admin Password: ");
                String pass = sc.nextLine();
                if(adminDB.adminInfo.containsKey(email) && adminDB.adminInfo.get(email).equals(pass))
                a.adminMain(adminDB.adminName.get(email));
            }
            else if (op==2) {
                b.borrowerMain();
            }
            else if (op==3) {
                System.out.println("----------------Thank You!----------------");
                break;
            }
            else{
                System.out.println("-------------Invalid Option, Try Again!-------------");
            }
        }
    }
}