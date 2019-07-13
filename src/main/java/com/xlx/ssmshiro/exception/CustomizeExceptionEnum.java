package com.xlx.ssmshiro.exception;

/**
 * @author: xielx on 2019/7/13
 */
public enum CustomizeExceptionEnum implements ICustomizeException{

  NETWORK_ERROR(1001, "网络错误请重试");

  private Integer code;
  private String message;

  CustomizeExceptionEnum(Integer code,String message){
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
