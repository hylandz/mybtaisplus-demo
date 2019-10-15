package com.xlx.ssmpshiro.exception;

/**
 * @author: xielx on 2019/7/13
 */
public enum ErrorCodeEnum implements ICustomizeException{

  NETWORK_ERROR(1001, "网络错误请重试"),
  LOGIN_ERROR(1002,"用户名或密码错误"),
  ACCOUNT_LOCKED_ERROT(1003,"用户被锁,请联系管理员"),
  CAPTCHA_CODE_ERROR(1004,"验证码错误");

  private Integer code;
  private String message;

  ErrorCodeEnum(Integer code, String message){
    this.code = code;
    this.message = message;
  }
  @Override
  public Integer getCode() {
    return this.code;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
