package com.xlx.mpd.common.mpconfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 公共字段填充
 * @author xielx on 2019/7/17
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

  /**
   * 插入时填充
   * FieldFill.INSERT
   */
  @Override
  public void insertFill(MetaObject metaObject) {

    System.out.println("*************************");
    System.out.println("insert fill");
    System.out.println("*************************");

    // 测试下划线
    Object testType = getFieldValByName("gmtCreate", metaObject);//mybatis-plus版本2.0.9+
    System.out.println("testType=" + testType);

    // 填充当前日期
    this.setFieldValByName("gmtCreate",LocalDateTime.now(),metaObject);
  }
  
  /**
   * 更新时填充
   * FieldFill.UPDATE
   * @param metaObject 元对象
   */
  @Override
  public void updateFill(MetaObject metaObject) {

    //更新时填充
    System.out.println("*************************");
    System.out.println("update fill");
    System.out.println("*************************");

    // 填充当前日期,可以多个
    this.setFieldValByName("gmtModified",  LocalDateTime.now(),metaObject);
  }
}
