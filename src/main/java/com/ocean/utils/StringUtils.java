package com.ocean.utils;

public class StringUtils {


    public static Boolean isNullOrZero(Integer target) {
        if (target == null || target.equals(0)) {
            return true;
        }
        return false;
    }
}
