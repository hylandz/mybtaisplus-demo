package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dto.LoginDTO;
import com.xlx.shiro.system.dto.ResultDTO;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 登录
 *
 * @author xielx on 2019/7/22
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);


	/**
	 * 登录
	 * @param loginDTO 登录dto
	 */
	@ResponseBody
	@RequestMapping(name = "/login",method = RequestMethod.POST)
	public ResultDTO login(LoginDTO loginDTO){

		logger.info("登录:[{}]",loginDTO);
		if (loginDTO == null){
			return ResultDTO.failed("用户名或密码不能为空");
		}
		UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUsername(),loginDTO.getPassword(),loginDTO.getRememberMe());

		Subject subject = ShiroUtil.getSubject();
		try{
			subject.login(token);
			return ResultDTO.success("登录成功");
		}catch (LockedAccountException | ExcessiveAttemptsException | UnauthenticatedException e){
			//帐号锁定/连续试错5次/用户名或密码错误
			return ResultDTO.failed(e.getMessage());
		}

	}


	/**
	 * 未授权
	 */
	@GetMapping("/unAuth")
	public String unauthorized() {
		return "unauthorized";
	}
}
