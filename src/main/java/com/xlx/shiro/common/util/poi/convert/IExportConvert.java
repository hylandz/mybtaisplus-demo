package com.xlx.shiro.common.util.poi.convert;

/**
 * 转换接口
 *
 * @author xielx at 2019/10/18 12:13
 */
public interface IExportConvert {
    
    /**
     * 数据转换
     * @param val 要转数据
     * @return String
     */
    String handler(Object val);
}
