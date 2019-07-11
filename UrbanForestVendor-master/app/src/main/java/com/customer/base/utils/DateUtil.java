package com.customer.base.utils;

/**
 * Created by priya on 08/05/17.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static String formatDate(String sourceFormatString, String desiredFormatString, String dateString) {
        SimpleDateFormat sourceFormat = new SimpleDateFormat(sourceFormatString, Locale.ENGLISH);
        SimpleDateFormat desiredFormat = new SimpleDateFormat(desiredFormatString, Locale.ENGLISH);

        Date date = null;
        try {
            date = sourceFormat.parse(dateString);
            return desiredFormat.format(date.getTime());

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dateString;
    }

    public static String formatDate(String desiredFormatString, Date date) {
        if (date != null) {
            SimpleDateFormat desiredFormat = new SimpleDateFormat(desiredFormatString, Locale.ENGLISH);
            return desiredFormat.format(date.getTime());
        } else{
            return "";
        }

    }

    public static long daysBetween(Calendar startDate, Calendar endDate) {
        long diff = setTimeToMidnight(endDate).getTime() - setTimeToMidnight(startDate).getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static Date setTimeToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getCurrentDateInSuffixFormat(Calendar currentCalDate) {
        String dayNumberSuffix = getDayNumberSuffix(currentCalDate.get(Calendar.DAY_OF_MONTH));

        DateFormat dateFormat = new SimpleDateFormat("d" + " MMMM, E", Locale.US);
        // DateFormat dateFormat = new
        // SimpleDateFormat(Html.fromHtml("d' <sup>"+dayNumberSuffix+"</sup>") +
        // "' MMMM, E", Locale.US);
        return dateFormat.format(currentCalDate.getTime());
    }

    public static String getCurrentDateInShortDateFormat(Calendar currentCalDate) {
        DateFormat dateFormat = new SimpleDateFormat("E dd MMM yyyy");
        return dateFormat.format(currentCalDate.getTime());
    }

    public static String getCurrentDateInSimpleFormat(Calendar currentCalDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        return dateFormat.format(currentCalDate.getTime());
    }

    public static String getCurrentDateInReverseFormat(Calendar currentCalDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(currentCalDate.getTime());
    }

    private static String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "<sup>th</sup>";
        }
        switch (day % 10) {
            case 1:
                return "<sup>st</sup>";
            case 2:
                return "<sup>nd</sup>";
            case 3:
                return "<sup>rd</sup>";
            default:
                return "<sup>th</sup>";
        }
    }
}
