package com.xlx.shiro.common.config;

import com.xlx.shiro.system.shiro.bind.CurrentUserMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * webMvc
 *
 * @author xielx on 2019/7/26
 */
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new CurrentUserMethodArgumentResolver());
	}
}
