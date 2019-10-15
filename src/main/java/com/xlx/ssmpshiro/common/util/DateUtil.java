package com.xlx.ssmpshiro.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date工具类
 *
 * @author xielx on 2019/7/17
 */
public class DateUtil {

  private static final String FORMAT_FULL_TIME = "yyyy-MM-dd HH:mm:ss";
  private static final String FORMAT_DATE = "yyyy-MM-dd";
  private static final String FORMAT_TIME = "HH:mm:ss";
  private static final String FORMAT_YEAR = "yyyy";

  /**
   * 字符日期:yyyy-MM-dd HH:mm:ss
   * @param date Date
   * @return str
   */
  public static String formatStringOfFullTime(Date date){
    return new SimpleDateFormat(FORMAT_FULL_TIME).format(date);
  }

  /**
   * 字符日期: yyyy-MM-dd
   * @param date Date
   * @return str
   */
  public static String formatStringOfDate(Date date){
    return new SimpleDateFormat(FORMAT_DATE).format(date);
  }

  /**
   * 字符日期: HH:mm:ss
   * @param date Date
   * @return str
   */
  public static String formatStringOfTime(Date date){
    return new SimpleDateFormat(FORMAT_TIME).format(date);
  }

  /**
   * 字符日期: yyyy
   * @param date Date
   * @return str
   */
  public static String formatStringOfYear(Date date){
    return new SimpleDateFormat(FORMAT_YEAR).format(date);
  }
}
