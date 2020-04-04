package com.xlx.mpd.system.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;
import org.apache.ibatis.type.LocalDateTypeHandler;
import org.springframework.format.annotation.DateTimeFormat;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.UPDATE;

/**
 * <p>
 * 
 * </p>
 *
 * @author xlx
 * @since 2020-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "sys_user" ,autoResultMap = true)
public class User extends Model<User> {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 昵称
     */
    private String avatarName;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 帐号
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String userReal;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 盐
     */
    private String salt;

    /**
     * cookie使用
     */
    private String token;

    /**
     * 性别,1:男;2:女;0:保密
     */
    private Integer gender;

    /**
     * 出生年月,yyyy-MM-dd
     */
    @TableField(typeHandler = LocalDateTypeHandler.class)
    private LocalDate birth;

    /**
     * 邮箱
     */
    @TableField(value = "mail")
    private String mail;

    /**
     * 电话
     */
    @JsonFormat(pattern = "")
    private String phone;

    /**
     * 账户锁定,1:锁;0:不锁,默认
     */
    private Boolean locked;

    /**
     * 最后登录ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime loginDate;

    /**
     * 创建时间
     */
    @TableField(fill = INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField(fill = UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime gmtModified;

    @TableField(exist = false)
    private String unnecessary;
    

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
