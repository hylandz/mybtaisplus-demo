package com.xlx.shiro.system.shiro.credentials;

import com.xlx.shiro.common.util.redis.StringRedisTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的密码匹配验证
 *
 * @author xielx on 2019/7/22
 */
@Slf4j
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {


	@Resource
	private StringRedisTemplateUtils stringRedisTemplateUtil;


	/**
	 * 密码匹配
	 * @param token 前台登录信息
	 * @param info 数据库信息
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		String retryCount = stringRedisTemplateUtil.get(username);
		Long ttl = stringRedisTemplateUtil.getExpire(username,TimeUnit.SECONDS);
		if (retryCount == null){
			retryCount = "0";
			stringRedisTemplateUtil.set(username,retryCount);
		}else if (ttl != null && ttl > 0){
			throw new ExcessiveAttemptsException("帐号被锁,请10分钟后重试");
		}
		
		Long times = stringRedisTemplateUtil.increment(username);
		if (times > 5){
			//连续尝试登录失败超过5次
			log.error("*********帐号:[{}]被锁定,请10分钟后重试******",username);
			stringRedisTemplateUtil.expire(username,10L, TimeUnit.MINUTES);
			throw new ExcessiveAttemptsException("帐号被锁,请10分钟后重试");
		}

		
		boolean matches = super.doCredentialsMatch(token,info);

		log.info("匹配结果:[{}]",matches);
		if (matches){
			stringRedisTemplateUtil.delete(username);
		}

		return matches;
	}
}
