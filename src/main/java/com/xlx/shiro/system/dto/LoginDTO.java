package com.xlx.shiro.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录dto
 *
 * @author xielx on 2019/7/24
 */
@Data
public class LoginDTO implements Serializable {

  // 登录账户
	private String username;

	// 密码
	private String password;

	// 记住我
	private Boolean rememberMe = Boolean.FALSE;

	// 验证码
	private String code;

	@Override
	public String toString() {
		return "LoginDTO{" +
									 "userName='" + username + '\'' +
									 ", password='" + password + '\'' +
									 ", rememberMe=" + rememberMe +
									 ", code='" + code + '\'' +
									 '}';
	}
}
