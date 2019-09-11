package com.xlx.shiro.system.entity;

import java.util.List;

/**
 * @author xielx on 2019/9/12
 */
public class RoleWithMenu extends Role {
	
	private Long menuId;
	
	private List<Long> menuIds;
	
	public Long getMenuId() {
		return menuId;
	}
	
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public List<Long> getMenuIds() {
		return menuIds;
	}
	
	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}
}
