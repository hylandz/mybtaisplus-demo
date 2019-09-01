package com.xlx.shiro.common.advice;

import com.xlx.shiro.system.dto.ResultDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * spring-boot的全局异常处理
 *
 * @author xielx on 2019/7/14
 */
@RestControllerAdvice
public class GlobalExceptionController {


  @ExceptionHandler(Exception.class)
  public ResultDTO errorHandler(HttpServletRequest request,Throwable t){
    HttpStatus status = getStatus(request);
    if (status.is4xxClientError()){
      return ResultDTO.failed(status.value(),t.getMessage());
    }else if (status.is5xxServerError()){
      return ResultDTO.failed(status.value(),t.getMessage());
    }else {
      return ResultDTO.failed(status.value(),t.getMessage());
    }
  }

  /**
   * 获取响应的状态码
   * @param request re
   * @return .
   */
  private HttpStatus getStatus(HttpServletRequest request){
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    try{
      return HttpStatus.valueOf(statusCode);
    }catch (Exception e){
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
  }
}
