package com.xlx.ssmpshiro.system.entity;

import com.baomidou.mybatisplus.annotations.*;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

import com.xlx.ssmpshiro.common.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
@TableName(value = "sys_users")
public class User extends Model<User> {

	private static final long serialVersionUID = 1L;

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
	private Date birth;

	/**
	 * 年龄
	 */
	@TableField(exist = false)
	private Integer age;
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
	private boolean locked;
	/**
	 * 最后登录ip
	 */
	private String loginIp;
	/**
	 * 最后登录时间
	 */
	private Date loginDate;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.UPDATE)
	private Date gmtModified;

	/**
	 * 盐 = userReal + salt
	 */
	public String getCredentialsSalt(){
		return userReal + salt;
	}

	/**
	 * 计算年龄
	 */
	public Integer getAge(){
		String birthYear = DateUtil.formatStringOfYear(birth);
		String currYear = DateUtil.formatStringOfYear(new Date());
		Integer age = Integer.parseInt(birthYear)-Integer.parseInt(currYear);
		return this.age = age >= 0? age : 0;
	}







	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

	@Override
	protected Serializable pkVal() {
		return this.userId;
	}

}
