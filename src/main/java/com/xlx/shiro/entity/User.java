package com.xlx.shiro.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
@Data
public class User  implements Serializable {

    private Long userId; //主键

    private Long deptId; // 部门id

    private String avatarName; // 昵称

    private String avatarUrl; //头像url

    private String userName; //用户名

    private String userReal; //真实姓名

    private String userPassword; //密码

    private String salt; //盐值

    private String token; //cookie使用

    private String mail; //邮箱

    private String phone; //电话

    private Boolean locked = Boolean.FALSE; //不锁定

    private String loginIp; // 登录ip

    private Date loginDate; //最后登录时间

    private Date gmtCreate; //创建时间

    private Date gmtModified; //修改时间

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }

    /**
     * 盐 = 用户名 + salt
     * @return
     */
    public String getCredentialsSalt(){
        return userName + salt;
    }

    public User(Long userId, Long deptId, String avatarName, String avatarUrl, String userName, String userReal, String userPassword, String salt, String token, String mail, String phone, Boolean locked, String loginIp, Date loginDate, Date gmtCreate, Date gmtModified) {
        this.userId = userId;
        this.deptId = deptId;
        this.avatarName = avatarName;
        this.avatarUrl = avatarUrl;
        this.userName = userName;
        this.userReal = userReal;
        this.userPassword = userPassword;
        this.salt = salt;
        this.token = token;
        this.mail = mail;
        this.phone = phone;
        this.locked = locked;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
    }
}