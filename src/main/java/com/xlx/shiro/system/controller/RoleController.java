package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * role
 *
 * @author xielx on 2019/9/9
 */
@Controller
public class RoleController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Resource
	private RoleService roleService;
	
	
	
	
	
	
	/**
	 * 角色管理页面
	 * 设置访问权限
	 * @return role.html
	 */
	@GetMapping("/system/role")
	public String roleManageIndex() {
		return "system/role/role";
	}
	/**
	 * 获取所有有效角色
	 * @param param page
	 * @return map
	 */
	@GetMapping("/role/list")
	@ResponseBody
	public Map<String,Object> listAllRoles(QueryParam param){
		log.info("roleParam={}",param);
		return super.selectByPageNumSize(param,() -> roleService.findAllRoles());
	}
	
	
	
}
