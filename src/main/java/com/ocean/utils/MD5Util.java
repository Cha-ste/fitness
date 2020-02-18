package com.ocean.utils;


import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    public static final String SALT = "1o9c9e4an";

    public static String inputPass2DBPass(String inputPass, String salt) {
        String formPass = inputPass2FormPass(inputPass);
        String dbPass = formPass2DBPass(formPass, salt);
        return dbPass;
    }

    public static String inputPass2FormPass(String inputPass) {
        String str = SALT.charAt(1) + SALT.charAt(3) + inputPass + SALT.charAt(7) + SALT.charAt(5);
        String formPass = DigestUtils.md5Hex(str);
        return formPass;
    }

    public static String formPass2DBPass(String formPass, String salt) {
        String str = formPass + salt;
        String dbPass = DigestUtils.md5Hex(str);
        return dbPass;
    }

    public static void main(String[] args) {
        String ocean = inputPass2DBPass("123456", "ocean");
        System.out.println(ocean);
    }

}
