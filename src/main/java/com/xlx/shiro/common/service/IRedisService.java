package com.xlx.shiro.common.service;

import com.xlx.shiro.common.entity.RedisInfo;

import java.util.List;
import java.util.Set;

/**
 * @author: xielx on 2019/9/4
 */
public interface IRedisService {
	
	List<RedisInfo> getRedisInfo();
	
	/**
	 * 获取所有key
	 * @param pattern 正则
	 * @return set
	 */
	Set<String> getKeys(String pattern);
	
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
	
	
	
}
