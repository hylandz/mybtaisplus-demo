package com.xlx.shiro.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlx.shiro.common.entity.QueryParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 公共
 *
 * @author xielx on 2019/9/6
 */
public class BaseController {
	
	
	/**
	 * 获取分页的结果集与总记录数
	 * @param pageInfo mybatis分页对象
	 * @return map
	 */
	private Map<String,Object> getDataTable(PageInfo<?> pageInfo){
		Map<String,Object> dataMap = new HashMap<>();
		
		// 分页的结果集
		dataMap.put("rows",pageInfo.getList());
		//数据总记录
		dataMap.put("total",pageInfo.getTotal());
		return dataMap;
	}
	
	/**
	 * 分页查询
	 * @param param 查询参数
	 * @param supplier 函数式接口
	 * @return map
	 */
	protected Map<String,Object> selectByPage(QueryParam param,Supplier<?> supplier){
		//设置查询条件
		PageHelper.startPage(param.getPageNum(),param.getPageSize());
		//查询
		final PageInfo<?> pageInfo = new PageInfo<>((List<?>) supplier.get());
		//?
		PageHelper.clearPage();
		return getDataTable(pageInfo);
	}
}
