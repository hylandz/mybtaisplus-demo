package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * controller:user
 *
 * @author xielx on 2019/7/24
 */
@Controller
public class UserController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;
	
	
	/**
	 * 跳转user.html
	 * @return str
	 */
	@GetMapping("/system/user")
	public String userManage(){
		return "system/user/user";
	}
	
	
	/**
	 * 分页查询
	 * @param param 分页参数
	 * @param user 查询参数
	 * @return map
	 */
	@RequiresPermissions("system:user:view")
	@ResponseBody
	@GetMapping("/user/list")
	public Map<String,Object> userList(QueryParam param,User user){
		return super.selectByPage(param,() -> this.userService.listUserByPage(user));
	}




}
