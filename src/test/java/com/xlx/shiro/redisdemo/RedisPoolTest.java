package com.xlx.shiro.redisdemo;

import com.xlx.shiro.common.service.IRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * jedispool
 *
 * @author xielx on 2019/9/5
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisPoolTest {
	
	@Resource
	private IRedisService redisService;
	
	@Test
	public void testString(){
		String statusCodeReply = redisService.set("k1","v1");
		String bulkReply = redisService.get("k1");
		System.out.println("statusCodeReply=" + statusCodeReply + ",bulkReply=" + bulkReply);
	}
	
	
}
