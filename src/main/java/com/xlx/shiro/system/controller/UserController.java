package com.xlx.shiro.system.controller;

import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * controller:user
 *
 * @author xielx on 2019/7/24
 */
@Controller
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;





	@GetMapping("/user/list")
	public List<User> listUserByPage(){
		List<User> userList = userService.listUserPage(0,5);
		return userList;
	}




}
