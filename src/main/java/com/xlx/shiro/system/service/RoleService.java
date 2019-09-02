package com.xlx.shiro.system.service;

import com.xlx.shiro.system.dao.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * 角色
 *
 * @author xielx on 2019/7/28
 */
@Service
public class RoleService {

	@Resource
	private RoleMapper roleMapper;


	/**
	 * 根据帐号获取该用户的角色集
	 * @param userName 用户名
	 * @return set
	 */
	public Set<String> findRoleKey(String userName) {

		return roleMapper.selectRoleKeyByUserName(userName);
	}

}
