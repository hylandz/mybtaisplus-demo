package com.xlx.shiro.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * sys_user
 *
 * @author xielx on 2019年9月7日
 */
public class User implements Serializable {
	
	public static final String DEFAULT_AVATAR_URL = "default.jpg";
	
	public static final String DEFAULT_AVATAR_NAME = "优雅的呼啦";
	private Long userId; //主键
	
	private Long deptId; // 部门id
	
	private String avatarName; // 昵称
	
	private String avatarUrl; //头像url
	
	private String userName; //用户名
	
	private String userReal; //真实姓名
	
	private String userPassword; //密码
	
	private String salt; //盐值
	
	private String token; //cookie使用
	
	private Integer gender; // 性别 1:男 2:女
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date birth; // 出生年月
	
	private String mail; //邮箱
	
	private String phone; //电话
	
	private Boolean locked; //不锁定
	
	private String loginIp; // 登录ip
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date loginDate; //最后登录时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtCreate; //创建时间
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date gmtModified; //修改时间
	
	private String deptName;//部门名称
	
	private String roleName;//角色名称
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
	
	
	/**
	 * 盐 = 用户名 + salt
	 *
	 * @return str
	 */
	public String getCredentialsSalt() {
		return userName + salt;
	}
	
	/**
	 * @return str
	 */
	public Long getAuthCacheKey() {
		return userId;
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
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}
	
	public String getUserReal() {
		return userReal;
	}
	
	public void setUserReal(String userReal) {
		this.userReal = userReal == null ? null : userReal.trim();
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
	
	public Integer getGender() {
		return gender;
	}
	
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public Date getBirth() {
		return birth;
	}
	
	public void setBirth(Date birth) {
		this.birth = birth;
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
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
	
}