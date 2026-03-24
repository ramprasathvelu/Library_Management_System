package db;

import java.util.*;

public class borrowerDB {
    public static HashMap<String, String> borrowerInfo = new HashMap<>();
    public static HashMap<String, BorrowerAccount> accounts = new HashMap<>();
    public static HashMap<String, String> borrowerName = new HashMap<>();
    static {
        addBorrower("ram@gmail.com", "ram@007", "RAMPRASATH");
        addBorrower("prasath@gmail.com", "prasath@007",  "PRASATH");
    }

    public static void addBorrower(String email, String pass, String name) {
        borrowerInfo.put(email, pass);
        borrowerName.put(email, name);
        accounts.put(email, new BorrowerAccount(name, email, pass));
    }

    public static BorrowerAccount getAccount(String email) {
        return accounts.get(email);
    }

    public static Collection<BorrowerAccount> getAllAccounts() {
        return accounts.values();
    }
}