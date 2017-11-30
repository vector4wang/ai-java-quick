package com.quick.text.Test4Excel.util;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtils {
    public static final String defaultDateFormat = "yyyy-MM-dd";
    public static final String defaultDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String dateYMdHm = "yyyy-MM-dd HH:mm";
    public static final String Hm = "HH:mm";
    public static final String Hms = "HH:mm:ss";
    public static final String noseq = "yyyyMMddhhmmss";

    public static final String ym = "yyyy-MM";

    public static final String y = "yyyy";

    public static final String ym_slash = "yyyy/MM";

    public static final String yMd = "yyyy-MM-dd";
    public static final String yMd1 = "yyyy.MM.dd";
    public static final String MMdd = "MM-dd";

    public final static String MONDAY = "Monday";
    public final static String TUESDAY = "Tuesday";
    public final static String WEDNESDAY = "Wednesday";
    public final static String THURSDAY = "Thursday";
    public final static String FRIDAY = "Friday";
    public final static String SATURDAY = "Saturday";
    public final static String SUNDAY = "Sunday";

    public final static long SECOND = 1000;
    public final static long MINUTE = SECOND * 60;
    public final static long HOUR = MINUTE * 60;
    public final static long DAY = HOUR * 24;
    public final static long WEEK = DAY * 7;

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        return dataFormat.format(date);
    }

    public static String format(Calendar calendar, String format) {
        if (calendar == null) {
            return "";
        }
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        return dataFormat.format(calendar.getTime());
    }

    public static Date parse(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        SimpleDateFormat dataFormat = new SimpleDateFormat(format);
        try {
            date = dataFormat.parse(dateStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return date;
    }



    public static boolean isMixed(Date oldBeginDate, Date oldEndDate, Date newBeginDate, Date newEndDate) {
        if (oldBeginDate.after(newEndDate)) {
            return false;
        } else if (newBeginDate.after(oldEndDate)) {
            return false;
        }
        return true;
    }

    public static PeriodDate getMixedDate(Date oldBeginDate, Date oldEndDate, Date newBeginDate, Date newEndDate) {
        if (isMixed(oldBeginDate, oldEndDate, newBeginDate, newEndDate)) {
            Date beginDate = null;
            Date endDate = null;
            int compare = newBeginDate.compareTo(oldBeginDate);
            if (compare == 0) {
                beginDate = newBeginDate;
            } else if (compare > 0) {
                beginDate = newBeginDate;
            } else {
                beginDate = oldBeginDate;
            }

            compare = newEndDate.compareTo(oldEndDate);
            if (compare == 0) {
                endDate = newEndDate;
            } else if (compare > 0) {
                endDate = oldEndDate;
            } else {
                endDate = newEndDate;
            }
            PeriodDate periodDate = new PeriodDate();
            periodDate.setBeginDate(beginDate);
            periodDate.setEndDate(endDate);
            return periodDate;
        } else {
            return null;
        }
    }

    public static class PeriodDate {

        private Date beginDate;
        private Date endDate;

        public Date getBeginDate() {
            return beginDate;
        }

        public void setBeginDate(Date beginDate) {
            this.beginDate = beginDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }

    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfWeek(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date addDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    public static String getDayOfWeekStr(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String dayOfWeekStr = null;
        if (dayOfWeek == 1) {
            dayOfWeekStr = SUNDAY;
        } else if (dayOfWeek == 2) {
            dayOfWeekStr = MONDAY;
        } else if (dayOfWeek == 3) {
            dayOfWeekStr = TUESDAY;
        } else if (dayOfWeek == 4) {
            dayOfWeekStr = WEDNESDAY;
        } else if (dayOfWeek == 5) {
            dayOfWeekStr = THURSDAY;
        } else if (dayOfWeek == 6) {
            dayOfWeekStr = FRIDAY;
        } else if (dayOfWeek == 7) {
            dayOfWeekStr = SATURDAY;
        }
        return dayOfWeekStr;
    }

    public static Calendar getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return getFirstDayOfMonth(calendar);
    }

    public static Calendar getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return getLastDayOfMonth(calendar);
    }

    public static Calendar getFirstDayOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    public static Calendar getLastDayOfMonth(Calendar calendar) {
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    public static Calendar getFirstDayOfMonth(int year, int month) {
        month = month - 1;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c;
    }

    public static Calendar getLastDayOfMonth(int year, int moth) {
        Calendar c = getFirstDayOfMonth(year, moth);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c;
    }

    public static Calendar getFirstDayOfWeek() {
        Calendar monday = Calendar.getInstance();
        return getADayOfWeek(monday, Calendar.MONDAY);
    }

    public static Calendar getFirstDayOfWeek(Calendar day) {
        Calendar monday = (Calendar) day.clone();
        return getADayOfWeek(monday, Calendar.MONDAY);
    }

    public static Calendar getLastDayOfWeek() {
        Calendar sunday = Calendar.getInstance();
        return getADayOfWeek(sunday, Calendar.SUNDAY);
    }

    public static Calendar getLastDayOfWeek(Calendar day) {
        Calendar sunday = (Calendar) day.clone();
        return getADayOfWeek(sunday, Calendar.SUNDAY);
    }

    private static Calendar getADayOfWeek(Calendar day, int dayOfWeek) {
        int week = day.get(Calendar.DAY_OF_WEEK);
        if (week == dayOfWeek)
            return day;
        int diffDay = dayOfWeek - week;
        if (week == Calendar.SUNDAY) {
            diffDay -= 7;
        } else if (dayOfWeek == Calendar.SUNDAY) {
            diffDay += 7;
        }
        day.add(Calendar.DATE, diffDay);
        return day;
    }

    /*
     *清除时分秒，毫秒
     */
    public static Date clearHMSM(Date date) {
        String dateStr = format(date, defaultDateFormat);
        return parse(dateStr, defaultDateFormat);
    }

    /**
     * 清除日、时、分、秒
     * @param date
     * @return
     */
    public static Date clearDHMSM(Date date) {
        String dateStr = format(date, ym);
        return parse(dateStr, ym);
    }

    /**
     * 清除月、日、时、分、秒
     * @param date
     * @return
     */
    public static Date clearMDHMSM(Date date) {
        String dateStr = format(date, y);
        return parse(dateStr, y);
    }

    /**
     * 计算两个日期的相差月份
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public static int diffMonth(Date sourceDate, Date targetDate) {
        Calendar bef = Calendar.getInstance();
        bef.setTime(sourceDate);
        Calendar aft = Calendar.getInstance();
        aft.setTime(targetDate);

        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 两日期 年月日 是否相同
     * @param d1
     * @param d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2) {
        if(null == d1 || null == d2)
            return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        return  cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
    }

    /**
     * 判断两个日期是否相同，精确到月，上下浮动2个月
     *
     * @param sourceDate
     * @param targetDate
     * @return
     */
    public static boolean isEqualDate(Date sourceDate, Date targetDate) {
        if (sourceDate == null || targetDate == null) {
            return false;
        }
        Date newSourceDate = DateUtils.clearDHMSM(sourceDate);
        Date newTargetDate = DateUtils.clearDHMSM(targetDate);
        if (newSourceDate.compareTo(newTargetDate) == 0
                || DateUtils.diffMonth(newSourceDate, newTargetDate) <= 2) {
            return true;
        }
        return false;

    }

    public static int diffYears(Date targetDate) {
        Calendar sourceInstance = Calendar.getInstance();
        Calendar currentInstance = Calendar.getInstance();
        sourceInstance.setTime(targetDate);
        int old = sourceInstance.get(Calendar.YEAR);
        int now = currentInstance.get(Calendar.YEAR);
        return now - old;
    }
}
