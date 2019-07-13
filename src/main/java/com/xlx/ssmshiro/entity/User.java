package com.xlx.ssmshiro.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
public class User  implements Serializable {

    private Long userId; //主键

    private Long deptId; // 部门id

    private String avatarName; // 昵称

    private String avatarUrl; //头像url

    private String userAccount; //账号

    private String userName; //用户名

    private String userPassword; //密码

    private String salt; //盐值

    private String token; //

    private String mail;

    private String phone;

    private Boolean locked;

    private String loginIp;

    private Date loginDate;

    private Date gmtCreate;

    private Date gmtModified;

    public User(Long userId, Long deptId, String avatarName, String avatarUrl, String userAccount, String userName, String userPassword, String salt, String token, String mail, String phone, Boolean locked, String loginIp, Date loginDate, Date gmtCreate, Date gmtModified) {
        this.userId = userId;
        this.deptId = deptId;
        this.avatarName = avatarName;
        this.avatarUrl = avatarUrl;
        this.userAccount = userAccount;
        this.userName = userName;
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

    public User() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName == null ? null : avatarName.trim();
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl == null ? null : avatarUrl.trim();
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}