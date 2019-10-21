package com.xlx.shiro.system.service;

import com.xlx.shiro.common.util.TreeUtils;
import com.xlx.shiro.system.dao.MenuMapper;
import com.xlx.shiro.system.dao.RoleMenuMapper;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

	@Resource
	private RoleMenuMapper roleMenuMapper;

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
		
		return TreeUtils.build(treeDTOList);
	}
	
	/**
	 * 获取所有菜单排序
	 * @return tree
	 */
	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public TreeDTO<Menu> findMenus(){
		//菜单数据
		List<Menu> menuList = menuMapper.selectAllMenus(null,null);
		
		//排序菜单
		List<TreeDTO<Menu>> treeDTOList = new ArrayList<>();
		buildTrees(treeDTOList,menuList);
		return TreeUtils.build(treeDTOList);
	}
	
	/**
	 * 获取菜单树
	 * @return Tree 菜单类型:type='menu'
	 */
	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public TreeDTO<Menu> getMenuTrees(){
		//菜单数据
		List<Menu> menuList = menuMapper.selectMenuTrees();
		
		//排序菜单
		List<TreeDTO<Menu>> treeDTOList = new ArrayList<>();
		buildTrees(treeDTOList,menuList);
		return TreeUtils.build(treeDTOList);
	}
	
	
	private void buildTrees(List<TreeDTO<Menu>> treeDTOList,List<Menu> menuList){
		menuList.stream().forEach(menu -> {
			TreeDTO<Menu> tree = new TreeDTO<>();
			tree.setId(menu.getMenuId().toString());
			tree.setParentId(menu.getParentId().toString());
			tree.setText(menu.getMenuName());
			treeDTOList.add(tree);
		});
	}
	/**
	 * 获取所有菜单
	 * @param menuName 菜单名
	 * @param type 菜单类型
	 * @return list
	 */
	@Transactional(rollbackFor = Exception.class,readOnly = true)
	public List<Menu> getAllMenus(String menuName,String type){
		
		return menuMapper.selectAllMenus(menuName, type);
	}
	
	/**
	 * 新增菜单
	 * @param menu 菜单
	 * @return true:成功
	 */
	public Boolean insertMenu(Menu menu){
		menu.setGmtCreate(new Date());
		menu.setAvailable(Boolean.TRUE);
		int i = menuMapper.insertSelective(menu);
		return i != 0;
	}
	
	/**
	 * 修改菜单
	 * @param menu 菜单
	 * @return true:修改成功
	 */
	public Boolean updateMenu(Menu menu){
		int i = menuMapper.updateByPrimaryKeySelective(menu);
		return i != 0;
	}
	
	/**
	 * 删除菜单
	 * @return true:删除成功
	 */
	public Boolean deleteMenu(){
	
		return false;
	}
	
	/**
	 * 获取menu
	 * @param menuId id
	 * @return Menu
	 */
	public Menu findMenu(Long menuId){
		return menuMapper.selectByPrimaryKey(menuId);
	}
	
	
	/**
	 * 新增菜单
	 * @param menu 菜单
	 * @return true:成功
	 */
	public Boolean createMenu(Menu menu){
		return menuMapper.insertSelective(menu) != 0;
	}
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean modifyMenu(Menu menu){
		menu.setAvailable(Boolean.TRUE);
		menu.setGmtModified(new Date());
		return menuMapper.updateByPrimaryKeySelective(menu) != 0;
	}
	
	
	/**
	 * 删除菜单
	 * 删除角色菜单
	 * @param menuIds 菜单id
	 * @return true:成功
	 */
	public Boolean removeMenu(Long[] menuIds){
		if (menuIds.length == 0){
			return false;
		}
		
		int i = menuMapper.deleteBatch(menuIds);
		int i1 = roleMenuMapper.deleteBatchByMenuId(menuIds);
		
		return true;
	}
}
