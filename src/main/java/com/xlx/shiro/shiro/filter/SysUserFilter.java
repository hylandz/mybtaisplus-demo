package com.xlx.shiro.shiro.filter;

import com.xlx.shiro.common.constant.UserConstant;
import com.xlx.shiro.common.util.ShiroUtil;
import com.xlx.shiro.entity.User;
import com.xlx.shiro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义的过滤器
 *
 * @author xielx on 2019/7/23
 */
@Slf4j
public class SysUserFilter extends PathMatchingFilter {

	@Resource
  private UserService userService;
	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		log.info("========PathMatchingFilter/onPreHandle方法================");
		User user = (User) ShiroUtil.getSubject().getPrincipal();
		request.setAttribute(UserConstant.USER_SESSION,user);
		return true;
	}

	/**
	 * path与请求路径匹配
	 * @param path ?
	 * @param request 请求路径
	 * @return boolean
	 */
	@Override
	protected boolean pathsMatch(String path, ServletRequest request) {
		log.info("========PathMatchingFilter/pathsMatch方法================");
		log.info("请求路径:[{}]-------->[{}]",path,WebUtils.toHttp(request).getRequestURI());
		return super.pathsMatch(path,request);
	}
}
