package com.xlx.shiro.system.controller;

import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Menu;
import com.xlx.shiro.system.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 *
 * @author xielx on 2019/9/5
 */
@Controller
public class MenuController {
	
	private static final Logger log = LoggerFactory.getLogger(MenuController.class);
	
	@Resource
	private MenuService menuService;
	
	
	/**
	 * 跳转菜单
	 * @return url
	 */
	@GetMapping("/system/menu")
	public String menuIndex(){
		return "system/menu/menu";
	}
	
	
	
	
	/**
	 * 获取用户菜单
	 * @param userName 用户名
	 * @return tree
	 */
	@ResponseBody
	@GetMapping("/menu/getUserMenu")
	public ResultDTO getUserMenu(@RequestParam("userName") String userName){
		try{
			final TreeDTO<Menu> treeDTO = menuService.listMenusOfLoginer(userName);
			return ResultDTO.success(treeDTO);
		}catch (Exception e){
			log.error("omg,something that seems like bad has been happened : {}",e.getMessage());
			return ResultDTO.failed("获取用户菜单失败");
		}
		
	}
	
	/**
	 * 获取菜单树
	 * @return tree
	 */
	@GetMapping("/menu/menuButtonTree")
	@ResponseBody
	public ResultDTO getMenuButtonTree(){
		try{
			TreeDTO<Menu> menus = menuService.findMenus();
			return ResultDTO.success(menus);
		}catch (Exception e){
			log.error("getMenuButtonTree()失败--->{}",e.getMessage());
			return ResultDTO.failed("获取菜单树失败,请联系管理员!");
		}
		
	}
	
	/**
	 * 获取菜单集合
	 * @param menuName 菜单名
	 * @param type 类型
	 * @return list
	 */
	@GetMapping("/menu/list")
	@ResponseBody
	public List<Menu> listMenus(String menuName,String type){
		try {
			return menuService.getAllMenus(menuName,type);
		}catch (Exception e){
			log.error("获取菜单集合失败--->{}",e.getMessage());
		  return new ArrayList<>();
		}
	}
	
	/**
	 * 创建菜单
	 * @param menu 菜单
	 * @return dto
	 */
	@PostMapping("/menu/create")
	@ResponseBody
	public ResultDTO createMenu(Menu menu){
		try{
			if (menuService.saveMenu(menu)){
				return ResultDTO.success("新增菜单成功!");
			}else {
				return ResultDTO.failed("新增菜单失败!");
			}
		}catch (Exception e){
			log.error("创建菜单失败,提示:{}",e.getMessage());
			return ResultDTO.failed("新增菜单失败!");
		}
	}
	
}
