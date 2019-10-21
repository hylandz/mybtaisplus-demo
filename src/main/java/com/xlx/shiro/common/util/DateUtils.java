package com.xlx.shiro.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author xielx on 2019/7/15
 */
public class DateUtils {

  /**
   * 将Date型格式化为字符型日期
   * @param date Date
   * @return yyyy/MM/dd HH:mm:ss
   */
  public static String formatString(Date date){
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }
  
  /**
   * 自定义格式日期字符串
   * @param date Date
   * @param format 格式
   * @return String
   */
  private static String formatString(Date date, String format){
    return new SimpleDateFormat(format).format(date);
  }
  
  
  /**
   * 解析美式字符日期再转换为合适字符日期
   * @param date 美式字符日期
   * @param format 要转换的格式
   * @return 字符日期
   * @throws ParseException 解析异常
   */
  public static String formatCSTTime(String date, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    Date d = sdf.parse(date);
    return DateUtils.formatString(d, format);
  }
}
