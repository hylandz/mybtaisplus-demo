package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.util.ShiroUtils;
import com.xlx.shiro.system.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * index
 *
 * @author xielx on 2019/7/22
 */
@Controller
public class IndexController {

	@GetMapping("/")
	public String redirectIndex(){
		return "redirect:/index";
	}

	/**
	 * 登录成功后跳转这里
	 * @param model view
	 * @return index
	 */
	@GetMapping("/index")
	public String index(Model model){
		User user = ShiroUtils.getCurrentUser();
		model.addAttribute(UserConstant.CURRENT_USER,user);
		
		return "index";
	}

	@GetMapping("/login")
	public String login(){
		return "login";
	}
	
	
	/**
	 * 未授权
	 */
	@GetMapping("/unAuth")
	public String unauthorized() {
		return "error/403";
	}
	
	@GetMapping("/403")
	public String forbid() {
		return "403";
	}
}
