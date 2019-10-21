package com.xlx.shiro.common.util.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * redisTemplate工具类
 *
 * @author xielx on 2019/9/5
 */
@Component
public class RedisTemplateUtils {
	
	@Resource
	private RedisTemplate<Object,Object> redisTemplate;
	
	/**
	 * setIfAbsent
	 * @param key k
	 * @param  v
	 * @return boolean
	 */
	public Boolean setIfAbsent(Object key,Object value){
		return redisTemplate.opsForValue().setIfAbsent(key,value);
	}
	
	/**
	 * set
	 * @param key k
	 * @param value v
	 */
	public void set(Object key,Object value){
		 redisTemplate.opsForValue().set(key,value);
	}
}
