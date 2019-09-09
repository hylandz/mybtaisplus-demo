package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.exception.CustomizeExceptionEnum;
import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dto.LoginDTO;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * 登录
 *
 * @author xielx on 2019/7/22
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private UserService userService;
	/**
	 * 登录
	 * @param loginDTO 登录dto
	 */
	@ResponseBody
	@PostMapping("/login")
	public ResultDTO login(LoginDTO loginDTO){
		logger.info("登录:[{}]",loginDTO);
		if (loginDTO == null){
			return ResultDTO.failed("用户名或密码不能为空");
		}else if (StringUtils.isBlank(loginDTO.getCode())){
			//验证码可以使用shiro过滤器拦截
			
			return ResultDTO.failed(CustomizeExceptionEnum.CAPTCHA_CODE_NOT_NULL);
		}
		UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUsername(),loginDTO.getPassword(),loginDTO.getRememberMe());

		Subject subject = ShiroUtil.getSubject();
		try{
			subject.login(token);
			this.userService.recordLoginTime(loginDTO.getUsername());
			logger.info( "User [" + loginDTO.getUsername() + "] logged in successfully." );
			return ResultDTO.success("登录成功");
		}catch (UnknownAccountException | ExcessiveAttemptsException | LockedAccountException e){
			//帐号锁定/连续试错5次/用户名或密码错误
			logger.error("there is a mistake happened:[{}]",e.getMessage());
			return ResultDTO.failed(e.getMessage());
		}catch (AuthenticationException e){
			logger.error("occurred an AuthenticationException:[{}]",e.getMessage());
			return ResultDTO.failed("用户名或密码错误");
		}

	}

	
}
