package com.xlx.shiro.common.config;

import com.xlx.shiro.common.entity.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 验证码配置
 *
 * @author xielx at 2019/10/9 18:00
 */
//@Configuration
//@ConfigurationProperties(prefix = "xlx")
public class CaptchaProperty {
	
	private ValidateCodeProperties validateCode = new ValidateCodeProperties();
	
	public ValidateCodeProperties getValidateCode() {
		return validateCode;
	}
	
	public void setValidateCode(ValidateCodeProperties validateCode) {
		this.validateCode = validateCode;
	}
}
