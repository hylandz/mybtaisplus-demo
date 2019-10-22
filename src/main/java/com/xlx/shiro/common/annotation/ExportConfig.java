package com.xlx.shiro.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * poi导出注解类
 *
 * @author xielx at 2019/10/17 19:52
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExportConfig {
    
    /**
     * 默认使用实体类属性名
     * @return String
     */
    String value() default "field";
    
    /**
     * 单元格宽度,默认-1
     * @return short
     */
    short width() default -1;
    
    /**
     * 数据转换 <br />
     * 应用场景:
     * 1. 数字转换对应字符串,如 1代表男,2代表女
     * @return String
     */
    String convert() default "";
    
    /**
     * 单元格的字体颜色
     * @return short
     */
    short color() default 8;
    
    /**
     * 数值替换
     * 如:手机号码变成173****2927
     * @return String
     `*/
    String replace() default "";
    
    
    
}
