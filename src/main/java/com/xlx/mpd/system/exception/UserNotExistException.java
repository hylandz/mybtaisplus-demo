package com.xlx.mpd.system.exception;

import com.xlx.mpd.system.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 用户异常类
 *
 * @author xielx at 2020/3/26 21:17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserNotExistException extends RuntimeException {
    
    private Integer code;
    
    
    public UserNotExistException(String message){
        super(message);
    }
    
    public UserNotExistException(Integer code, String message){
        super(message);
        this.code = code;
    }
    
    public UserNotExistException(ResultCodeEnum codeEnum){
        super(codeEnum.getMessage());
        this.code = codeEnum.getCode();
    }
    
    @Override
    public String toString() {
        return "UserNotExist{" +
                       "code=" + code +
                       "message=" + getMessage() +
                       '}';
    }
}
