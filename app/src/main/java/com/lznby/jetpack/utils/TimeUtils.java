package com.lznby.jetpack.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Lznby
 */
public class TimeUtils {

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
     * @throws ParseException
     */
    public static String dealDateFormat(String oldDateStr) throws ParseException {
        //此格式只有  jdk 1.7才支持  yyyy-MM-dd'T'HH:mm:ss.SSSXXX
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        Date date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //  Date date3 =  df2.parse(date1.toString());
        return df2.format(date1);
    }

    /**
     * 转Unix
     */
    public static long date2TimeStamp(String dateString){
        Date date = null;
        long unixTimestamp = 0;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(dateString);
            unixTimestamp = date.getTime()/1000;
        } catch (ParseException e) {
//            e.printStackTrace();
        }

        return unixTimestamp;
    }
}
