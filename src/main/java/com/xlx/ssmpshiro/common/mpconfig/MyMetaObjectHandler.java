package com.xlx.ssmpshiro.common.mpconfig;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * 公共字段填充
 * @author xielx on 2019/7/17
 */
public class MyMetaObjectHandler extends MetaObjectHandler {

  /**
   * 插入时填充
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
    setFieldValByName("gmtCreate",new Date(),metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {

    //更新填充
    System.out.println("*************************");
    System.out.println("update fill");
    System.out.println("*************************");

    // 填充当前日期,可以多个
    setFieldValByName("gmtModified",new Date(),metaObject);
  }
}
