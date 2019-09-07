package com.xlx.shiro.common.config;

import com.xlx.shiro.system.interceptor.SqlStatementInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 *
 * @author xielx on 2019/9/7
 */
@Configuration
public class MybatisConfig {
	
	/**
	 * 配置sql拦截器
	 * yml中xlx.showsql=true时生效
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name = "xlx.showsql",havingValue = "true")
	SqlStatementInterceptor sqlStatementInterceptor(){
		return new SqlStatementInterceptor();
	}
}
