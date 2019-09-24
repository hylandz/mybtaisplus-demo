package com.xlx.shiro.system.service;

import com.xlx.shiro.common.exception.CustomizeException;
import com.xlx.shiro.system.dao.RoleMapper;
import com.xlx.shiro.system.dao.RoleMenuMapper;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Role;
import com.xlx.shiro.system.entity.RoleWithMenu;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author xielx on 2019/7/28
 */
@Slf4j
@Service
public class RoleService {

	
	@Resource
	private RoleMapper roleMapper;


	@Resource
	private RoleMenuMapper roleMenuMapper;
	/**
	 * 根据帐号获取该用户的角色集
	 * @param userName 用户名
	 * @return set
	 */
	public Set<String> findRoleKey(String userName) {

		return roleMapper.selectRoleKeyByUserName(userName);
	}
	
	/**
	 * 获取所有有效的角色
	 * @return list
	 */
	public List<Role> findAllRoles(String roleName){
		return roleMapper.selectAllRoles(roleName);
	}
	
	/**
	 * 新增角色时验证role_key是否重复
	 * @param roleKey 角色关键字
	 * @return null:ok
	 */
	public boolean verifyRoleKey(String roleKey){
		if (StringUtils.isEmpty(roleKey)){
			return false;
		}
		Role role = roleMapper.selectRoleKey(roleKey);
		return role == null;
	}
	
	
	/**
	 * 新增角色
	 * @param role 角色
	 * @param menuId 菜单id
	 * @return boolean
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveRole(Role role,Long[] menuId){
		if (role == null || menuId.length == 0){
			return false;
		}
		role.setAvailable(true);
		role.setGmtCreate(new Date());
		//role新增
		int i = roleMapper.insertSelective(role);
		log.info("新增角色 ---> effectRows: {}",i);
		Role newer = roleMapper.selectRoleKey(role.getRoleKey());
		int i1 = 0;
		//roleMenu批量插入
		if (newer != null){
			i1 = roleMenuMapper.insertByBatch(newer.getRoleId(), menuId);
			log.info("roleMenu批量插入 ---> effectRows: {}",i1);
		}
		return i* i1 != 0;
	}
	
	/**
	 * 获取角色
	 * @param roleId 角色id
	 * @return role
	 */
	@Transactional(readOnly = true)
	public RoleWithMenu getRoleWithMenu(Long roleId){
		
		List<RoleWithMenu> menuList = roleMapper.selectRole(roleId);
		List<Long> collect = menuList.stream().map(RoleWithMenu::getMenuId).collect(Collectors.toList());
		if (menuList.size() == 0){
			return new RoleWithMenu();
		}
		
		RoleWithMenu roleWithMenu = menuList.get(0);
		roleWithMenu.setMenuIds(collect);
		return roleWithMenu;
	}
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean editRole(Role role,Long[] menuId){
		if (role == null || menuId.length == 0){
			return false;
		}
		//修改角色
		int i = roleMapper.updateByPrimaryKeySelective(role);
		log.info("修改角色 effectRows --->{}",i);
		//删除原先的角色菜单
		int i1 = roleMenuMapper.deleteByRoleId(role.getRoleId());
		log.info("修改角色 effectRows --->{}",i1);
		//新增修改过的角色菜单
		int i2 = roleMenuMapper.insertByBatch(role.getRoleId(), menuId);
		log.info("修改角色 effectRows --->{}",i2);
		
		return i*i1*i2 != 0;
	}
	
	/**
	 * 批量删除
	 * 角色id + 角色菜单id
	 * @param roleId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteBatch(Long[] roleId) {
		if (roleId.length == 0){
			return false;
		}
		try {
			//删除role
			int i = roleMapper.deleteByBatch(roleId);
			//删除roleMenu
			int i1 = roleMenuMapper.deleteByBatch(roleId);
			return i * i1 != 0;
		} catch (Exception e) {
			throw new CustomizeException(e.getMessage());
		}
		
	}
}
