package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.entity.Role;
import com.xlx.shiro.system.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public Map<String,Object> listAllRoles(QueryParam param,
	                                       @RequestParam(name = "roleName",required = false) String roleName){
		log.info("roleParam={}",param);
		return super.selectByPageNumSize(param,() -> roleService.findAllRoles(roleName));
	}
	
	/**
	 * 验证roleKey是否重复
	 * @param roleKey 角色关键字
	 * @return boolean
	 */
	@GetMapping("/role/checkRoleKey")
	@ResponseBody
	public Boolean checkRoleKey(@RequestParam("roleKey") String roleKey){
		log.info("roleKey={}",roleKey);
		return roleService.verifyRoleKey(roleKey);
	}
	
	
	/**
	 * 角色新增
	 * @param role
	 * @param menuId
	 * @return
	 */
	@PostMapping("/role/create")
	@ResponseBody
	public ResultDTO createRole(Role role,Long[] menuId){
		log.info("create Role={}",role);
		log.info("menuId of role={}",menuId.length);
		if (roleService.saveRole(role,menuId)){
			return ResultDTO.success("角色新增成功");
		}
		return  ResultDTO.failed("角色新增失败,请联系管理员!");
		
	}
	
	
	/**
	 * 修改角色
	 * @param role Role
	 * @param menuId 菜单id
	 * @return dto
	 */
	@PostMapping("/role/edit")
	@ResponseBody
	public ResultDTO editUser(Role role,Long[] menuId){
		log.info("修改角色----->{}",role);
		log.info("修改角色菜单---->{}",menuId.length);
		if (roleService.editRole(role,menuId)){
			return ResultDTO.success("角色更新成功!");
		}else {
			return ResultDTO.failed("角色跟新失败,请联系管理员!");
		}
	}
	
	/**
	 * 获取角色
	 * @param roleId 角色id
	 * @return dto
	 */
	@GetMapping("/role/getRole")
	@ResponseBody
	public ResultDTO getRoleWithMenu(Long roleId){
		log.info("获取角色 --->{}",roleId);
		try{
			Role roleWithMenu = roleService.getRoleWithMenu(roleId);
			return ResultDTO.success(roleWithMenu);
		}catch (Exception e){
			log.error("获取角色信息失败,{}",e.getMessage());
			return ResultDTO.failed("获取角色信息失败，请联系网站管理员");
		}
	}
	
	
	/**
	 * 角色删除
	 * @param ids 角色id
	 * @return dto
	 */
	@PostMapping("/role/remove")
	@ResponseBody
	public ResultDTO deleteBatch(Long[] ids){
		if (roleService.deleteBatch(ids)){
			return ResultDTO.success("删除成功!");
		}else {
			return ResultDTO.failed("删除失败!");
		}
	}
}
