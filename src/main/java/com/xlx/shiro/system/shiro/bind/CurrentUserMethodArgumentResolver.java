package com.xlx.shiro.system.shiro.bind;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 用于绑定@FormModel的方法参数解析
 *
 * @author xielx on 2019/7/24
 */
@Slf4j
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

	public CurrentUserMethodArgumentResolver() {
		log.info("-------CurrentUserMethodArgumentResolver的无参构造--------");
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.hasParameterAnnotation(CurrentUser.class)){
			log.info("********supportsParameter true**********");
			return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		log.info("*********resolveArgument解析参数***************");
		CurrentUser currentUser = parameter.getParameterAnnotation(CurrentUser.class);
		log.info("getParameterAnnotation=[{}]",currentUser);
		return webRequest.getAttribute(currentUser.value(),NativeWebRequest.SCOPE_REQUEST);

	}
}
