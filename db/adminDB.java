package db;
import java.util.*;
public class adminDB {
    public static HashMap<String,String> adminInfo = new HashMap<>();
    public static HashMap<String,String> adminName = new HashMap<>();
    static{
        adminInfo.put("velu@gmail.com","velu007");
        adminName.put("velu@gmail.com","VELU");
    }
    public void addAdmin(String email, String pass, String name){
        adminInfo.put(email,pass);
        adminName.put(email,name);
    }
}
