package com.xlx.shiro.common.util.poi;

import com.xlx.shiro.common.constant.POIConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Apache POI SXSS相关API的简易封装
 * 工作簿,sheet工作表,单元格 行对象实例化,excel下载
 *
 * @author xielx at 2019/10/17 22:02
 */
@Slf4j
public class POIUtils {
    
    
    protected POIUtils() {}
    
    /**
     * 一个sheet的最大行数
     */
    private static final int DEFAULT_ROW_ACCESS_WINDOW_SIZE = 10000;
    
    /**
     * 创建SXSSFWorkbook工作簿,默认
     * @return SXSSFWorkbook
     */
    static SXSSFWorkbook newSXSSFWorkbook() {
        return new SXSSFWorkbook(DEFAULT_ROW_ACCESS_WINDOW_SIZE);
    }
    
    /**
     * 创建自定义SXSSFWorkbook工作簿
     *
     * @param rowAccessWindowSize 记录数
     * @return SXSSFWorkbook
     */
    private static SXSSFWorkbook newSXSSFWorkbook(int rowAccessWindowSize) {
        return new SXSSFWorkbook(rowAccessWindowSize);
    }
    
    
    /**
     * 创建sheet工作表
     *
     * @param workbook  工作簿
     * @param sheetName 单元名称
     * @return SXSSFSheet
     */
     static SXSSFSheet newSXSSFSheet(SXSSFWorkbook workbook, String sheetName) {
        return workbook.createSheet(sheetName);
    }
    
    /**
     * 创建行
     *
     * @param sheet  单元
     * @param rownum 行位置
     * @return SXSSFRow
     */
     static SXSSFRow newSXSSFRow(SXSSFSheet sheet, int rownum) {
        return sheet.createRow(rownum);
    }
    
    /**
     * 创建单元格
     *
     * @param row    行
     * @param column 列位置
     * @return SXSSFCell
     */
     static SXSSFCell nweSXSSFCell(SXSSFRow row, int column) {
        return row.createCell(column);
    }
    
    /**
     * 设置单元格(列)宽度
     *
     * @param sheet 单元对象
     * @param index 单元格索引
     * @param width 宽度,-1为自适应
     * @param value 填充的内容(宽度通过填充内容计算)
     */
    static void setColumnWidth(SXSSFSheet sheet, int index, int width, String value) {
        // 自动
        if (width == -1 && !StringUtils.isEmpty(value)) {
            sheet.setColumnWidth(index, value.length() * 512);
        } else {
            // 手动调整
            width = (width == -1) ? 200 : width;
            sheet.setColumnWidth(index, width * 35);
        }
    }
    
    /**
     * 浏览器下载
     * @param workbook 工作簿对象
     * @param response 响应
     * @param fileName 文件名
     * @param out 输出流
     */
    static void writeFromLocalBrowser(SXSSFWorkbook workbook, HttpServletResponse response, String fileName, OutputStream out) throws IOException {
        // ?
        ZipSecureFile.setMinInflateRatio(0L);
        
            // 文件名字符编码
            String encode = URLEncoder.encode(String.format("%s%s", fileName, POIConstant.XLSX_SUFFIX), "UTF-8");
            // response对象不为空,响应到浏览器下载
            if (response != null) {
                response.setHeader("Content-disposition", "attachment;fileName=" + encode);
                response.setContentType("multipart/form-data");
                response.setCharacterEncoding("utf-8");
                if (out == null) {
                    out = response.getOutputStream();
                }
                workbook.write(out);
                out.flush();
                out.close();
            }
        
    }
    
    /**
     * 校验文件
     * @param file File
     */
    public static void checkExcellFile(File file){
        if (file == null || !file.exists()){
            throw new IllegalArgumentException("excel文件不存在");
        }
    
        String path = file.getAbsolutePath();
        if (!path.endsWith(POIConstant.XLSX_SUFFIX)){
            throw new IllegalArgumentException("抱歉,目前ExcelKit仅支持.xlsx格式的文件");
        }
    }
    
    /**
     * 文件名称校验
     * @param file 文件名
      */
    public static void checkExcellFile(String file){
        // ?
    }
}
