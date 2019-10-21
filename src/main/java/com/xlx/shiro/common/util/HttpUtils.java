package com.xlx.shiro.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * http工具类
 *
 * @author xielx on 2019/9/2
 */
public class HttpUtils {


	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);


	/**
	 * 检查请求是否为ajax请求
	 * 解释:
	 * ajax(异步)请求在它的请求头里会比传统请求(同步)多一个头参数,x-requested-with
	 * accept
	 * 			x-requested-with  XMLHttpRequest //ajax请求
	 * 所以可以此判断
	 * @param request req
	 * @return boolean
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {

		String header = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(header)) {
			return true;
		}
		return false;
	}
}
