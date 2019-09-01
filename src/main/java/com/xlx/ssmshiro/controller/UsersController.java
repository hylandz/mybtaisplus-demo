package com.xlx.ssmshiro.controller;


import com.xlx.ssmshiro.service.UsersService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
@Controller
@RequestMapping("/users")
public class UsersController {

	@Resource
	private UsersService usersService;




}

