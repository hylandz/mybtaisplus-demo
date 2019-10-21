package com.xlx.shiro.common.util.poi;

import com.xlx.shiro.common.annotation.ExportConfig;
import com.xlx.shiro.common.handler.IExportHandler;
import com.xlx.shiro.common.util.poi.convert.IExportConvert;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * excel工具类
 *
 * @author xielx at 2019/10/18 12:08
 */
@Slf4j
public class ExcelUtils {
    
    
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
    
    
    protected ExcelUtils() {
    }
    
    
    /**
     * 创建实例
     *
     * @param aClass Class
     * @return 工具类实例
     */
    static ExcelUtils build(Class<?> aClass) {
        return new ExcelUtils(aClass);
    }
    
    public static ExcelUtils export(Class<?> aClass, HttpServletResponse response) {
        return new ExcelUtils(aClass, response);
    }
    
    /**
     * 设置sheet存储量
     *
     * @param maxRecordPerSheet 最大
     * @return ExcelUtils
     */
    public ExcelUtils setMaxRecordPerSheet(Integer maxRecordPerSheet) {
        this.maxRecordPerSheet = maxRecordPerSheet;
        return this;
    }
    
    /**
     * 构造
     */
    private ExcelUtils(Class<?> aClass, HttpServletResponse response) {
        this.aClass = aClass;
        this.response = response;
    }
    
    private ExcelUtils(Class<?> aClass) {
        this(aClass, null);
    }
    
    /**
     * excel下载
     *
     * @param data     表单数据对象集合
     * @param sheetName sheet名
     * @param out      输出流
     * @return true:下载成功
     */
    public boolean toExcel(List<?> data, String sheetName,OutputStream out){
        return toExcel(data, sheetName, new IExportHandler() {
            @Override
            public CellStyle headCellStyle(SXSSFWorkbook workbook) {
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillBackgroundColor((short) 12);
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);// 填充模式
                cellStyle.setBorderTop(BorderStyle.THIN);// 上边框为细边框
                cellStyle.setBorderRight(BorderStyle.THIN);// 右边框为细边框
                cellStyle.setBorderBottom(BorderStyle.THIN);// 下边框为细边框
                cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框为细边框
                cellStyle.setAlignment(HorizontalAlignment.CENTER); //对齐
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
                cellStyle.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
    
                Font font = workbook.createFont();
                font.setFontHeightInPoints((short) 12);// 字体大小
                font.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
                // 应用标题字体到标题样式
                cellStyle.setFont(font);
                return cellStyle;
            }
    
            @Override
            public String exportFileName(String name) {
                return String.format("导出-%s-%s",sheetName,System.currentTimeMillis());
            }
        },out);
    }
    
    
    /**
     * excel下载
     *
     * @param data     表单数据对象集合
     * @param sheetName sheet名
     * @param handler  转换器
     * @param out      输出流
     * @return true:下载成功
     */
    private Boolean toExcel(List<?> data, String sheetName, IExportHandler handler, OutputStream out) {
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
        SXSSFWorkbook workbook = POIUtils.newSXSSFWorkbook();
        // sheet数量(类似分页)
        double sheetNum = Math.ceil(data.size() / maxRecordPerSheet);
        
        int index = 0;
        while (index <= (sheetNum == 0.0 ? sheetNum : sheetNum - 1)) {
            
            // 2.1创建表头(excel表的标题)
            SXSSFSheet sheet = POIUtils.newSXSSFSheet(workbook, sheetName + (index == 0 ? "" : "_" + index));
            SXSSFRow row = POIUtils.newSXSSFRow(sheet, 0);
            for (int i = 0; i < exportItemList.size(); i++) {
                SXSSFCell cell = POIUtils.nweSXSSFCell(row, i);
                // 设置标题宽度
                POIUtils.setColumnWidth(sheet, i, exportItemList.get(i).getWidth(), exportItemList.get(i).getDisplay());
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
            
            // 2.2数据填充(横向)
            if (!data.isEmpty()) {
                int startNo = index * maxRecordPerSheet;
                int endNo = Math.min(startNo + maxRecordPerSheet, data.size());
                
                int i = startNo;
                while (i < endNo) {
                    bodyRow = POIUtils.newSXSSFRow(sheet, i + 1 - startNo);
                    for (int j = 0; j < exportItemList.size(); j++) {
                        String replace = exportItemList.get(j).getReplace();
                        try {
                            // getProperty(bean,name):获取bean属性里对应的name(field)的值
                            cellVal = BeanUtils.getProperty(data.get(i), exportItemList.get(j).getField());
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            log.error("getProperty()失败:{}", cellVal);
                        }
    
                        // 处理设置了replace注解的值
                        if (!"".equals(replace)) {
                            // cellVal = cellVal.substring(0,3) + replace + cellVal.substring(7);
                            cellVal = new StringBuilder(cellVal).replace(3, 7, replace).toString();
                        }
                        
                        // 处理convert注解
                        String convert = exportItemList.get(j).getConvert();
                        if (!"".equals(convert)) {
                            cellVal = convertCellValue(cellVal, convert);
                        }
                        
                        // 设置数据单元格宽度
                        POIUtils.setColumnWidth(sheet,j,exportItemList.get(j).getWidth(),cellVal);
                        bodyCell = POIUtils.nweSXSSFCell(bodyRow, j);
                        bodyCell.setCellValue(Objects.equals("",cellVal) ? null : cellVal);
                        bodyCell.setCellStyle(style);
                    }
                    i++;
                }
            }
            index++;
        }
    
        // 下载Excel
        try {
            POIUtils.writeFromLocalBrowser(workbook,response,handler.exportFileName(sheetName),out);
        } catch (IOException e) {
            log.error("下载excel文件失败:{}",e.getMessage());
            return false;
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
            // s:1=男,0=女
            String[] split = format.split(":")[1].split(",");
            for (String s : split) {
                String[] split1 = s.split("=");
                if (split1[0].equals(entityVal)) {
                    return split1[1];
                }
            }
            return "保密";
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
            throw new IllegalArgumentException("请使用common.util.poi.ExcelUtils.build(Class<?> aClass)构造参数");
        }
    }
    
    
    private boolean regexPhone(String number) {
        String pattern = "\\d{11}";
        return number.matches(pattern);
    }
}