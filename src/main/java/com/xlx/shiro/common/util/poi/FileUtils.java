package com.xlx.shiro.common.util.poi;

import com.xlx.shiro.system.dto.ResultDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author xielx at 2019/10/17 23:35
 */
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);
    private static final String FILE_SUFFIX = ".xlsx";
    private static final String FILE_PATH = "file/";
    
    /**
     * 生成系统文件名
     * @param name 文件名
     * @return UUID_文件名
     */
    private static String makeFileName(String name){
        return UUID.randomUUID().toString() + "_" +name;
    }
    
    /**
     * 创建excel
     * @param fileName 文件名
     * @param list 导出数据
     * @param clz 数据所属类
     * @return true:ok
     */
    public static ResultDTO createExcel(String fileName, List<?> list, Class<?> clz, HttpServletResponse response){
        if (list.isEmpty()){
            return ResultDTO.failed("导出数据为空!");
        }
        
        boolean isOk = false;
        String name = fileName + FILE_SUFFIX;
    
        name = makeFileName(name);
        File fileDir = new File(FILE_PATH);
        if (!fileDir.exists()){
            fileDir.mkdir();
        }
        
        String path = FILE_PATH + name;
        try {
            FileOutputStream fos = new FileOutputStream(path);
            //isOk = ExcelUtils.build(clz).toExcel(list,fileName,fos);
            isOk = ExcelUtils.export(clz,response).toExcel(list,fileName,fos);
        } catch (FileNotFoundException e) {
            log.error("文件 [{}]未找到:{}",fileName,e.getMessage());
        }
    
        if (isOk){
            return ResultDTO.success(fileName);
        }
        return ResultDTO.failed("导出Excel失败，请联系网站管理员!");
        
    }
}
