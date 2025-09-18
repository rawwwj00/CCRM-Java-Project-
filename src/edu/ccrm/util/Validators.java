package edu.ccrm.util;

public class Validators {
    public static boolean isEmail(String e){
        if (e == null) return false;
        e = e.trim();
        return e.contains("@") && e.indexOf('@') != 0 && e.indexOf('@') != e.length()-1;
    }
}
