package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dto.ProfileDTO;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
	 *
	 * @param model user.html
	 * @return str
	 */
	@GetMapping("/system/user")
	public String userManage(Model model) {
		//????????
		final User currentUser = ShiroUtil.getCurrentUser();
		model.addAttribute(UserConstant.CURRENT_USER, currentUser);
		return "system/user/user";
	}
	
	
	/**
	 * 分页查询
	 *
	 * @param param 分页参数
	 * @param user  查询参数
	 * @return map
	 */
	@RequiresPermissions("system:user:view")
	@GetMapping("/user/list")
	@ResponseBody
	public Map<String, Object> userList(QueryParam param, User user) {
		logger.info("QueryParam={}", param);
		logger.info("userName={},gender={},locked={}", user.getUserName(), user.getGender(), user.getLocked());
		return super.selectByPageNumSize(param, () -> this.userService.listUserByPage(user));
	}
	
	/**
	 * 修改个人信息
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
		final User currentUser = ShiroUtil.getCurrentUser();
		if (currentUser == null) {
			return ResultDTO.failed("session已过期,请重新登录");
		}
		
		if (this.userService.modifyPassword(currentUser, newPassword)) {
			ShiroUtil.getSubject().logout();
			return ResultDTO.success("修改成功");
		}
		
		return ResultDTO.failed("修改失败,请联系网站管理员");
	}
	
	/**
	 * 验证原始密码
	 *
	 * @return dto
	 */
	@PostMapping("/user/verify")
	@ResponseBody
	public Boolean verifyOriginPassword(String originPwd) {
		if (this.userService.verifyPassword(originPwd)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 个人信息页面显示
	 *
	 * @param model model
	 * @return str
	 */
	@GetMapping("/user/profile")
	public String userProfile(Model model) {
		final User currentUser = ShiroUtil.getCurrentUser();
		if (currentUser == null) {
			return "redirect:/login";
		}
		final ProfileDTO profile = this.userService.getProfile(currentUser.getUserId());
		model.addAttribute(UserConstant.CURRENT_USER, profile);
		return "system/user/profile";
	}
	
	/**
	 * 点击编辑资料时数据显示
	 *
	 * @return
	 */
	@GetMapping("/user/getUserProfile")
	@ResponseBody
	public ResultDTO getUserProfile() {
		final User currentUser = ShiroUtil.getCurrentUser();
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
}
