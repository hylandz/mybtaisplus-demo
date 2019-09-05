package com.xlx.shiro.common.service.impl;

import afu.org.checkerframework.checker.oigj.qual.O;
import com.xlx.shiro.common.entity.RedisInfo;
import com.xlx.shiro.common.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;

/**
 * redis
 *
 * @author xielx on 2019/9/4
 */
@Slf4j
@Service("redisService")
@SuppressWarnings("unchecked")
public class RedisServiceImpl implements IRedisService {
	
	@Resource
	private JedisPool jedisPool;
	
	/**
	 * jedis连接
	 * @param function 函数式接口
	 * @return object
	 */
	private Object execute(Function<Jedis,Object> function){
		try(Jedis jedis = jedisPool.getResource()){
			return function.apply(jedis);
		}catch (Exception e){
		log.error("JedisPool execute exception:{}",e.getMessage());
		return null;
		}
	}
	@Override
	public List<RedisInfo> getRedisInfo() {
		String info = (String) this.execute(
						j -> {
							Client client = j.getClient();
							client.info();
							return client.getBulkReply();
						}
		);
		List<RedisInfo> infoList = new ArrayList<>();
		String[] strs = Objects.requireNonNull(info).split("\n");
		RedisInfo redisInfo;
		if (strs.length > 0) {
			for (String str1 : strs) {
				redisInfo = new RedisInfo();
				String[] str = str1.split(":");
				if (str.length > 1) {
					String key = str[0];
					String value = str[1];
					redisInfo.setKey(key);
					redisInfo.setValue(value);
					infoList.add(redisInfo);
				}
			}
		}
		return infoList;
	}
	
	@Override
	public Set<String> getKeys(String pattern) {
		return (Set<String>) this.execute(jedis -> jedis.keys(pattern));
	}
	
	@Override
	public Map<String, Object> getKeySize() {
		//获取数据库数据个数
		 Long dbsize = (Long) this.execute(
						jedis -> {
							final Client client = jedis.getClient();
							client.dbSize();
							return client.getIntegerReply();
						}
		);
		
		 //返回
		Map<String,Object> map = new HashMap<>();
		map.put("createTime",System.currentTimeMillis());
		map.put("dbSize",dbsize);
		return map;
	}
	
	@Override
	public Map<String, Object> getMemoryInfo() {
		return null;
	}
	
	@Override
	public String get(String key) {
		return (String) this.execute(jedis -> jedis.get(key));
	}
	
	@Override
	public String set(String key, String value) {
		return (String) this.execute(jedis -> jedis.set(key,value));
	}
	
	
	@Override
	public Long del(String... key) {
		return (Long) this.execute(jedis -> jedis.del(key));
	}
	
	@Override
	public Long ttl(String key) {
		return (Long) this.execute(jedis -> jedis.pttl(key));
	}
	
	@Override
	public Long expire(String key, Long millSecond) {
		return (Long) this.execute(jedis -> jedis.pexpire(key,millSecond));
	}
}
