package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.exception.CustomizeExceptionEnum;
import com.xlx.shiro.common.util.ShiroUtils;
import com.xlx.shiro.common.util.vcode.Captcha;
import com.xlx.shiro.common.util.vcode.GifCaptcha;
import com.xlx.shiro.system.dto.LoginDTO;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * 登录
 *
 * @author xielx on 2019/7/22
 */
@Controller
public class LoginController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private static final String CODE_KEY = "_code";
	/**
	 * 宽度
	 */
	@Value("${xlx.validateCode.width:156}")
	private Integer width;

	/**
	 * 高度
	 */
	@Value("${xlx.validateCode.height:33}")
	private Integer height;

	/**
	 * 长度
	 */
	@Value("${xlx.validateCode.length:4}")
	private Integer length;
	
	
	
	@Resource
	private UserService userService;
	
	/**
	 * 登录
	 *
	 * @param loginDTO 登录dto
	 */
	@ResponseBody
	@PostMapping("/login")
	public ResultDTO login(LoginDTO loginDTO) {
		logger.info("登录:[{}]", loginDTO);
		if (loginDTO == null) {
			return ResultDTO.failed("用户名或密码不能为空");
		}
		if (StringUtils.isBlank(loginDTO.getCode())) {
			//验证码可以使用shiro过滤器拦截
			return ResultDTO.failed(CustomizeExceptionEnum.CAPTCHA_CODE_NOT_NULL);
		}
		
		//校验验证码
		Session session = super.getSession();
		String captcha = (String) session.getAttribute(CODE_KEY);
		if (!loginDTO.getCode().equalsIgnoreCase(captcha)){
			return ResultDTO.failed(CustomizeExceptionEnum.CAPTCHA_CODE_ERROR);
		}
		
		
		UsernamePasswordToken token = new UsernamePasswordToken(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getRememberMe());
		
		Subject subject = ShiroUtils.getSubject();
		try {
			subject.login(token);
			this.userService.recordLoginTime(loginDTO.getUsername());
			logger.info("User [" + loginDTO.getUsername() + "] logged in successfully.");
			return ResultDTO.success("登录成功");
		} catch (UnknownAccountException | ExcessiveAttemptsException | LockedAccountException e) {
			//帐号锁定/连续试错5次/用户名或密码错误
			logger.error("there is a mistake happened:[{}]", e.getMessage());
			return ResultDTO.failed(e.getMessage());
		} catch (AuthenticationException e) {
			logger.error("occurred an AuthenticationException:[{}]", e.getMessage());
			return ResultDTO.failed("用户名或密码错误");
		}
		
	}
	
	
	
	/**
	 * 获取验证码
	 * @param request req
	 * @param response res
	 */
	
	@GetMapping("/gifCode")
	public void getGifCode(HttpServletRequest request, HttpServletResponse response){
	
		//设置响应头
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires",0);
		response.setContentType("image/gif");
		
		Captcha captcha = new GifCaptcha(width,height,length);
		
		//获取当前Session,不存在则创建
		HttpSession session = request.getSession(true);
		try{
			captcha.out(response.getOutputStream());
			session.removeAttribute(CODE_KEY);
			session.setAttribute(CODE_KEY,captcha.text().toLowerCase());
		}catch (Exception e){
			logger.error("图形验证码生成失败:{}",e.getMessage());
		}
	}
	
}
