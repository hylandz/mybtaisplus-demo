package com.xlx.ssmshiro.service;

import com.xlx.ssmshiro.entity.Users;
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
public interface UsersService extends IService<Users> {


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
