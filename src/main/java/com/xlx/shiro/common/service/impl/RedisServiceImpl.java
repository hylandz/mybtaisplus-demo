package com.xlx.shiro.common.service.impl;

import com.xlx.shiro.common.entity.RedisInfo;
import com.xlx.shiro.common.service.IRedisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * redis
 *
 * @author xielx on 2019/9/4
 */
@Service("redisService")
public class RedisServiceImpl implements IRedisService {
	
	
	@Override
	public List<RedisInfo> getRedisInfo() {
		return null;
	}
	
	@Override
	public Set<String> getKeys(String pattern) {
		return null;
	}
	
	@Override
	public String get(String key) {
		return null;
	}
	
	@Override
	public String set(String key, String value) {
		return null;
	}
}
