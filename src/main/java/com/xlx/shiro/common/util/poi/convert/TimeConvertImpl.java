package com.xlx.shiro.common.util.poi.convert;

import com.xlx.shiro.common.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间转换
 *
 * @author xielx at 2019/10/18 12:15
 */
public class TimeConvertImpl implements IExportConvert{
    
    private static final Logger log = LoggerFactory.getLogger(TimeConvertImpl.class);
    
    private static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    @Override
    public String handler(Object val) {
        if (val == null){
            return "";
        }
        
        try{
            return DateUtils.formatCSTTime(val.toString(),DATE_TIME);
        }catch (Exception e){
            log.error("日期转换失败:{}",e.getMessage());
            return "";
        }
    }
}
