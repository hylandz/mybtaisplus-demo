package com.xlx.shiro.system.entity;

import java.util.List;

/**
 * user-role
 *
 * @author xielx on 2019/9/10
 */
public class UserWithRole extends User {
	
	private Long roleId;
	
	private List<Long> roleIdList;
	
	public Long getRoleId() {
		return roleId;
	}
	
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	public List<Long> getRoleIdList() {
		return roleIdList;
	}
	
	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
}
