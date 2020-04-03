package com.xlx.mpd.common.advice;

import com.xlx.mpd.system.dto.ResultDTO;
import com.xlx.mpd.system.enums.ResultCodeEnum;
import com.xlx.mpd.system.exception.UserNotExistException;
import com.xlx.mpd.system.util.ExceptionLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常捕获处理
 *
 * @author xielx at 2020/3/26 21:23
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    
    /**
     *  未知异常处理
     * @param e Exception
     * @return ResultDTO
     */
    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e){
        // log.error("未知异常:{}" ,e.getMessage());
        log.error("未知异常:{}" , ExceptionLogUtil.getMessage(e));
        return ResultDTO.error();
    }
    
    
    
    /**
     * 处理空指针异常
     * @param e 空指针异常
     * @return ResultDTO
     */
    @ExceptionHandler(NullPointerException.class)
    public ResultDTO handleNullPointerException(NullPointerException e){
        log.error("空指针异常:{}" ,e.getMessage());
        return ResultDTO.setResult(ResultCodeEnum.NULL_POINTER);
    }
    
    /**
     * 自定义异常处理
     * @param e 用户不存在异常
     * @return ResultDTO
     */
    @ExceptionHandler(UserNotExistException.class)
    public ResultDTO handleUserNotException(UserNotExistException e){
        log.error("用户异常:{}" ,e.getMessage());
        return ResultDTO.error().code(e.getCode()).message(e.getMessage());
    }
    
    
    
}
