package com.xlx.shiro.common.handler;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * 导出Excel设置接口。
 * @author xielx at 2019/10/18 14:39
 */
public interface IExportHandler {
    
    
    /**
     * 设置表头样式
     * @param workbook 工作簿对象
     * @return CellStyle
     */
    CellStyle headCellStyle(SXSSFWorkbook workbook);
    
    
    /**
     * 设置导出的文件名
     * @param name 文件名
     * @return String
     */
    String exportFileName(String name);
}
