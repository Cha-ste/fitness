package com.ocean.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {
    private final SimpleDateFormat smf =  new SimpleDateFormat("yyyyMMdd");
    @Override
    public Date convert(String s) {
        if ("".equals(s) || null == s) {
            return null;
        }

        try {
            return smf.parse(s);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
