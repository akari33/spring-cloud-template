package com.temp.common.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Date;

/**
 * 时间工具类
 */
public class MyDateUtil {

    /**
     * 设置传入时间的小时最后一秒,保证mysql存储的时间是59分59秒
     */
    public static Date setHourLastSecond(Date time, int hour) {
        DateTime end = DateUtil.endOfHour(DateUtil.offsetHour(time, hour));
        //mysql中需要存储的时间是秒级别的，所以需要将毫秒设置为0
        return DateUtil.offsetMillisecond(end, -599);
    }

    /**
     * 设置传入时间的小时最后一秒,保证mysql存储的时间是59分59秒
     */
    public static Date setHourLastSecond(Date time) {
        DateTime end = DateUtil.endOfHour(time);
        //mysql中需要存储的时间是秒级别的，所以需要将毫秒设置为0
        return DateUtil.offsetMillisecond(end, -599);
    }


    /**
     * 传入的date转换成年份+周数的字符串
     */
    public static String getYearWeekStrByDate(Date date) {
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        //date to localdatetime
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        return localDateTime.getYear() + "" + localDateTime.get(weekFields.weekOfYear());
    }

    public static String getYearWeekStrByLocalDateTime(LocalDateTime date) {
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        return date.getYear() + "" + date.get(weekFields.weekOfYear());
    }

    /**
     * 获取传入日期是当周周几，周一为一周的开始
     */
    public static int getDayOfWeek(Date date) {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        return localDateTime.getDayOfWeek().getValue();
    }

    /**
     * 将yyyyMMdd格式的字符串转换成带-的日期字符串
     */
    public static String getDateString(String dateStr) {
        return dateStr.substring(0, 4) + "-" + dateStr.substring(4, 6) + "-" + dateStr.substring(6);
    }

    /**
     * 获取传入时间的年
     */
    public static String getYear(Date date) {
        return String.valueOf(DateUtil.year(date));
    }

    /**
     * 获取传入时间的月
     */
    public static String getMonth(Date date) {
        return String.valueOf(DateUtil.month(date) + 1);
    }

    /**
     * 获取传入时间的日
     */
    public static String getDayOfMonth(Date date) {
        return String.valueOf(DateUtil.dayOfMonth(date));
    }

    /**
     * 获取传入时间的小时
     */
    public static String getHour(Date date) {
        return String.valueOf(DateUtil.hour(date, true));
    }

}

