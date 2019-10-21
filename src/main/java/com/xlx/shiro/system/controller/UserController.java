package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.common.exception.CustomizeExceptionEnum;
import com.xlx.shiro.common.util.ShiroUtils;
import com.xlx.shiro.common.util.poi.FileUtils;
import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * controller:user
 *
 * @author xielx on 2019/7/24
 */
@Controller
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	private UserService userService;
	
	
	/**
	 * 点击用户管理跳转
	 * 设置访问权限
	 *
	 * @param model user.html
	 * @return str
	 */
	@GetMapping("/system/user")
	@RequiresPermissions("system:user:view")
	public String userManage(Model model) {
		User currentUser = ShiroUtils.getCurrentUser();
		model.addAttribute(UserConstant.CURRENT_USER, currentUser);
		return "system/user/user";
	}
	
	
	/**
	 * 分页查询
	 * 设置访问权限
	 *
	 * @param param 分页参数
	 * @param user  查询参数
	 * @return map
	 */
	@GetMapping("/user/list")
	@RequiresPermissions("system:user:list")
	@ResponseBody
	public Map<String, Object> userList(QueryParam param, User user) {
		logger.info("QueryParam={}", param);
		logger.info("userName={},gender={},locked={}", user.getUserName(), user.getGender(), user.getLocked());
		return super.selectByPageNumSize(param, () -> this.userService.listUserByPage(user));
	}
	
	/**
	 * 修改个人信息
	 *
	 * @param profileDTO profile
	 * @return dto
	 */
	@RequestMapping(value = "/user/updateUserProfile", method = RequestMethod.POST)
	@ResponseBody
	public ResultDTO editProfile(ProfileDTO profileDTO) {
		logger.info("profile={}", profileDTO);
		try {
			if (userService.editProfile(profileDTO)) {
				return ResultDTO.success("更新个人信息成功");
			}
		} catch (Exception e) {
			return ResultDTO.failed("更新个人信息失败,请联系网站管理员!");
		}
		
		return ResultDTO.success("更新个人信息失败,请联系网站管理员!");
	}
	
	/**
	 * 修改密码吗
	 *
	 * @param newPassword 新密码
	 * @return dto
	 */
	
	@PostMapping("/user/updatePassword")
	@ResponseBody
	public ResultDTO modifyPassword(String newPassword) {
		final User currentUser = ShiroUtils.getCurrentUser();
		if (currentUser == null) {
			return ResultDTO.failed("session已过期,请重新登录");
		}
		
		if (this.userService.modifyPassword(currentUser, newPassword)) {
			ShiroUtils.getSubject().logout();
			return ResultDTO.success("修改成功");
		}
		
		return ResultDTO.failed("修改失败,请联系网站管理员");
	}
	
	/**
	 * 验证原始密码
	 * @param originPwd 原始密码
	 * @return boolean
	 */
	@PostMapping("/user/verify")
	@ResponseBody
	public Boolean verifyOriginPassword(String originPwd) {
		return this.userService.verifyPassword(originPwd);
	}
	
	/**
	 * 个人信息页面显示
	 *
	 * @param model model
	 * @return str
	 */
	@GetMapping("/user/profile")
	public String userProfile(Model model) {
		final User currentUser = ShiroUtils.getCurrentUser();
		if (currentUser == null) {
			return "redirect:/login";
		}
		ProfileDTO profile = this.userService.getProfile(currentUser.getUserId());
		model.addAttribute(UserConstant.CURRENT_USER, profile);
		return "system/user/profile";
	}
	
	/**
	 * 点击编辑资料时数据显示
	 */
	@GetMapping("/user/getUserProfile")
	@ResponseBody
	public ResultDTO getUserProfile() {
		final User currentUser = ShiroUtils.getCurrentUser();
		if (currentUser == null) {
			return ResultDTO.failed("session已过期,请重新登录");
		}
		try {
			final ProfileDTO profile = this.userService.getProfile(currentUser.getUserId());
			return ResultDTO.success(profile);
		} catch (Exception e) {
			logger.error("error:get userInfo from database failed");
			return ResultDTO.failed("获取用户信息失败，请联系网站管理员");
		}
	}
	
	/**
	 * 修改头像
	 *
	 * @param avatarUrl 头像url
	 * @return dto
	 */
	@PostMapping("/user/changeAvatar")
	@ResponseBody
	public ResultDTO editAvatar(@RequestParam(name = "avatarUrl") String avatarUrl) {
		logger.info("avatarUrl=[{}]", avatarUrl);//img/avatar/20180414165936.jpg
		
		//Optional<String> optional = Optional.ofNullable(avatarUrl);
		//final String orElse = optional.orElse("default.jpg");
		
		if (StringUtils.isEmpty(avatarUrl)) {
			avatarUrl = "default.jpg";
		} else {
			int i = avatarUrl.lastIndexOf("/");
			avatarUrl = avatarUrl.substring(++i);
			//final String[] split = avatarUrl.split("\\/");
		}
		
		
		final User currentUser = ShiroUtils.getCurrentUser();
		if (currentUser == null) {
			return ResultDTO.failed("session已过期,请重新登录");
		}
		
		if (userService.changAvatar(currentUser.getUserId(), avatarUrl)) {
			return ResultDTO.success("更新头像成功!");
		}
		return ResultDTO.failed("更新头像失败，请联系网站管理员!");
	}
	
	/**
	 * 验证新增用户的用户名
	 * @param userName 用户名
	 * @return true:可以
	 */
	@PostMapping("/user/verifyUserName")
	@ResponseBody
	public Boolean verifyUserName(String userName) {
		final User user = userService.findUserByUserName(userName);
		return user == null;
	}
	
	/**
	 * 用户新增
	 * @param user User
	 * @param roles roleName
	 * @return dto
	 */
	@PostMapping("/user/create")
	@ResponseBody
	public ResultDTO createUser(User user,Long[] roles){
	  logger.info("用户新增={}",user);
		logger.info("rolesIds={}",roles.length);
		try{
			if (userService.saveUser(user,roles)){
				return ResultDTO.success("用户新增成功!");
			}
		}catch (Exception e){
			logger.error("用户新增失败: {}",e.getMessage());
			return ResultDTO.failed("用户新增失败,请联系网站管理员!");
		}
	  return ResultDTO.failed("用户新增失败,请联系网站管理员!");
	}
	
	/**
	 * 获取用户
	 * @param userId 用户id
	 * @return dto
	 */
	@GetMapping("/user/getUser")
	@ResponseBody
	public ResultDTO getUser(@RequestParam(name = "userId") Long userId){
		try{
			final User user = userService.findWithUserRole(userId);
			return ResultDTO.success(user);
		}catch (Exception e){
			return ResultDTO.failed("查询用户信息失败!");
		}
	}
	
	/**
	 * 用户修改
	 * @param user User
	 * @param rolesSelect 角色
	 * @param locked 锁定
	 * @return dto
	 */
	@PostMapping("/user/edit")
	@ResponseBody
	public ResultDTO editUser(User user,Long[] rolesSelect,Boolean locked){
		logger.info("user={}",user);
		logger.info("locked={}",locked);
		logger.info("重新选择过的角色:{}",rolesSelect.length);
		
		try{
			userService.editUser(user,rolesSelect);
			return ResultDTO.success("修改用户信息成功!",user);
		}catch (Exception e){
			logger.error("修改用户信息失败:{}",e.getMessage());
			return ResultDTO.failed("修改用户信息失败,请联系网站管理员!");
		}
	}
	
	/**
	 * 删除用户+用户角色
	 * @param ids 用户id
	 * @return dto
	 */
	@PostMapping("/user/remove")
	@ResponseBody
	public ResultDTO removeUser(@RequestParam(name = "ids") Long[] ids){
		logger.info("ids={}",ids.length);
		try{
			userService.deleteUserByBatch(ids);
			return ResultDTO.success("删除用户成功!");
		}catch (Exception e){
			logger.error("删除用户失败:{}",e.getMessage());
			return ResultDTO.failed("删除用户失败,请联系网站管理员!");
		}
	}
	
	
	/**
	 * 注册
	 * @param user 新账户
	 * @return result
	 */
	@PostMapping("/register")
	@ResponseBody
	public ResultDTO registerAccount(User user){
		logger.info("注册用户:{}",user);
		if(userService.registerUser(user)){
			return ResultDTO.success("注册成功");
		}else {
			return ResultDTO.failed(CustomizeExceptionEnum.REGISTER_ERROR);
		}
	}
	
	
	/**
	 * 本地导出Excel
	 * @param user 导出数据
	 * @return result
	 */
	@PostMapping("/user/excel")
	@ResponseBody
	public ResultDTO downLoadExcel(User user, HttpServletResponse response){
		List<User> userList = userService.listUserByPage(user);
		try{
			return FileUtils.createExcel("用户数据",userList,User.class,response);
		}catch (Exception e){
			logger.error("导出用户excel失败:{}",e.getMessage());
			return ResultDTO.failed("导出用户excel失败,请联系网站管理员!");
		}
		
	}
}