package com.xlx.shiro.common.config;

import net.sf.ehcache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * ehcache
 *
 * @author xielx on 2019/7/23
 */
//@Configuration
//@EnableCaching
public class EhCacheConfig {

	//@Bean(name = "springCacheManager")
	public EhCacheCacheManager ehCacheCacheManager(CacheManager cacheManager){
		return new EhCacheCacheManager(cacheManager);
	}


	//@Bean(name = "ehCacheManager")
	public EhCacheManagerFactoryBean ehCacheManagerFactory(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		return ehCacheManagerFactoryBean;
	}
}
