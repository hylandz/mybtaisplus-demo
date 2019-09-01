package com.xlx.shiro.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index
 *
 * @author xielx on 2019/7/22
 */
@Controller
public class IndexController {

	@GetMapping("/index")
	public String index(){
		return "index";
	}

	@GetMapping("/")
	public String home(){
		return "index";
	}

	@GetMapping("/login")
	public String login(){
		return "login";
	}
}
