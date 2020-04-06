package com.xlx.mpd.common.mpconfig;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.xlx.mpd.system.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

/**
 * 公共字段填充
 * @author xielx on 2019/7/17
 */
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

  /**
   * 插入时填充
   * FieldFill.INSERT
   */
  @Override
  public void insertFill(MetaObject metaObject) {

    log.info("*************************");
    log.info("插入数据时填充");
    log.info("*************************");

    // 测试下划线
    Object testType = this.getFieldValByName("gmtCreate", metaObject);//mybatis-plus版本2.0.9+
    log.info("testType={}" , testType);

    // 填充当前日期
    this.setFieldValByName("gmtCreate",DateTimeUtil.getEpochSecondOfNow(),metaObject);
  }
  
  /**
   * 更新时填充
   * FieldFill.UPDATE
   * @param metaObject 元对象
   */
  @Override
  public void updateFill(MetaObject metaObject) {

    //更新时填充
    log.info("*************************");
    log.info("更新数据时填充");
    log.info("*************************");
  
    // 填充当前日期,可以多个
    this.setFieldValByName("gmtModified", DateTimeUtil.getMilliSecondOfNow(),metaObject);
  }
}
