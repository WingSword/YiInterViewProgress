package com.walkS.yiprogress.utils;

import androidx.core.net.ParseException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author: xtxiaolu
 * @date: 2023/7/14
 * 描述: 主要功能格式化时间工具类
 */
public class DateUtil {

    // 日期格式年份，例如：2022，2023
    public static final String FORMAT_YYYY = "yyyy";
    // 其他格式常量...

    private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();
    private static TimeZone DefaultTimeZone = TimeZone.getDefault();

    /**
     * 获取当前时间的字符串表示
     *
     * @param pattern 时间格式
     * @return 当前时间的字符串表示
     */
    public static String getCurrentDate(String pattern) {
        return formatToStr(new Date(), pattern);
    }

    /**
     * 将时间戳格式化为指定格式的字符串
     *
     * @param timestamp 时间戳
     * @param pattern   时间格式
     * @return 格式化后的时间字符串
     */
    public static String formatToStr(long timestamp, String pattern) {
        return formatToStr(new Date(timestamp), pattern);
    }

    /**
     * 将日期对象格式化为指定格式的字符串
     *
     * @param date    日期对象
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    public static String formatToStr(Date date, String pattern) {
        DateFormat dateFormat = getDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 获取指定格式的日期格式化对象
     *
     * @param pattern 时间格式
     * @return 日期格式化对象
     */
    private static DateFormat getDateFormat(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(DEFAULT_TIMEZONE);
        return dateFormat;
    }

    /**
     * 格式化字符串时间为指定格式
     *
     * @param dateString 字符串时间
     * @param format     格式
     * @return 格式化后的时间字符串
     */
    public static String formatStringDate(String dateString, String format) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat(format);
        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前时间的日期对象
     *
     * @return 当前时间的日期对象
     */
    public static Date getCurrentTime() {
        return new Date();
    }

    /**
     * 将日期对象格式化为指定格式的时间字符串
     *
     * @param date    日期对象
     * @param pattern 时间格式
     * @return 格式化后的时间字符串
     */
    public static String formatTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 解析指定格式的时间字符串为日期对象
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 解析后的日期对象
     * @throws ParseException 解析异常
     */
    public static Date parseTime(String time, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(time);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算两个日期之间的时间差，返回指定时间单位的差值
     *
     * @param date1     第一个日期对象
     * @param date2     第二个日期对象
     * @param timeUnit  时间单位
     * @return 时间差的差值
     */
    public static long getTimeDifference(Date date1, Date date2, TimeUnit timeUnit) {
        long difference = date2.getTime() - date1.getTime();
        return timeUnit.convert(difference, TimeUnit.MILLISECONDS);
    }

    /**
     * 判断指定时间是否在给定时间区间内
     *
     * @param time      待判断的时间
     * @param startTime 时间区间的开始时间
     * @param endTime   时间区间的结束时间
     * @return 如果指定时间在时间区间内，返回 true；否则返回 false
     */
    public static boolean isInTimeRange(Date time, Date startTime, Date endTime) {
        return time.after(startTime) && time.before(endTime);
    }

    /**
     * 判断指定年份是否为闰年
     *
     * @param year 年份
     * @return 如果是闰年，返回 true；否则返回 false
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 获取指定日期对象的年份
     *
     * @param date 日期对象
     * @return 年份
     */
    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取指定日期对象的月份
     *
     * @param date 日期对象
     * @return 月份
     */
    public static int getMonthFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定日期对象的星期
     *
     * @param date 日期对象
     * @return 星期，1 表示星期一，2 表示星期二，依次类推
     */
    public static int getWeekdayFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}

