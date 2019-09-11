package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.TreeUtil;
import com.xlx.shiro.system.dao.MenuMapper;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	public TreeDTO<Menu> listMenusOfLoginer(String userName){
			List<Menu> menuList = this.menuMapper.selectMenuByUserName(userName);
		
		List<TreeDTO<Menu>> treeDTOList =	menuList.stream().map(menu -> {
				TreeDTO<Menu> treeDTO = new TreeDTO<>();
				treeDTO.setId(menu.getMenuId().toString());
				treeDTO.setParentId(menu.getParentId().toString());
				treeDTO.setText(menu.getMenuName());
				treeDTO.setIcon(menu.getIcon());
				treeDTO.setUrl(menu.getUrl());
				return treeDTO;
			}).collect(Collectors.toList());
		
		return TreeUtil.build(treeDTOList);
	}
	
	/**
	 * 获取所有菜单排序
	 * @return tree
	 */
	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public TreeDTO<Menu> findMenus(){
		//菜单数据
		List<Menu> menuList = menuMapper.selectAllMenus();
		
		//排序菜单
		List<TreeDTO<Menu>> treeDTOList = new ArrayList<>();
		menuList.stream().forEach(menu -> {
			TreeDTO<Menu> tree = new TreeDTO<>();
			tree.setId(menu.getMenuId().toString());
			tree.setParentId(menu.getParentId().toString());
			tree.setText(menu.getMenuName());
			treeDTOList.add(tree);
		});
		
		return TreeUtil.build(treeDTOList);
	}
}
