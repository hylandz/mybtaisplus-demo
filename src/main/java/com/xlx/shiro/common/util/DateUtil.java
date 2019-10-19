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
public class DateUtil {

  /**
   * 将Date型格式化为字符型日期
   * @param date Date
   * @return yyyy/MM/dd HH:mm:ss
   */
  public static String formatString(Date date){
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }
  
  private static String formatString(Date date, String format){
    return new SimpleDateFormat(format).format(date);
  }
  
  public static String formatCSTTime(String date, String format) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
    Date d = sdf.parse(date);
    return DateUtil.formatString(d, format);
  }
}
