package com.xlx.mpd.system.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * @author xielx at 2020/4/4 14:55
 */
public class DateTimeUtil {
    
    /**
     * 字符日期转时间类型
     * @param str 自定义的字符日期
     * @param formatter 自定义的时间格式
     * @return LocalDateTime
     * @throws Exception 格式转换错误异常
     */
    public static LocalDateTime parseWithFormatter(String str, DateTimeFormatter formatter) throws Exception{
        return LocalDateTime.parse(str, formatter);
    }
    
    /**
     * 指定解析格式
     * @param str 日期字符 yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime
     * @throws Exception
     */
    public static LocalDateTime parse(String str) throws Exception{
        return LocalDateTime.parse(str, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    
    /**
     * 时间类型转字符日期
     * @param dateTime LocalDateTime
     * @param formatter 自定义的时间格式
     * @return 字符
     * @throws Exception 式转换错误异常
     */
    public static String formatWithFormatter(LocalDateTime dateTime, DateTimeFormatter formatter) throws Exception{
        return dateTime.format(formatter);
    }
    
    public static void main(String[] args) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String st = "2020-01-04 15:11:30";
        LocalDateTime localDateTime = parseWithFormatter(st, formatter);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
