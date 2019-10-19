package com.xlx.shiro.common.util.poi;

/**
 * 导出POI类,excel的单列封装
 * 如:
 *   '@ExportConfig(value = "昵称")'
 *    private String avatarName;
 *
 * @author xielx at 2019/10/17 23:41
 */
public class ExportItem {
    
    private String field; // excel的列名(实体类属性)
   
    // 注解类属性
    private String display; // excel的列名(value值)
    private short width; // 宽度
    private String convert; // 数据转换
    private short color; // 颜色
    private String replace; // 敏感字符置换
    
    public String getField() {
        return field;
    }
    
    public ExportItem setField(String field) {
        this.field = field;
        return this;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public ExportItem setDisplay(String display) {
        this.display = display;
        return this;
    }
    
    public short getWidth() {
        return width;
    }
    
    public ExportItem setWidth(short width) {
        this.width = width;
        return this;
    }
    
    public String getConvert() {
        return convert;
    }
    
    public ExportItem setConvert(String convert) {
        this.convert = convert;
        return this;
    }
    
    public short getColor() {
        return color;
    }
    
    public ExportItem setColor(short color) {
        this.color = color;
        return this;
    }
    
    public String getReplace() {
        return replace;
    }
    
    public ExportItem setReplace(String replace) {
        this.replace = replace;
        return this;
    }
}
