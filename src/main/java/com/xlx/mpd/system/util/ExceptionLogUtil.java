package com.xlx.mpd.system.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常日志工具
 *
 * @author xielx at 2020/3/28 14:36
 */
@Slf4j
public class ExceptionLogUtil {
    
    
    /**
     * 打印异常信息
     * @param e 异常
     * @return 字符串
     */
    public static String getMessage(Exception e) {
        String str = null;
        try(StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)){
        
            e.printStackTrace(pw);
            pw.flush();;
            sw.flush();
            str = pw.toString();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("异常日志工具:{}",ex.getMessage());
        }
        
        return str;
    }
    
}
