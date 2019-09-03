package com.xlx.shiro.system.dto;

import com.xlx.shiro.common.exception.CustomizeExceptionEnum;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 统一操作结果
 *
 * @author xielx on 2019/7/13
 */

@Data
public class ResultDTO implements Serializable {

  private static final String SUCCESS = "OK";
  private static final String FAILURE = "ERROR";

  // 状态码
  private Integer code;

  // 提示信息
  private String message;

  //携带参数
  private Object data;


  public ResultDTO(Integer code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }


  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
  }

  /**
   * 默认成功
   */
  public static ResultDTO success(){
    return new ResultDTO(200,ResultDTO.SUCCESS,null);
  }

  /**
   * 自定义提示信息
   */
  public static ResultDTO success(String message){
    return new ResultDTO(200,message,null);
  }

  /**
   * 携带参数
   */
  public static ResultDTO success(Object object){
    return new ResultDTO(200,ResultDTO.SUCCESS,object);
  }

  /**
   * 自定义信息+携带参数
   */
  public static ResultDTO success(String message,Object object){
    return new ResultDTO(200,message,object);
  }

  /**
   * 默认失败
   */
  public static ResultDTO failed(){
    return new ResultDTO(400,ResultDTO.FAILURE,null);
  }


  /**
   * 自定义状态码
   */
  public static ResultDTO failed(Integer code){
    return new ResultDTO(code,ResultDTO.FAILURE,null);
  }

  /**
   * 自定义信息
   */
  public static ResultDTO failed(String message){
    return new ResultDTO(400,message,null);
  }

  /**
   * 状态码 + 提示信息
   */
  public static ResultDTO failed(Integer code,String message){
    return new ResultDTO(code,message,null);
  }

  /**
   * 枚举-异常
   *
   * @param errorCodeEnum 枚举,不需要自定义了
   * @return
   */
  public static ResultDTO failed(CustomizeExceptionEnum errorCodeEnum) {
    return failed(errorCodeEnum.getCode(), errorCodeEnum.getMessage());
  }


}
