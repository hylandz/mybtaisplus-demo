package com.xlx.shiro.common.handler;

import com.xlx.shiro.common.exception.FileDownLoadException;
import com.xlx.shiro.common.util.HttpUtils;
import com.xlx.shiro.system.dto.ResultDTO;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * spring-boot的全局异常处理
 *
 * @author xielx on 2019/7/14
 */
@RestControllerAdvice
// @Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {
    
    /**
     * ajax请求异常
     * @param request 客户端请求
     * @return 403.html
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public Object handleAuthorizationException(HttpServletRequest request) {
        if (HttpUtils.isAjaxRequest(request)) {
            return ResultDTO.failed("暂无权限,请联系管理员");
        }
        
        return new ModelAndView("/error/403");
    }
    
    
    /**
     * 会话失效
     * @return login.html
     */
    @ExceptionHandler(value = ExpiredSessionException.class)
    public String handleExpiredSessionException(){
        return "/login";
    }
    
    
    /**
     * 文件下载异常
     * @param e  FileDownLoadException
     * @return result
     */
    @ExceptionHandler(value = FileDownLoadException.class)
    public ResultDTO handleExpiredSessionException(FileDownLoadException e){
        return ResultDTO.failed(e.getMessage());
    }
    
    
    
    
    
    
    
    
    /**
     * 获取响应的状态码
     * @param request re
     * @return .
     */
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
