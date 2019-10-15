package com.xlx.ssmpshiro.system.dto;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 统一操作结果
 *
 * @author xielx on 2019/7/13
 */

@Data
public class ResultDTO {

  private static final String SUCCESS = "操作成功";
  private static final String FAILURE = "操作失败";

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
   * @return dto
   */
  public static ResultDTO success(){
    return new ResultDTO(200,ResultDTO.SUCCESS,null);
  }

  /**
   * 自定义提示信息
   * @param message msg
   * @return dto
   */
  public static ResultDTO success(String message){
    return new ResultDTO(200,message,null);
  }

  /**
   * 携带参数
   * @param object obj
   * @return dto
   */
  public static ResultDTO success(Object object){
    return new ResultDTO(200,ResultDTO.SUCCESS,object);
  }

  /**
   * 自定义信息+携带参数
   * @param message msg
   * @param object obj
   * @return dto
   */
  public static ResultDTO success(String message,Object object){
    return new ResultDTO(200,message,object);
  }

  /**
   * 默认失败
   * @return
   */
  public static ResultDTO failed(){
    return new ResultDTO(400,ResultDTO.FAILURE,null);
  }

  public static ResultDTO failed(Integer code){
    return new ResultDTO(code,ResultDTO.FAILURE,null);
  }

  public static ResultDTO failed(String message){
    return new ResultDTO(400,message,null);
  }

  /**
   *
   * @param code
   * @param message
   * @return
   */
  public static ResultDTO failed(Integer code,String message){
    return new ResultDTO(code,message,null);
  }



}
