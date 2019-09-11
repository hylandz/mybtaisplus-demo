package com.xlx.shiro.system.controller;

import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Menu;
import com.xlx.shiro.system.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
	
	
}
