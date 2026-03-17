package db;
import java.util.*;
public class adminDB {
    static HashMap<String,String> adminInfo = new HashMap<>();
    static HashMap<String,String> adminName = new HashMap<>();
    static{
        adminInfo.put("velu@gmail.com","velu007");
        adminName.put("velu@gmail.com","VELU");
    }
    public static void addAdmin(String email, String pass, String name){
        adminInfo.put(email,pass);
        adminName.put(email,name);
    }
}
