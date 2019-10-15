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
public class LogLogin extends Model<LogLogin> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录账户
     */
    private String account;
    /**
     * 登录ip
     */
    private String ip;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录信息
     */
    private String message;
    /**
     * 创建时间/登录时间
     */
    private Date gmtCreate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
