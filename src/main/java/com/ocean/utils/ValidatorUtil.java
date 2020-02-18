package com.ocean.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");

    /**
     * 判断电话号码是否正确
     *
     * @param mobile 电话号码
     * @return true-正确； false-错误
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) return false;

        Matcher matcher = MOBILE_PATTERN.matcher(mobile);
        return matcher.matches();
    }
}
