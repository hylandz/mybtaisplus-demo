package com.xlx.mpd.system.util;

import java.util.Base64;

/**
 * 加密工具类
 *
 * @author xielx at 2020/4/12 12:15
 */
public class CryptoUtil {
    
    /**
     * Base64编码
     * @param str 字符串
     * @return String
     */
    public static String encodeBase64(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }
    
    /**
     * 解码
     * @param encode 已编码字符串
     * @return String
     */
    public static String decodeBase64(String encode){
        return new String(Base64.getDecoder().decode(encode));
    }
}
