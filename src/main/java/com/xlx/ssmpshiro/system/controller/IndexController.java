package com.xlx.ssmpshiro.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index
 *
 * @author xielx on 2019/7/17
 */
@Controller
public class IndexController {

	@GetMapping("/index")
	public String index(){
		return "index";
	}
	
	
	@GetMapping("/")
	public String home(){
		return "redirect:/index";
	}
	
	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	
}
