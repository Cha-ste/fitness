package com.ocean.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间、日期工具
 */
public class DateUtils {


    /**
     * 指定时间的前或者后几天
     *
     * @param date 时间
     * @param days 步数
     * @return 前或者后几天
     */
    public static Date addDays (Date date, int days) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }

    public static void main(String[] args) {
        System.out.println(addDays(new Date(), 2));
    }
}
