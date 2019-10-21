package com.xlx.shiro.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.UUID;

/**
 * 导出excel测试
 *
 * @author xielx on 2019/7/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ExportExcelTest {
    
    
    @Test
    public void testExportDeptExcel() throws IOException {
        
        
        String sheetName = "department";
        String[] title = {"主键", "部门名称", "父节点", "完整节点", "可用", "创建时间", "修改时间"};
        String[][] dept = {{"1", "开发部", "0", "0/1/", "1", "1235813", "13256336"}, {"2", "测试部", "0", "0/2/", "1", "12358635", "132566326"}};
        
        //导出excel存放路径
        File excelPath = new File("D:\\excelFile");
        if (!excelPath.exists()) {
            excelPath.mkdir();
        }
        
        //创建一个excel文件
        String path = UUID.randomUUID().toString() + "_部门表" + ".xls";
        File exFile = new File(excelPath, path);
        System.out.println("文件路径:" + exFile.getAbsolutePath());
        HSSFWorkbook workbook = ExcelDemo.getHHSFWorkBook(sheetName, title, dept, null);
        
        OutputStream out = new FileOutputStream(exFile);
        workbook.write(out);
        out.flush();
        workbook.close();
        out.close();
    }
}
