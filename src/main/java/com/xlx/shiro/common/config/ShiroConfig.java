package com.xlx.shiro.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.xlx.shiro.system.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.xlx.shiro.system.shiro.filter.SysUserFilter;
import com.xlx.shiro.system.shiro.realm.UserRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro注解配置
 *
 * @author xielx on 2019/7/23
 */
@Configuration
public class ShiroConfig {

	private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);


	/**
	 * 参考Web.xml设置的shiro过滤器
	 *
	 * @return obj
	 */
	@Bean
	public FilterRegistrationBean<Filter> shiroFilterRegistration() {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

		DelegatingFilterProxy proxy = new DelegatingFilterProxy("shiroFilter");
		proxy.setTargetFilterLifecycle(true);

		registrationBean.setFilter(proxy);
		registrationBean.setEnabled(true);
		registrationBean.setAsyncSupported(true);
		registrationBean.addUrlPatterns("/*");
		registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
		return registrationBean;
	}


	/**
	 * 缓存管理器 CacheManager
	 */
	@Bean(name = "cacheManager")
	public EhCacheManager cacheManager() {
		logger.info("****cacheManager");
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");

		return ehCacheManager;
	}

	/**
	 * 自定义Realm
	 */
	@Bean(name = "userRealm")
	public UserRealm userRealm(@Qualifier("credentialsMatcher") HashedCredentialsMatcher hashedCredentialsMatcher) {
		logger.info("****userRealm");
		UserRealm userRealm = new UserRealm();
		//使用自定义的CredentialsMatcher
		userRealm.setCredentialsMatcher(hashedCredentialsMatcher);
		return userRealm;
	}

	/**
	 * 凭证匹配器 HashedCredentialsMatcher
	 */
	@Bean("credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		logger.info("****credentialsMatcher");
		//CacheManager
		HashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(cacheManager());
		//密码匹配设置:采用的加密算法md5,迭代次数2,使用16进制编码存储密码
		credentialsMatcher.setHashAlgorithmName("md5");
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}


	/**
	 * 会话DAO SessionDAO
	 */
	/*@Bean(name = "sessionDAO")
	public SessionDAO sessionDAO(JavaUuidSessionIdGenerator sessionIdGenerator) {
		logger.info("****sessionDAO");
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		//SessionIdGenerator
		enterpriseCacheSessionDAO.setSessionIdGenerator(sessionIdGenerator);
		return enterpriseCacheSessionDAO;
	}*/

	/**
	 * 会话Cookie模板 sessionIdCookie
	 * cookie1
	 */
	/*@Bean
	public Cookie sessionIdCookie() {
		logger.info("****sessionIdCookie");
		SimpleCookie simpleCookie = new SimpleCookie("sessionIdCookie");
		simpleCookie.setHttpOnly(true);
		//单位:秒,此处设置浏览器关闭即失效
		simpleCookie.setMaxAge(-1);
		simpleCookie.setPath("/");
		return simpleCookie;
	}*/


	/**
	 * 会话ID生成器 SessionIdGenerator
	 *
	 * @return obj
	 */
	/*@Bean(name = "sessionIdGenerator")
	public JavaUuidSessionIdGenerator sessionIdGenerator() {
		logger.info("****sessionIdGenerator");
		return new JavaUuidSessionIdGenerator();
	}*/


	/**
	 * 记住我 RememberMeCookie
	 * cookie2
	 *
	 * @return I
	 */
	@Bean
	public Cookie rememberMeCookie() {
		logger.info("****rememberMeCookie");
		SimpleCookie simpleCookie = new SimpleCookie("rememberMeCookie");
		simpleCookie.setHttpOnly(true);
		//单位:秒;此处设置3天
		simpleCookie.setMaxAge(3 * 24 * 60 * 60);
		simpleCookie.setPath("/");
		return simpleCookie;
	}


	/**
	 * 记住我管理器 RememberMeManager
	 *
	 * @return obj
	 */
	@Bean(name = "rememberMeManager")
	public CookieRememberMeManager rememberMeManager(Cookie rememberMeCookie) {
		logger.info("****rememberMeManager");
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		//cookie加密的密钥
		cookieRememberMeManager.setCipherKey(Base64.decode("c3ByaW5nYm9vdHNoaXJv"));
		//RememberMeCookie
		cookieRememberMeManager.setCookie(rememberMeCookie);
		return cookieRememberMeManager;
	}


	/**
	 * 会话管理 SessionManager
	 *
	 * @return obj
	 */
	@Bean(name = "sessionManager")
	public DefaultWebSessionManager sessionManager(Cookie sessionIdCookie) {
		logger.info("****sessionManager");
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		//设置全局session超时时间,单位/毫秒,此处30min
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);

		//Quartz会话定时刷新
		//defaultWebSessionManager.setSessionValidationScheduler(scheduler);
		//SessionDAO
		//defaultWebSessionManager.setSessionDAO(sessionDAO);
		//SessionIdCookie
		defaultWebSessionManager.setSessionIdCookieEnabled(true);
		defaultWebSessionManager.setSessionIdCookie(sessionIdCookie);
		return defaultWebSessionManager;
	}

	/*@Bean(name = "sessionValidationScheduler")
	public QuartzSessionValidationScheduler sessionValidationScheduler(DefaultWebSessionManager sessionManager) {
		logger.info("*****sessionValidationScheduler");
		QuartzSessionValidationScheduler quartz = new QuartzSessionValidationScheduler();
		//session刷新时间间隔,单位/毫秒,此处设置30min
		quartz.setSessionValidationInterval(1800000);
		//SessionManager
		quartz.setSessionManager(sessionManager);
		return quartz;
	}*/

	/**
	 * 安全管理器 SecurityManager
	 *
	 * @return obj
	 */
	@Bean(name = "securityManager")
	public SecurityManager securityManager(UserRealm userRealm, CookieRememberMeManager rememberMeManager,
																				 EhCacheManager cacheManager, SessionManager sessionManager) {
		logger.info("****securityManager");
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// Realm
		defaultWebSecurityManager.setRealm(userRealm);
		//RememberMeManager
		defaultWebSecurityManager.setRememberMeManager(rememberMeManager);
		//CacheManager
		defaultWebSecurityManager.setCacheManager(cacheManager);
		//SessionManager
		defaultWebSecurityManager.setSessionManager(sessionManager);

		return defaultWebSecurityManager;
	}


	/**
	 * 安全管理器工厂 Factory
	 * 相当于:SecurityUtils.setSecurityManager(securityManager)
	 *
	 * @return obj
	 */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
		logger.info("****methodInvokingFactoryBean");
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		//SecurityManager
		methodInvokingFactoryBean.setArguments(securityManager);
		return methodInvokingFactoryBean;
	}

	/**
	 * 基于Form表单的身份验证过滤器 FormAuthenticationFilter
	 *
	 * @return obj
	 */
	@Bean(name = "authcFilter")
	public FormAuthenticationFilter formAuthenticationFilter() {
		logger.info("****formAuthenticationFilter");
		FormAuthenticationFilter formFilter = new FormAuthenticationFilter();
		formFilter.setUsernameParam("username");
		formFilter.setPasswordParam("password");
		formFilter.setRememberMeParam("rememberMe");
		return formFilter;
	}


	/**
	 * 自定义过滤器
	 *
	 * @return obj
	 */
	@Bean(name = "sysUserFilter")
	public PathMatchingFilter sysUserFilter() {
		logger.info("****sysUserFilter");
		return new SysUserFilter();
	}


	/**
	 * thymeleaf使用shiro标签
	 *
	 * @return obj
	 */
	@Bean
	public ShiroDialect dialectForThymeleaf() {
		logger.info("****dialectForThymeleaf");
		return new ShiroDialect();
	}

	/**
	 * Shiro的Web过滤器 ShiroFilterFactoryBean
	 *
	 * @param securityManager 安全管理器
	 * @return .
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactory(@Qualifier("securityManager") SecurityManager securityManager) {
		logger.info("***shiroFilterFactory");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//登录url
		shiroFilterFactoryBean.setLoginUrl("/login");
		//登录成功后跳转url
		//未授权跳转url
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");


		//设置过滤器
		Map<String, Filter> filters = new LinkedHashMap<>();
		filters.put("authc", formAuthenticationFilter());
		filters.put("sysUser", sysUserFilter());
		shiroFilterFactoryBean.setFilters(filters);

		//设置过滤链
		Map<String, String> filterChainDefinitions = new LinkedHashMap<>();
		filterChainDefinitions.put("/favicon.ico**", "anon");
		filterChainDefinitions.put("/ruoyi.png**", "anon");
		filterChainDefinitions.put("/css/**", "anon");
		filterChainDefinitions.put("/docs/**", "anon");
		filterChainDefinitions.put("/fonts/**", "anon");
		filterChainDefinitions.put("/img/**", "anon");
		filterChainDefinitions.put("/ajax/**", "anon");
		filterChainDefinitions.put("/js/**", "anon");
		filterChainDefinitions.put("/ruoyi/**", "anon");

		filterChainDefinitions.put("/login", "authc");
		filterChainDefinitions.put("/logout", "logout");
		filterChainDefinitions.put("/authenticated", "authc");
		filterChainDefinitions.put("/**", "user,sysUser");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
		return shiroFilterFactoryBean;
	}

	/**
	 * Shiro的生命周期处理器 Lifecycle
	 *
	 * @return obj
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
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
	public AuthorizationAttributeSourceAdvisor shiroAnnotation(SecurityManager securityManager) {
		logger.info("***shiroAnnotation");
		AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		//SecurityManager
		attributeSourceAdvisor.setSecurityManager(securityManager);
		return attributeSourceAdvisor;
	}

}


