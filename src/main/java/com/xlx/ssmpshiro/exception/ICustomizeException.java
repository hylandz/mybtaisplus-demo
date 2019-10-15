package com.xlx.ssmpshiro.exception;

/**
 * 自定义的异常接口
 * @author: xielx on 2019/7/13
 */
public interface ICustomizeException {

  /**
   * 获取状态码
   */
  Integer getCode();

  /**
   * 获取提示信息
   */
  String getMessage();
}
