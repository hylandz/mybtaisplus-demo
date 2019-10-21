package com.xlx.shiro.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xlx.shiro.common.listener.ShiroSessionListener;
import com.xlx.shiro.common.util.ShiroUtils;
import com.xlx.shiro.system.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.xlx.shiro.system.shiro.filter.CustomUserFilter;
import com.xlx.shiro.system.shiro.realm.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro注解配置
 *
 * @author xielx on 2019/7/23
 */
@Slf4j
@Configuration
public class ShiroConfig {
	
	
	/*@Value("${spring.redis.host:127.0.0.1}")
	private String host;
	
	@Value("${spring.redis.port:6379}")
	private int port;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.database:0}")
	private int database;*/



	/**
	 * Redis管理
	 *
	 * @return RedisManager
	 */
	private RedisManager redisManager() {
		log.info("Initializing  *redisManager");
		//log.info("host={},port={},password={},timeout={},database={}",host,port,password,timeout,database);
		
		RedisManager redisManager = new RedisManager();
		redisManager.setHost("127.0.0.1");
		redisManager.setPort(6379);
		/*if (StringUtils.isNotBlank(password)) {
			redisManager.setPassword(password);
		}*/
		redisManager.setTimeout(0);
		redisManager.setDatabase(0);
		/*redisManager.setHost(host);
		redisManager.setPort(port);
		if (StringUtils.isNotBlank(password)) {
			redisManager.setPassword(password);
		}
		redisManager.setTimeout(timeout);
		redisManager.setDatabase(database);*/
		
		
		return redisManager;
	}


	/**
	 * shiro的Redis 缓存管理
	 * @return RedisCacheManager
	 */
	private RedisCacheManager cacheManager() {
		log.info("Initializing  *cacheManager");
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * Shiro的Web过滤器
	 *
	 * @param securityManager 安全管理器
	 * @return ShiroFilterFactoryBean
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) {
		log.info("Initializing  *shiroFilter");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		//设置过滤器
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("user", new CustomUserFilter());
		shiroFilterFactoryBean.setFilters(filters);

		//设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//登录url
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登录成功后跳转url
		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权跳转url
		shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");

		//设置过滤链
		Map<String, String> filterChainDefinitions = new LinkedHashMap<>();

		// 不拦截
		filterChainDefinitions.put("/css/**", "anon");
		filterChainDefinitions.put("/js/**", "anon");
		filterChainDefinitions.put("/fonts/**", "anon");
		filterChainDefinitions.put("/img/**", "anon");
		filterChainDefinitions.put("/druid/**", "anon");
		filterChainDefinitions.put("/login", "anon");
		filterChainDefinitions.put("/register", "anon");
		filterChainDefinitions.put("/gifCode", "anon");
		filterChainDefinitions.put("/actuator/**", "anon");
		filterChainDefinitions.put("/test/**", "anon");

		// 登出logout
		filterChainDefinitions.put("/logout", "logout");

		//认证通过才能访问
		filterChainDefinitions.put("/**", "user");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);

		return shiroFilterFactoryBean;
	}


	/**
	 * 自定义凭证匹配器 HashedCredentialsMatcher
	 */
	@Bean("credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		log.info("Initializing  *credentialsMatcher");
		//CacheManager
		HashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
		//密码匹配设置:采用的加密算法md5,迭代次数2,使用16进制编码存储密码
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	/**
	 * 自定义Realm
	 */
	@Bean(name = "userRealm")
	public UserRealm userRealm() {
		log.info("Initializing  *userRealm");
		UserRealm userRealm = new UserRealm();
		//使用自定义的CredentialsMatcher
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return userRealm;
	}

	/**
	 * 安全管理器 SecurityManager
	 *
	 * @return obj
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager() {
		log.info("Initializing  *securityManager");
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// Realm
		defaultWebSecurityManager.setRealm(userRealm());
		//RememberMeManager
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
		//CacheManager
		defaultWebSecurityManager.setCacheManager(cacheManager());
		//SessionManager
		defaultWebSecurityManager.setSessionManager(sessionManager());

		return defaultWebSecurityManager;
	}


	/**
	 * RedisSession的处理
	 * sessionDAO的CRUD
	 *
	 * @return RedisSessionDAO
	 */
	@Bean
	public RedisSessionDAO sessionDAO() {
		log.info("Initializing  *sessionDAO");
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}



	/**
	 * 记住我 RememberMe
	 *
	 * @return SimpleCookie
	 */
	private SimpleCookie rememberMeCookie() {
		log.info("Initializing  *rememberMeCookie");
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		//过期时间,单位:秒;此处设置1天
		simpleCookie.setMaxAge(24 * 60 * 60);
		simpleCookie.setPath("/");
		return simpleCookie;
	}


	/**
	 * 记住我管理器 RememberMeManager
	 *
	 * @return obj
	 */
	private CookieRememberMeManager rememberMeManager() {
		log.info("Initializing  *rememberMeManager");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥
		String rememberKey = ShiroUtils.generateCipherKeyKey("xlx_shiro_key");
		cookieRememberMeManager.setCipherKey(Base64.decode(rememberKey));
		return cookieRememberMeManager;
	}


	/**
	 * 会话管理 SessionManager
	 *
	 * @return obj
	 */
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager sessionManager() {
		log.info("Initializing  *sessionManager");

		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		ArrayList<SessionListener> listeners = new ArrayList<>();
		listeners.add(new ShiroSessionListener());
		//设置全局session超时时间,单位/毫秒,此处30min
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);
		//SessionDAO
		defaultWebSessionManager.setSessionListeners(listeners);
		//Shiro去掉URL中的JSESSIONID
		defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		defaultWebSessionManager.setSessionDAO(sessionDAO());
		defaultWebSessionManager.setSessionIdUrlRewritingEnabled(false);
		return defaultWebSessionManager;
	}




	/**
	 * 安全管理器工厂 Factory
	 * 相当于:SecurityUtils.setSecurityManager(securityManager)
	 *
	 * @return obj
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
		log.info("Initializing  *methodInvokingFactoryBean");
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		//SecurityManager
		methodInvokingFactoryBean.setArguments(securityManager);
		return methodInvokingFactoryBean;
	}



	/**
	 * thymeleaf使用shiro标签
	 *
	 * @return obj
	 */
	@Bean
	public ShiroDialect dialectForThymeleaf() {
		log.info("Initializing  *dialectForThymeleaf");
		return new ShiroDialect();
	}


	/**
	 * Shiro的生命周期处理器 Lifecycle
	 *
	 * @return obj
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		log.info("Initializing  *lifecycleBeanPostProcessor");
		return new LifecycleBeanPostProcessor();
	}
	/*@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}*/


	/**
	 * 开启shiro的权限注解
	 *
	 * @return obj
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		log.info("Initializing  *shiroAnnotation");
		AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		//SecurityManager
		attributeSourceAdvisor.setSecurityManager(securityManager);
		return attributeSourceAdvisor;
	}

}


