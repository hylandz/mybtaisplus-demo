package com.xlx.ssmpshiro.system.service;

import com.xlx.ssmpshiro.system.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xlx
 * @since 2019-07-16
 */
public interface UserService extends IService<User> {


	/**
	 * 获取帐号对应的所有角色
	 * @param userAccount account
	 */
	Set<String> listRoles(String userAccount);

	/**
	 * 获取帐号对应的权限
	 * @param userAccount account
	 */
	Set<String> listPermissions(String userAccount);
}
