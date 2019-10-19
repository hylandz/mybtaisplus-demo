package com.xlx.ssmpshiro.system.shiro.config;

import com.xlx.ssmpshiro.system.shiro.realm.UserRealm;
import net.sf.ehcache.Cache;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro配置
 *
 * @author xielx on 2019/8/28
 */
@Configuration
public class ShiroConfig {
    
    @Value("${ssmp.shiro.crypto.algorithmName}")
    private String algorithmName;
    
    @Value("${ssmp.shiro.crypto.hashIterations}")
    private Integer hashIterations;
    
    
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
    }

    @Bean
    public UserRealm userRealm(){
        UserRealm realm = new UserRealm();
        return realm;
    }
    
    @Bean
    public CredentialsMatcher hashedCredentialsMatcher(CacheManager cacheManager){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(cacheManager);
        credentialsMatcher.setHashAlgorithmName(algorithmName);
        credentialsMatcher.setHashIterations(hashIterations);
        // Hex格式存储
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    
    
}
