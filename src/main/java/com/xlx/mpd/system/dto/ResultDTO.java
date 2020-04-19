package com.xlx.mpd.system.dto;

import com.xlx.mpd.system.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果类
 *
 * @author xielx at 2020/3/26 20:54
 */
@Data
@ApiModel("统一返回结果类")
public class ResultDTO {
    
    /**
     * 是否响应成功,true:成功
     */
    @ApiModelProperty(value = "是否响应成功",example = "true")
    private Boolean success;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码",example = "10002")
    private Integer code;

    /**
     * 状态码描述
     */
    @ApiModelProperty(value = "状态码描述",example = "查询成功")
    private String message;
    
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据",example = "{user:[{'userId':1,'deptId':2,'nickName':'zs'}]}")
    private Map<String,Object> data = new HashMap<>();
    
    // 私有构造
    private ResultDTO(){}
    
    /**
     * 通用成功
     * @return ResultDTO
     */
    public static ResultDTO ok(){
        ResultDTO result = new ResultDTO();
        result.setSuccess(ResultCodeEnum.SUCCESS.getIsSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        
        return result;
    }
    
    /**
     * 通用失败
     * @return ResultDTO
     */
    public static ResultDTO error(){
        ResultDTO result = new ResultDTO();
        result.setSuccess(ResultCodeEnum.FAILED.getIsSuccess());
        result.setCode(ResultCodeEnum.FAILED.getCode());
        result.setMessage(ResultCodeEnum.FAILED.getMessage());
        
        return result;
    }
    
    /**
     * 设置结果(返回状态,状态码,状态码描述)
     * @param codeEnum 枚举参数
     * @return ResultDTO
     */
    public static ResultDTO setResult(ResultCodeEnum codeEnum){
        ResultDTO result = new ResultDTO();
        result.setSuccess(codeEnum.getIsSuccess());
        result.setCode(codeEnum.getCode());
        result.setMessage(codeEnum.getMessage());
        return result;
    }
    
    
    /**-------------------------- 链式编程,返回类本身--------------------------**/

    // 自定义返回结果
    public ResultDTO success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    
    // 自定义状态信息
    public ResultDTO message(String message){
        this.setMessage(message);
        return this;
    }
    
    // 自定义状态码
    public ResultDTO code(Integer code){
        this.setCode(code);
        return this;
    }
    
    // 自定义响应数据
    public ResultDTO data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
    
    // 通用响应数据
    public ResultDTO data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    
}
