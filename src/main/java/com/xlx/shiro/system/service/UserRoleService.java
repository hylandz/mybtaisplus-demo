package com.xlx.shiro.system.service;

import com.xlx.shiro.system.dao.UserRoleMapper;
import com.xlx.shiro.system.entity.User;
import com.xlx.shiro.system.entity.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户-角色
 *
 * @author xielx on 2019/9/9
 */
@Service
public class UserRoleService {
	
	@Resource
	private UserRoleMapper userRoleMapper;
	
	/**
	 * 批量新增用户角色
	 * @param userRoleList 用户角色集合
	 * @return boolean
	 */
	public Boolean addByBatch(List<UserRole> userRoleList){
		return userRoleMapper.insertBatch(userRoleList) != 0;
	}
}
