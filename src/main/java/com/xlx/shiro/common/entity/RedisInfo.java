package com.xlx.shiro.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * redis配置信息
 *
 * @author xielx on 2019/9/4
 */
public class RedisInfo {
	
	private static Map<String,String> map = new HashMap<>();
	
	static {
		map.put("redis_version", "Redis 服务器版本");
		map.put("redis_git_sha1", "Git SHA1");
		map.put("redis_git_dirty", "Git dirty flag");
		map.put("os", "Redis 服务器的宿主操作系统");
		map.put("arch_bits", " 架构（32 或 64 位）");
		map.put("multiplexing_api", "Redis 所使用的事件处理机制");
		map.put("gcc_version", "编译 Redis 时所使用的 GCC 版本");
		map.put("process_id", "服务器进程的 PID");
		map.put("run_id", "Redis 服务器的随机标识符（用于 Sentinel 和集群）");
		map.put("tcp_port", "TCP/IP 监听端口");
	}
	
	
	
	
	
	
	private String key;
	private String value;
	private String description;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
		this.description = map.get(key);
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "RedisInfo{" +
						       "key='" + key + '\'' +
						       ", value='" + value + '\'' +
						       ", description='" + description + '\'' +
						       '}';
	}
}
