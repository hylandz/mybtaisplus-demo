package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.util.DateUtils;
import com.xlx.shiro.common.util.JwtUtils;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户
 *
 * @author xielx on 2019/7/14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class JWTController {

	@Resource
	private UserService userService;

	/**
	 * 登录
	 * 思路:
	 * 登录成功,就创建一个token(有效时间:登录时间+10min)
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	@Transactional
	public ResultDTO login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
		log.info("username:[{}],password:[{}]", username, password);
		//TODO  检验参数的完整性

		User user = userService.findUserByUserName(username);
		//检验username是否存在
		if (user != null) {
			//检验密码是否正确
			if (!user.getUserPassword().equals(password)) {

				return ResultDTO.failed(1001, "用户名或密码错误");
			}
		}
		//登录成功,更新登录时间?数据库插入有延迟1ms
		Date loginDate = new Date();
		log.info("记录登录时间:[{}]", DateUtils.formatString(loginDate));
		this.userService.recordLoginTime(username);
		return ResultDTO.success("登录成功", JwtUtils.generateToken(username, loginDate));
	}

	/**
	 * 验证token是否过期
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/index")
	public ResultDTO login(HttpServletRequest request) {
		String token = request.getParameter("token");
		return JwtUtils.validateToken(token);
	}


	/**
	 * 前端请求刷新过期的token
	 *
	 * @return
	 */
	@GetMapping("/fresh")
	public ResultDTO freshToken() {

		return ResultDTO.success();
	}

}
