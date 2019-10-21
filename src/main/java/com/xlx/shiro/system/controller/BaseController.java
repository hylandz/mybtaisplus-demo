package com.xlx.shiro.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xlx.shiro.common.entity.QueryParam;
import com.xlx.shiro.common.util.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 公共
 *
 * @author xielx on 2019/9/6
 */
@Slf4j
public class BaseController {
	
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder){
		
		//将String类型做trim,再绑定
		binder.registerCustomEditor(String.class,
						new StringTrimmerEditor(true));
		
		//将Date类型参数格式化,再绑定
		binder.registerCustomEditor(Date.class,
						new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), false));
		
	}
	
	/**
	 * 获取Session
	 * @return session
	 */
	protected Session getSession(){
		return ShiroUtils.getSubject().getSession();
	}
	/**
	 * 获取分页的结果集与总记录数
	 *
	 * @param pageInfo mybatis分页对象
	 * @return map
	 */
	private Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
		Map<String, Object> dataMap = new HashMap<>();
		
		// 分页的结果集
		dataMap.put("rows", pageInfo.getList());
		//数据总记录
		dataMap.put("total", pageInfo.getTotal());
		return dataMap;
	}
	
	/**
	 * 分页查询
	 *
	 * @param param    查询参数
	 * @param supplier 函数式接口
	 * @return map
	 */
	protected Map<String, Object> selectByPageNumSize(QueryParam param, Supplier<?> supplier) {
		//设置查询条件
		PageHelper.startPage(param.getPageNum(), param.getPageSize());
		//查询
		/*List<User> userList = this.userService.listUserByPage(user);
		PageInfo<User> pageInfo = new PageInfo<>(userList);*/
		
		//只有紧跟在PageHelper.startPage方法后的第一个Mybatis的查询（Select）方法会被分页
		final PageInfo<?> pageInfo = new PageInfo<>((List<?>) supplier.get());
		
		//?
		PageHelper.clearPage();
		return getDataTable(pageInfo);
	}
}
