package com.xlx.shiro.common.service;

import com.xlx.shiro.common.entity.RedisInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: xielx on 2019/9/4
 */
public interface IRedisService {
	
	/**
	 * 获取redis信息
	 * @return
	 */
	List<RedisInfo> getRedisInfo();
	
	/**
	 * 获取redis key
	 * @param pattern 正则
	 * @return set
	 */
	Set<String> getKeys(String pattern);
	
	/**
	 * 获取redis key数量
	 * @return map
	 */
	Map<String,Object> getKeySize();
	
	
	/**
	 * 获取 redis 内存信息
	 *
	 * @return Map
	 */
	Map<String, Object> getMemoryInfo();
	
	/**
	 * get命令
	 * @param key k
	 * @return String
	 */
	String get(String key);
	
	/**
	 * set命令
	 * @param key k
	 * @param value v
	 * @return String
	 */
	String set(String key,String value);
	
	/**
	 * del命令
	 * @param key k
	 * @return long
	 */
	Long del(String... key);
	
	/**
	 * ttl
	 * @param key k
	 * @return long
	 */
	Long ttl(String key);
	
	/**
	 * expire命令
	 * @param key k
	 * @param millSeconds 秒
	 * @return long
	 */
	Long expire(String key,Long millSeconds);
}
