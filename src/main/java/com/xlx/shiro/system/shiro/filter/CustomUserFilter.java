package com.xlx.shiro.system.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.xlx.shiro.common.util.HttpUtils;
import com.xlx.shiro.system.dto.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 自定义UserFilter
 *
 * @author xielx on 2019/9/2
 */
@Slf4j
public class CustomUserFilter extends UserFilter {

	/**
	 * 判断是否是ajax请求
	 * 是,返回403状态码
	 * @param request  req
	 * @param response req
	 * @return boolean
	 * @throws Exception e
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//
		if (HttpUtils.isAjaxRequest((HttpServletRequest) request)) {
			log.info("onAccessDenied:拦截到AJAX请求~~");
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
			httpServletResponse.setContentType("application/json; charset=utf-8");
			PrintWriter writer = httpServletResponse.getWriter();
			writer.print(JSON.toJSONString(ResultDTO.failed()));
			return false;
		} else {
			saveRequestAndRedirectToLogin(request, response);
			return false;
		}
	}
}
