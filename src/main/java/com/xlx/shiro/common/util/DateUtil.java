package com.xlx.shiro.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
  }

}
