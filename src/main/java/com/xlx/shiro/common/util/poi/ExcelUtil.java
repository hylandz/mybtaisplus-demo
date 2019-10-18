package com.xlx.shiro.common.util.poi;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.xlx.shiro.common.annotation.ExportConfig;
import com.xlx.shiro.common.handler.IExportHandler;
import com.xlx.shiro.common.util.poi.convert.IExportConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel工具类
 *
 * @author xielx at 2019/10/18 12:08
 */
@Slf4j
public class ExcelUtil {
    
    
    /**
     * 反射使用
     */
    private Class<?> aClass = null;
    /**
     * response
     */
    private HttpServletResponse response = null;
    
    /**
     * 每个sheet最多的数据为1W
     */
    private Integer maxRecordPerSheet = 10000;
    
    /**
     * 缓存数据格式器实例,避免多次使用反射进行实例化
     */
    private Map<String, IExportConvert> convertMap = new HashMap<>();
    
    
    protected ExcelUtil() {
    }
    
    
    /**
     * 创建实例
     *
     * @param aClass Class
     * @return 工具类实例
     */
    static ExcelUtil build(Class<?> aClass) {
        return new ExcelUtil(aClass);
    }
    
    public static ExcelUtil export(Class<?> aClass, HttpServletResponse response) {
        return new ExcelUtil(aClass, response);
    }
    
    /**
     * 设置sheet存储量
     *
     * @param maxRecordPerSheet 最大
     * @return ExcelUtil
     */
    public ExcelUtil setMaxRecordPerSheet(Integer maxRecordPerSheet) {
        this.maxRecordPerSheet = maxRecordPerSheet;
        return this;
    }
    
    /**
     * 构造
     */
    private ExcelUtil(Class<?> aClass, HttpServletResponse response) {
        this.aClass = aClass;
        this.response = response;
    }
    
    private ExcelUtil(Class<?> aClass) {
        this(aClass, null);
    }
    
    
    /**
     * excel下载
     *
     * @param data     表单数据对象集合
     * @param fileName excel文件名
     * @param handler  转换器
     * @param out      输出流
     * @return true:下载成功
     */
    private Boolean toExcel(List<?> data, String fileName, IExportHandler handler, OutputStream out) {
        requireBuilderParams();
        if (data == null || data.isEmpty()) {
            return false;
        }
        
        
        // 1.获取excel表的列对象集合
        ExportConfig exportConfig;
        //
        ExportItem exportItem;
        // excel表列对象集合
        List<ExportItem> exportItemList = new ArrayList<>();
        
        // Field[]数组,返回此Class对象表示的类或接口所声明的所有字段(反射获取对应的对象)
        for (Field field : aClass.getDeclaredFields()) {
            // 获取元素对应注解类
            exportConfig = field.getAnnotation(ExportConfig.class);
            if (exportConfig != null) {
                // field:表示使用实体类名称,否则使用注解类值
                exportItem = new ExportItem().setField(field.getName())
                                     .setDisplay("field".equals(exportConfig.value()) ? field.getName() : exportConfig.value())
                                     .setWidth(exportConfig.width())
                                     .setConvert(exportConfig.convert())
                                     .setColor(exportConfig.color())
                                     .setReplace(exportConfig.replace());
                exportItemList.add(exportItem);
            }
        }
        
        
        // 2.创建工作簿,
        SXSSFWorkbook workbook = POIUtil.newSXSSFWorkbook();
        // sheet数量(类似分页)
        double sheetNum = Math.ceil(data.size()) / maxRecordPerSheet;
        
        int index = 0;
        while (index <= (sheetNum == 0.0 ? sheetNum : sheetNum - 1)) {
            
            // 2.1创建表头(excel表的标题)
            SXSSFSheet sheet = POIUtil.newSXSSFSheet(workbook, fileName + (index == 0 ? "" : "_" + index));
            SXSSFRow row = POIUtil.newSXSSFRow(sheet, 0);
            for (int i = 0; i < exportItemList.size(); i++) {
                SXSSFCell cell = POIUtil.nweSXSSFCell(row, i);
                // 设置标题宽度
                POIUtil.setColumnWidth(sheet, i, exportItemList.get(i).getWidth(), exportItemList.get(i).getDisplay());
                // 单元格填充内容(设置列名)
                cell.setCellValue(exportItemList.get(i).getDisplay());
                // 样式
                CellStyle style = handler.headCellStyle(workbook);
                if (style != null) {
                    cell.setCellStyle(style);
                }
            }
            
            // 数据行
            SXSSFRow bodyRow;
            String cellVal = "";
            SXSSFCell bodyCell;
            // 数据行样式
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            style.setFont(font);
            
            // 2.2数据填充
            if (!data.isEmpty()) {
                int startNo = index * maxRecordPerSheet;
                int endNo = Math.min(startNo + maxRecordPerSheet, data.size());
                
                int i = startNo;
                while (i < endNo) {
                    bodyRow = POIUtil.newSXSSFRow(sheet, i + 1 - startNo);
                    for (int j = 0; j < exportItemList.size(); j++) {
                        // 处理设置了replace注解的值
                        String replace = exportItemList.get(j).getReplace();
                        try {
                            cellVal = BeanUtils.getProperty(data.get(i), exportItemList.get(i).getField());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            log.error("getProperty()失败:{}", cellVal);
                        }
                        
                        if (!"".equals(replace) && regexPhone(cellVal)) {
                            // cellVal = cellVal.substring(0,3) + replace + cellVal.substring(7);
                            cellVal = new StringBuilder(cellVal).replace(3, 7, replace).toString();
                        }
                        
                        // 处理convert注解
                        String convert = exportItemList.get(j).getConvert();
                        if (!"".equals(convert)) {
                            convertCellValue(cellVal, convert);
                        }
                        
                        // 设置数据单元格宽度
                        POIUtil.setColumnWidth(sheet,j,exportItemList.get(j).getWidth(),cellVal);
                        bodyCell = POIUtil.nweSXSSFCell(bodyRow, j);
                        bodyCell.setCellValue("".equals(cellVal) ? null : cellVal);
                        bodyCell.setCellStyle(style);
                    }
                    i++;
                }
            }
            index++;
        }
        return true;
    }
    
    
    /**
     * 转换
     * 1.性别转换
     *  注解 s:1=男,2=女
     *  实体属性 sex=1
     *  如果注解的1==sex的1,最后显示男
     * @param entityVal 实体类的值
     * @param format  注解值
     * @return str
     */
    private String convertCellValue(Object entityVal, String format) {
        
        // 性别转换
        String proto = format.substring(0, 1);
        if ("s".equalsIgnoreCase(proto)) {
            // s:1=男,2=女
            String[] split = format.split(":")[1].split(",");
            for (String s : split) {
                String[] split1 = s.split("=");
                if (split1[0].equals(entityVal)) {
                    return split1[1];
                }
                
                return "保密";
            }
        }
        
        // 时间处理
        if ("c".equalsIgnoreCase(proto)) {
            String clz = format.split(":")[1];
            IExportConvert iExportConvert = convertMap.get(clz);
            if (iExportConvert == null) {
                try {
                    iExportConvert = (IExportConvert) Class.forName(clz).newInstance();
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                    log.error("Class.forName(clz).newInstance()异常:{}", e.getMessage());
                }
                
                convertMap.put(clz, iExportConvert);
            }
            
            if (convertMap.size() > 10) {
                convertMap.clear();
            }
            return iExportConvert.handler(entityVal);
        }
        
        return String.valueOf(entityVal);
    }
    
    
    private void requireBuilderParams() {
        if (aClass == null) {
            throw new IllegalArgumentException("请使用common.util.poi.ExcelUtil.build(Class<?> aClass)构造参数");
        }
    }
    
    
    private boolean regexPhone(String number) {
        String pattern = "\\d{11}";
        return number.matches(pattern);
    }
}