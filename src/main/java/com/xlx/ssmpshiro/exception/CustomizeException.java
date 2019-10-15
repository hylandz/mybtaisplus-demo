package com.xlx.ssmpshiro.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author xielx on 2019/7/13
 */
public class CustomizeException extends RuntimeException implements ICustomizeException{

  // 状态码
  @Getter
  @Setter
  private Integer code;

  // 提示信息
  @Getter
  @Setter
  private String message;


  public CustomizeException(){
    super();
  }

  public CustomizeException(Integer code,String message){
    this.code = code;
    this.message = message;
  }

  public CustomizeException(ICustomizeException iCustomize){
    this.code = iCustomize.getCode();
    this.message = iCustomize.getMessage();
  }

  public CustomizeException(String message){
    super(message);
  }
}
