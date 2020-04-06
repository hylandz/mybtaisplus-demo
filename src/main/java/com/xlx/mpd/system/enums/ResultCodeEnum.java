package com.xlx.mpd.system.enums;

import lombok.Getter;

/**
 * 统一结果状态枚举
 *
 * @author xielx at 2020/3/26 20:47
 */
@Getter
public enum ResultCodeEnum {
    
    SUCCESS(true,20000,"成功"),
    FAILED(false,20001,"失败"),
    UNKNOWN_ERROR(false,20002,"系统异常"),
    PARAM_ERROR(false,20003,"参数错误"),
    NULL_POINTER(false,20004,"空指针异常,可能参数或对象为空"),
    DATA_NOT_FOUND(false,20005,"您查找的数据不存在");
    
    
    /**
     * 是否响应成功
     */
    private Boolean isSuccess;
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态码描述
     */
    private String message;
    
    ResultCodeEnum(Boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
    
}
