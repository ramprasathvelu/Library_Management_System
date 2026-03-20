package db;
import java.util.*;
public class borrowerDB {
    public static HashMap<String,String> borrowerInfo = new HashMap<>();
    public static HashMap<String,String> borrowerName = new HashMap<>();
    static{
        borrowerInfo.put("ram@gamil.com","ram@007");
        borrowerName.put("ram@gamil.com", "RAMPRASATH");
    }
    public static void addBorrower(String email, String pass, String name){
        borrowerInfo.put(email,pass);
        borrowerName.put(email,name);
    }
}