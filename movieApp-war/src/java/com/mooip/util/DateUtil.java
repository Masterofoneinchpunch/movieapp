package com.mooip.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtil {
 
    private static final LoggerAdapter log = LoggerAdapter.getLogger(DateUtil.class);
    public static final String DATE_TIME_FORMAT = "MM/dd/yyyy  hh:mm a";
    public static final String DATE_FORMAT = "MM/dd/yyyy";
    
    /** Date Format for time in MM/dd/yyyy  hh:mm a format */
    public static final DateFormat DATE_TIME_DATEFORMAT = new SimpleDateFormat(DATE_TIME_FORMAT);
    
    /** Date Format for MM/dd/yyyy format */
    public static final DateFormat DATE_DATEFORMAT = new SimpleDateFormat(DATE_FORMAT);
    
    private DateUtil() {
    }

    public static String getDateText(DateFormat df, Date date) {
        if (date == null) {
            return "";
        } else {
            return df.format(date);
        }
    }

    public static int getAge(Date birthdate) {
        Calendar earlyCal = Calendar.getInstance();
        earlyCal.setTime(birthdate);
        Calendar currentCal = Calendar.getInstance();
        
        int age = currentCal.get(Calendar.YEAR) - earlyCal.get(Calendar.YEAR);
        earlyCal.set(Calendar.YEAR, currentCal.get(Calendar.YEAR));
        
        if (earlyCal.before(currentCal)) {
            age++;
        }
        
        return age;
    }
 
    public static boolean sameDay(Date firstDate, Date secondDate) {
        if (firstDate == null) {
            throw new NullPointerException("The parameter firstDate in sameDay method should not be null!");
        }
        if (secondDate == null) {
            throw new NullPointerException("The parameter secondDate in sameDay method should not be null!");
        }
        Calendar cal = new GregorianCalendar();
        cal.setTime(firstDate);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(secondDate);
        
        return sameDay(cal, cal2);
    }
    
    /**
     * Check if two dates are same day, ignoring the time element.
     *
     * @param cal1 the first Calendar object.
     * @param cal2 the second Calendar object.
     * @return boolean a true if it is the same day, a false if different day.
     * @throws NullPointerException if cal1 or cal2 is null.
     */
    public static boolean sameDay(Calendar cal1, Calendar cal2){
        if (cal1 == null) {
            throw new NullPointerException("The parameter cal1 in sameDay method should not be null!");
        }
        if (cal2 == null) {
            throw new NullPointerException("The parameter cal2 in sameDay method should not be null!");
        }        
        boolean same = false;
        
        if (cal1.get(Calendar.DATE) == cal2.get(Calendar.DATE) && 
            cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
            cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            same = true;
        }
        
        return same;
    }
    
    public static Date parseDate(String date) {
        if (date == null || StringUtil.isEmpty(date)) {
            return null;
        }
        return parseDate(date, DATE_FORMAT);
    }
    
    public static Date parseDate(String date, String format) {
        if (date == null) {
            throw new NullPointerException("parameter date of method parseDate should not be null!");
        }
        if (format == null) {
            throw new NullPointerException("parameter date of method parseDate should not be null!");
        }
        
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException pee) {
            log.error(pee);
            throw new IllegalArgumentException("parameter date of method parseDate with value of " + date + " is illegal.");
        }
    }

    /**
     * Find a day (result will be in upper case string like SUNDAY).
     * 
     * @param month The month.
     * @param day The day.
     * @param year The year.
     * @return day Upper case day given the month/day/year.
     */
    public static String findDay(int month, int day, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] daysOfWeek = dfs.getWeekdays();

        return daysOfWeek[cal.get(Calendar.DAY_OF_WEEK)].toUpperCase();
    }
    
}