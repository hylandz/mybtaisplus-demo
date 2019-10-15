package com.xlx.ssmpshiro.system.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogError extends Model<LogError> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * uri
     */
    private String requestUri;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 代理
     */
    private String userAgent;
    /**
     * ip
     */
    private String ip;
    /**
     * ip位置
     */
    private String ipLocation;
    /**
     * 操作信息
     */
    private String message;
    /**
     * 创建时间
     */
    private Date gmtCreate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
