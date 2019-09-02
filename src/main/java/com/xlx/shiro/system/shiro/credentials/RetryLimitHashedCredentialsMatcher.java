package com.xlx.shiro.system.shiro.credentials;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.crazycake.shiro.RedisCache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义的密码匹配验证
 *
 * @author xielx on 2019/7/22
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


	private Cache<String, AtomicInteger> passwordRetryCache;

	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager){
		passwordRetryCache = cacheManager.getCache("passwordRetryCache");
	}

	/**
	 * 密码匹配
	 * @param token 前台登录信息
	 * @param info 数据库信息
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		AtomicInteger retryCount = passwordRetryCache.get(username);
		if (retryCount == null){
			retryCount = new AtomicInteger(0);
			passwordRetryCache.put(username,retryCount);
		}

		if (retryCount.incrementAndGet() > 5){
			//连续尝试登录失败超过5次
			log.error("*********帐号:[{}]被锁定,请10分钟后重试******",username);
			throw new ExcessiveAttemptsException("帐号被锁,请10分钟后重试");
		}

		boolean matches = super.doCredentialsMatch(token,info);

		log.info("匹配结果:[{}]",matches);
		if (matches){
			passwordRetryCache.remove(username);
		}

		return matches;
	}
}
