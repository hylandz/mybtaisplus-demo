package com.xlx.shiro.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

/**
 * excel工具类
 *
 * @author xielx on 2019/7/7
 */
public class ExcelDemo {


  /**
   * 创建一个工作簿
   * @param sheetName 工作表名称
   * @param title excel标题
   * @param workbook excel内容
   * @return wb
   */
  public static HSSFWorkbook getHHSFWorkBook(String sheetName, String[] title, String[][] deptArray,HSSFWorkbook workbook){

    long start = System.currentTimeMillis();
    //1.创建一个工作簿对象
    if (workbook == null){
      workbook = new HSSFWorkbook();
    }

    // 2.工作簿workBook添加一个sheet工作表
    HSSFSheet sheet = workbook.createSheet(sheetName);

    // 3.填充excel表标题,第一行(0)
    HSSFRow row =  sheet.createRow(0);

    //4. 设置单元格样式,水平居中
    HSSFCellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(HorizontalAlignment.CENTER);

    //5.创建单元格(列)cell
    HSSFCell cell;

    //创建标题,居中
    for (int i = 0; i < title.length; i++) {
      cell = row.createCell(i);
      cell.setCellValue(title[i]);
      cell.setCellStyle(cellStyle);
    }

    // 填充excel表内容,第二行始(1)
    for (int i = 0; i < deptArray.length ; i++) {
      row = sheet.createRow(i + 1);
      for (int j = 0; j < deptArray[i].length; j++) {//[["1001","研发部","0"],["1002","测试部","  1"]]

        row.createCell(j).setCellValue(deptArray[i][j]);
      }
    }

    System.out.println("耗时:" + (System.currentTimeMillis() - start));
    return workbook;
  }
}
