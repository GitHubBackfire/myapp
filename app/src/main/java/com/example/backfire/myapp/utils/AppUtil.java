package com.example.backfire.myapp.utils;

/**
 * Created by backfire on 2018/4/23.
 */

public class AppUtil {
    public static boolean isTextEmpty(String chkStr) {
        if (chkStr == null) {
            return true;
        } else {
            return "".equals(chkStr.trim()) ? true : false;
        }
    }
}
