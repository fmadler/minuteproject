package net.sf.minuteproject.adf.utils;

import java.sql.Timestamp;

import java.util.Calendar;

import oracle.jbo.domain.Date;

import org.apache.commons.lang.time.FastDateFormat;

public class DateUtils {
    public DateUtils() {
        super();
    }

    public static Date toOracleDate(java.util.Date date) {
        if (date == null)
            return null;
        return new oracle.jbo.domain.Date(new Timestamp(date.getTime()));
    }

    public static java.util.Date toJavaDate(Date date) {
        if (date == null)
            return null;
        Timestamp stamp = date.timestampValue();
        java.util.Date javaDate = new java.util.Date(stamp.getTime());
        return javaDate;
    }

    public static oracle.jbo.domain.Date getTodayStart() {
        return toOracleDate(clear(Calendar.getInstance()).getTime());
    }

    public static oracle.jbo.domain.Date getTodayEnd() {
        Calendar calendar = clear(Calendar.getInstance());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return toOracleDate(calendar.getTime());

    }

    public static oracle.jbo.domain.Date getThisWeekStart() {
        Calendar calendar = clear(Calendar.getInstance());
        calendar.add(Calendar.DAY_OF_WEEK, - calendar.get(Calendar.DAY_OF_WEEK));
        return toOracleDate(calendar.getTime());
    }

    public static oracle.jbo.domain.Date getThisWeekEnd() {
        return getTodayEnd();
    }

    public static oracle.jbo.domain.Date getThisMonthStart() {
        Calendar calendar = clear(Calendar.getInstance());
        calendar.add(Calendar.DAY_OF_MONTH,  - calendar.get(Calendar.DAY_OF_MONTH));
        return toOracleDate(calendar.getTime());
    }

    public static oracle.jbo.domain.Date getThisMonthEnd() {
        return getTodayEnd();
    }

    public static oracle.jbo.domain.Date getThisYearStart() {
        Calendar calendar = clear(Calendar.getInstance());
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 0);
        return toOracleDate(calendar.getTime());
    }

    public static oracle.jbo.domain.Date getThisYearEnd() {
        return getTodayEnd();
    }

    public static oracle.jbo.domain.Date getDateStart(oracle.jbo.domain.Date date) {
        java.util.Date javaDate = toJavaDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(javaDate);
        return toOracleDate(clear(calendar).getTime());
    }

    public static oracle.jbo.domain.Date getDateEnd(Date param) {
        java.util.Date javaDate = toJavaDate(param);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(javaDate);
        clear(calendar).add(Calendar.DAY_OF_MONTH, 1);
        return toOracleDate(calendar.getTime());
    }

    public static Calendar clear(Calendar calendar) {
        calendar.add(Calendar.HOUR_OF_DAY,
                     -calendar.get(Calendar.HOUR_OF_DAY));
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }
    
    public static void main(String [] args){
      System.out.println(formatJavaDate(toJavaDate(getTodayStart()), "yyyy-MM-dd HH:mm"));
      System.out.println(formatJavaDate(toJavaDate(getTodayEnd()), "yyyy-MM-dd HH:mm"));
      System.out.println(formatJavaDate(toJavaDate(getThisWeekStart()), "yyyy-MM-dd HH:mm"));
      System.out.println(formatJavaDate(toJavaDate(getThisMonthStart()), "yyyy-MM-dd HH:mm"));
      System.out.println(formatJavaDate(toJavaDate(getThisYearStart()), "yyyy-MM-dd HH:mm"));
    }
    
    public static String formatJavaDate(java.util.Date date, String pattern) {
        if (date==null){
            return "";
        }
        FastDateFormat dateFormat = FastDateFormat.getInstance(pattern);
        return dateFormat.format(date);
    }
}
