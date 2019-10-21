package com.xlx.shiro.common.util.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类:StringRedisTemplate
 *
 * @author xielx on 2019/9/4
 */
@Component
public class StringRedisTemplateUtils {
	
	@Resource
	private  StringRedisTemplate stringRedisTemplate;
	
	
	/**
	 * get命令
	 * @param key k
	 * @return String
	 */
	public String get(String key){
	   return stringRedisTemplate.opsForValue().get(key);
	}
	
	/**
	 * set命令
	 * @param key k
	 * @param value v
	 */
	public void set(String key,String value){
		stringRedisTemplate.opsForValue().set(key,value);
	}
	
	/**
	 * set命令,会检查
	 * @param key k
	 * @param value v
	 * @return boolean
	 */
	public Boolean setIfAbsent(String key,String value){
		return stringRedisTemplate.opsForValue().setIfAbsent(key,value);
	}
	
	/**
	 * set命令带有时间
	 * @param key k
	 * @param value v
	 * @param time t
	 * @param unit mail/second/hour
	 */
	public void setWithTime(String key, String value, Long time, TimeUnit unit){
		stringRedisTemplate.opsForValue().set(key,value,time,unit);
	}
	
	/**
	 * 自增
	 * @param key k
	 */
	public Long increment(String key){
		return stringRedisTemplate.opsForValue().increment(key);
	}
	
	/**
	 * 自增按增量
	 * @param key k
	 * @param delta 增量
	 * @return long
	 */
	public Long increment(String key,Long delta){
		return stringRedisTemplate.opsForValue().increment(key,delta);
	}
	
	/**
	 * 自减
	 * @param key k
	 * @return long
	 */
	public Long decrement(String key){
		return stringRedisTemplate.opsForValue().decrement(key);
	}
	
	/**
	 * 自减按比例
	 * @param key k
	 * @param delta 比例
	 * @return
	 */
	public Long decrement(String key,long delta){
		return stringRedisTemplate.opsForValue().decrement(key,delta);
	}
	
	
	/**
	 * 删除命令
	 * @param key k
	 */
	public Boolean delete(String key){
		return stringRedisTemplate.delete(key);
	}
	
	/**
	 * 设置过期时间
	 * @param key k
	 * @param time t
	 * @param unit 时间单位
	 * @return
	 */
	public Boolean expire(String key,long time,TimeUnit unit){
		return stringRedisTemplate.expire(key,time,unit);
	}
	
	/**
	 * 获取ttl
	 * @param key k
	 * @return long
	 */
	public Long getExpire(String key){
		return stringRedisTemplate.getExpire(key);
	}
	
	/**
	 * 获取ttl
	 * @param key k
	 * @param unit 指定时间单位
	 * @return long
	 */
	public Long getExpire(String key,TimeUnit unit){
		return stringRedisTemplate.getExpire(key,unit);
	}
	
	
}
