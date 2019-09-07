package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
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
	 * 点击用户管理跳转
	 * @param model user.html
	 * @return str
	 */
	@GetMapping("/system/user")
	public String userManage(Model model){
		//????????
		final User currentUser = ShiroUtil.getCurrentUser();
		model.addAttribute(UserConstant.CURRENT_USER,currentUser);
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
		logger.info("QueryParam={}",param);
		logger.info("User={}",user);
		//final List<User> userList = this.userService.listUserByPage(user);
		//logger.info("userList={}",userList);
			return super.selectByPageNumSize(param,() -> this.userService.listUserByPage(user));
	}
	
	
	/**
	 * 验证原始密码
	 * @return dto
	 */
	@ResponseBody
	@GetMapping("/verify")
	public ResultDTO verifyOriginPassword(String originPwd){
	  if(this.userService.verifyPassword(originPwd)){
	  	return ResultDTO.success();
	  }
	  return ResultDTO.failed();
	}
	
	/**
	 * 修改密码吗
	 * @param newPwd 新密码
	 * @return dto
	 */
	@ResponseBody
	@PostMapping("/user/updatePassword")
	public ResultDTO modifyPassword(String newPwd){
		final User currentUser = ShiroUtil.getCurrentUser();
		if (currentUser == null){
			return ResultDTO.failed("session已过期,请重新登录");
		}
		
		final Long userId = currentUser.getUserId();
		if (this.userService.modifyPassword(userId,newPwd)){
			return ResultDTO.success("修改成功");
		}
		
		return ResultDTO.failed("修改失败");
	}
	
	

}
