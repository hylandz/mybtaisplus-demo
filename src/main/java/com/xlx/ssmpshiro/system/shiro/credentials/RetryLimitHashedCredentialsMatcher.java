package com.xlx.ssmpshiro.system.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 登录次数限制
 *
 * @author xielx on 2019/7/22
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


    private Cache<String, AtomicInteger> passwordRetryCache;
    
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
        this.passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }
    
    
    /**
     * 限制验证次数
     * @param token 前台数据
     * @param info 后台数据
     * @return true:密码匹配
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String principal = (String) token.getPrincipal();
        AtomicInteger retryCount = passwordRetryCache.get(principal);
        if (retryCount == null){
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(principal,retryCount);
        }
        
        if (retryCount.incrementAndGet() > 5){
            throw new ExcessiveAttemptsException("连续登录错误5次,10分钟后尝试");
        }
    
        boolean match = super.doCredentialsMatch(token, info);
        if (match){
            passwordRetryCache.remove(principal);
        }
        return match;
    }
}
