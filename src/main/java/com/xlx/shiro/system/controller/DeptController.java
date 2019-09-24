package com.xlx.shiro.system.controller;

import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.system.dto.ResultDTO;
import com.xlx.shiro.system.dto.TreeDTO;
import com.xlx.shiro.system.entity.Dept;
import com.xlx.shiro.system.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * dept
 *
 * @author xielx on 2019/9/8
 */
@Controller
public class DeptController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(DeptController.class);
	
	@Resource
	private DeptService deptService;
	
	
	
	/**
	 * 部门管理页面
	 * 设置访问权限
	 * @return role.html
	 */
	@GetMapping("/system/dept")
	public String deptManageIndex() {
		return "system/dept/dept";
	}
	
	
	/**
	 * 获取所有部门信息
	 * @return 部门数
	 */
	@GetMapping("/dept/tree")
	@ResponseBody
	public ResultDTO getDeptTree(){
		try{
			final TreeDTO<Dept> treeDTO = deptService.listAllDepts();
			return ResultDTO.success(treeDTO);
		}catch (Exception e){
			log.error("error:get data of dept from database failed:{}",e.getMessage());
			return ResultDTO.failed("获取部门信息失败");
		}
	}
	
	/**
	 * 部门首页
	 * @param deptName 部门名称
	 * @return list
	 */
	@GetMapping("/dept/list")
	@ResponseBody
	public List<Dept> listDept(@RequestParam("deptName") String deptName){
		return deptService.findAllDepts(deptName);
	}
}
