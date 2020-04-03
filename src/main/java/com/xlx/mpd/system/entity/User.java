package com.xlx.mpd.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("sys_user")
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
     * 性别,1:男;0:女
     */
    private Integer gender;

    /**
     * 出生年月,yyyy-MM-dd
     */
    private LocalDate birth;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 电话
     */
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
    private LocalDateTime loginDate;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
