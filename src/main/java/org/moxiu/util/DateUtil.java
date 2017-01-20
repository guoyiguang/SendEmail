package org.moxiu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getDate2String(String str) {
        int i = Integer.valueOf(str);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.add(5, i);
        return sdf.format(ca.getTime());
    }

    public static Date stringToDate(String dateString, String pattern) {
        if ((pattern == null) || ("" == pattern))
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = new Date(sdf.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToString(Date date, String pattern) {
        if ((null == pattern) || ("" == pattern))
            pattern = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (null == date)
            date = new Date();
        return sdf.format(date);
    }

    public static String getYestarday(String dateString, String pattern) {
        if ((pattern == null) || ("" == pattern))
            pattern = "yyyy-MM-dd";
        if ((null == dateString) || ("" == dateString))
            dateToString(null, null);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = new Date(sdf.parse(dateString).getTime() - 86400000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }
}