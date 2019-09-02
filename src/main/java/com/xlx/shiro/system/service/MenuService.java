package com.xlx.shiro.system.service;

import com.xlx.shiro.system.dao.MenuMapper;
import com.xlx.shiro.system.entity.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 资源
 *
 * @author xielx on 2019/7/28
 */
@Service
public class MenuService {

	@Resource
	private MenuMapper menuMapper;


	/**
	 * 根据帐号获取该用户的权限集
	 * @param userName 登录帐号
	 */
	public Set<String> findPermissions(String userName){
		return menuMapper.selectPermissionsByUserName(userName);
	}


	/**
	 * 根据账户获取对应的资源管理菜单
	 * @param userName 登录账户
	 * @return list
	 */
	public List<Menu> listMenusOfLoginer(String userName){

		return  null;
	}
}
