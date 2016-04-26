package com.example.myapplication.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public final class DateUtil implements Serializable {
    private static final long serialVersionUID = -3098985139095632110L;
    private static String TAG = DateUtil.class.getSimpleName();

    private DateUtil() {
    }

    public static String getCurrentMonth1() {
        return getFormatCurrentTime("yyyy/MM");
    }

    /**
     * 返回当前时间字符串。
     */
    public static String getCurrentDate() {
        return getFormatDateTime(new Date(), "yyyy/MM/dd");
    }

    /**
     * 返回给定时间字符串。
     */
    public static String getFormatYear(Date date) {
        return getFormatDateTime(date, "yyyy");
    }

    public static String getFormatMonth(Date date) {
        return getFormatDateTime(date, "MM");
    }

    public static String getFormatDay(Date date) {
        return getFormatDateTime(date, "dd");
    }

    public static String getFormatDate(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    public static String getFormatDate1(Date date) {
        return getFormatDateTime(date, "yyyy/MM");
    }

    public static String getFormatDate2(Date date) {
        return getFormatDateTime(date, "yyyy/MM/dd");
    }

    /**
     * 返回当前时间字符串。
     */
    public static String getCurrentTime() {
        return getFormatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentTime1() {
        return getFormatDateTime(new Date(), "yyyy/MM/dd");
    }

    public static String getCurrentTime2() {
        return getFormatDateTime(new Date(), "yyyy MM dd HH mm ss");
    }

    /**
     * 返回给短时间字符串。
     */
    public static String getFormatShortTime(Date date) {
        return getFormatDateTime(date, "yyyy-MM-dd");
    }

    /**
     * 根据给定的格式，返回时间字符串。
     */
    public static String getFormatCurrentTime(String format) {
        return getFormatDateTime(new Date(), format);
    }

    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用
     */
    public static String getFormatDateTime(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 取得指定年月日的日期对象.
     */
    public static Date getDateObj(int year, int month, int day) {
        Calendar c = new GregorianCalendar();
        c.set(year, month - 1, day);
        return c.getTime();
    }

    /**
     * 获取指定日期的下一天。
     */
    public static String getNextDay(String date) {
        Date tempDate = null;
        if (date.indexOf("/") > 0)
            tempDate = getDateObj(date, "[/]");
        if (date.indexOf("-") > 0)
            tempDate = getDateObj(date, "[-]");
        tempDate = getDateAdd(tempDate, 1);
        return getFormatDateTime(tempDate, "yyyy/MM/dd");
    }

    /**
     * 获取指定日期的上一天。
     */
    public static String getPreDay(String date) {
        Date tempDate = null;
        if (date.indexOf("/") > 0)
            tempDate = getDateObj(date, "[/]");
        if (date.indexOf("-") > 0)
            tempDate = getDateObj(date, "[-]");
        tempDate = getDateAdd(tempDate, -1);
        return getFormatDateTime(tempDate, "yyyy/MM/dd");
    }

    /**
     * 取得指定分隔符分割的年月日的日期对象.
     */
    public static Date getDateObj(String argsDate, String split) {
        String[] temp = argsDate.split(split);
        int year = new Integer(temp[0]).intValue();
        int month = new Integer(temp[1]).intValue();
        int day = new Integer(temp[2]).intValue();
        return getDateObj(year, month, day);
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     */
    public static Date getDateAdd(Date date, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, amount);
        return cal.getTime();
    }

    public static String getDateTime(long microsecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(microsecond * 1000);
        return getFormatDateTime(c.getTime(), "yyyy-MM-dd HH:mm");
    }

    public static String getDateTime1(long microsecond) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(microsecond * 1000);
        return getFormatDateTime(c.getTime(), "HH:mm");
    }


    /**
     * 判读date是否是今天
     */
    public static boolean isToday(String date) {
        String today = DateUtil.getCurrentDate();
        if (TextUtils.isEmpty(today)) {
            return false;
        }
        if (today.equalsIgnoreCase(date)) {
            return true;
        }
        return false;
    }

    /**
     * 获取给定日期的前两周、本周、后两周的开始时间和结束时间
     * str = 2016/03/25
     */
    public static List<String> getWeek(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        List<String> weeks = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.WEEK_OF_YEAR, i);
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);            //如果你想设置周一是第一天。这里就设置Calendar.MONDAY
            String start = sdf.format(c.getTime());
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);            //如果你想设置周日是第最后。这里就设置Calendar.SUNDAY
            String end = sdf.format(c.getTime());
            Log.i(TAG, "周[" + i + "]:" + start + "-" + end);
            weeks.add(start + "-" + end);
        }
        return weeks;
    }

    /**
     * 获取给定日期的上周开始时间和结束时间
     * str = 2016/03/25
     */
    public static String getPreWeek(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, -1);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);            //如果你想设置周一是第一天。这里就设置Calendar.MONDAY
        String start = sdf.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);          //如果你想设置周日是第最后。这里就设置Calendar.SUNDAY
        String end = sdf.format(c.getTime());
        Log.i(TAG, "上周:" + start + "-" + end);
        return start + "-" + end;
    }

    /**
     * 获取给定日期的下周开始时间和结束时间
     * str = 2016/03/25
     */
    public static String getNextWeek(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, 1);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);            //如果你想设置周一是第一天。这里就设置Calendar.MONDAY
        String start = sdf.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);          //如果你想设置周日是第最后。这里就设置Calendar.SUNDAY
        String end = sdf.format(c.getTime());
        Log.i(TAG, "上周:" + start + "-" + end);
        return start + "-" + end;
    }

    /**
     * 获取当前周的开始时间和结束时间
     */
    public static String getCurrentWeek() {
        String weeks = "";
        String str = DateUtil.getCurrentTime1();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.WEEK_OF_YEAR, 0);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);            //如果你想设置周一是第一天。这里就设置Calendar.MONDAY
        String start = sdf.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);            //如果你想设置周日是第最后。这里就设置Calendar.SUNDAY
        String end = sdf.format(c.getTime());
        Log.i(TAG, start + "-" + end);
        weeks = start + "-" + end;
        return weeks;
    }

    /**
     * 获取给定日期的前两月、本月、后两月的月时间
     * str = 2016/03/25
     */
    public static List<String> getMonth(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        List<String> months = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, i);
            String start = sdf.format(c.getTime());
            Log.i(TAG, "月[" + i + "]:" + start);
            months.add(start);
        }
        return months;
    }

    public static String getPreMonth(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        String start = sdf.format(c.getTime());
        Log.i(TAG, "上月:" + start);
        return start;
    }

    public static String getNextMonth(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 1);
        String start = sdf.format(c.getTime());
        Log.i(TAG, "下月:" + start);
        return start;
    }

    /**
     * 根据年月获取对应的月份天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年月获取对应的月份天数
     * date = 2016/3
     */
    public static int getDaysByYearMonth1(String date) {
        String[] args = date.split("/");
        int year = Integer.parseInt(args[0]);
        int month = Integer.parseInt(args[1]);
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取一天的开始时间
     */
    public static long getStartMillsInOneDay(Calendar calendar) {
        int i = calendar.get(Calendar.YEAR);
        int j = calendar.get(Calendar.MONTH);
        int k = calendar.get(Calendar.DATE);
        calendar.clear();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, j);
        calendar.set(Calendar.DATE, k);
        return calendar.getTimeInMillis();
    }

    /**
     * 时间转换
     * timeStr = "2016年03月29日 10"
     */
    public static long timeString2Long(String timeStr) {
        long forcastTime = 0;
        try {
            forcastTime = new SimpleDateFormat("yyyy年M月d日 HH").parse(timeStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forcastTime;
    }

    /**
     * 时间转换
     * timeStr = "2016-03-29 09:00"
     */
    public static long timeString2Long1(String timeStr) {
        Calendar calendar1 = Calendar.getInstance();
        String[] arrayOfString = timeStr.split(":");
        int i = Integer.parseInt(arrayOfString[0]);
        int j = Integer.parseInt(arrayOfString[1]);
        Long localLong = Long.valueOf(calendar1.getTimeInMillis());
        if (i - calendar1.get(Calendar.HOUR_OF_DAY) > 2) {
            localLong = Long.valueOf(localLong.longValue() - 24 * 60 * 60 * 1000L);
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(localLong.longValue());
        calendar2.set(Calendar.HOUR_OF_DAY, i);
        calendar2.set(Calendar.MINUTE, j);
        return calendar2.getTimeInMillis();
    }

    /**
     * 时间转换
     * timeStr = "2016-03-29 09:00"
     */
    public static long timeString2Long2(String timeStr) {
        long time = 0;
        try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 字符串转日期格式
     */
    public static Date String2Date(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据开始时间、结束时间得到两个时间段内所有的日期
     */
    public static List<String> getDatePeriods(String min, String max) {
        List<String> periods = new ArrayList<String>();
        Date start = String2Date(min);
        Date end = String2Date(max);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        Date temp = calendar.getTime();
        long endTime = end.getTime();
        while (temp.before(end) || temp.getTime() == endTime) {
            periods.add(getFormatDate(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            temp = calendar.getTime();
        }
        return periods;
    }

}