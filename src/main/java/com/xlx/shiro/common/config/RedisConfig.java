package com.xlx.shiro.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.Duration;

/**
 * redis配置
 *
 * @author xielx on 2019/9/3
 */
@Slf4j
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.password}")
	private String password;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.jedis.pool.max-wait}")
	private int maxWait;
	
	@Value("${spring.redis.database}")
	private int database;
	
	
	/**
	 * redis的连接池:JedisPool
	 *
	 * @return JedisPool
	 */
	@Bean
	public JedisPool redisPoolFactory() {
		log.info("Initializing  *JedisPool");
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		if (StringUtils.isNotBlank(password)) {
			return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
		} else {
			return new JedisPool(jedisPoolConfig, host, port, timeout, null, database);
		}
	}
	
	/**
	 * Jedis连接
	 * @return JedisConnectionFactory
	 */
	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		log.info("Initializing  *jedisConnectionFactory");
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		
		redisStandaloneConfiguration.setHostName(host);
		redisStandaloneConfiguration.setPort(port);
		redisStandaloneConfiguration.setPassword(password);
		redisStandaloneConfiguration.setDatabase(database);
		
		JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
		jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
		jedisClientConfiguration.usePooling();
		return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration.build());
	}
	
	
	/**
	 * redis的模板:RedisTemplate
	 * tip:
	 *    默认序列化使用JdkSerializationRedisSerializer
	 *    使用StringRedisSerializer,防止RedisTemplate的key指定成StringRedisSerializer
	 *    序列化会报类型转换错误
	 *
	 * @param redisConnectionFactory factory
	 * @return RedisTemplate
	 */
	@Bean(name = "redisTemplate")
	@SuppressWarnings({"unchecked", "rawtypes"})
	@ConditionalOnBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		log.info("Initializing  *redisTemplate");
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		
		// 使用StringRedisSerializer来序列化key值
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		template.setKeySerializer(stringRedisSerializer);
		template.setHashValueSerializer(stringRedisSerializer);
		
		// 使用FastJsonRedisSerializer来序列化value值
		FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		template.setValueSerializer(fastJsonRedisSerializer);
		template.setHashValueSerializer(fastJsonRedisSerializer);
		
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
	
	
	/**
	 * redis的StringRedisTemplate
	 * 序列化默认使用:StringRedisSerializer
	 * @param redisConnectionFactory factory
	 * @return StringRedisTemplate
	 */
	@Bean
	@ConditionalOnBean(StringRedisTemplate.class)
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		log.info("Initializing  *stringRedisTemplate");
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		return stringRedisTemplate;
	}
	
	/**
	 * redis限流模板
	 * @param redisConnectionFactory factory
	 * @return RedisTemplate
	 */
	//@Bean
	public RedisTemplate<String, Serializable> limitRedisTemplate(RedisConnectionFactory redisConnectionFactory){
		log.info("Initializing  *limitRedisTemplate");
		RedisTemplate<String,Serializable> limitTemplate = new RedisTemplate<>();
		// 序列化
		limitTemplate.setKeySerializer(new StringRedisSerializer());
		limitTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		limitTemplate.setConnectionFactory(redisConnectionFactory);
		return limitTemplate;
	}
	
	
	
	/**
	 * redis的缓存管理
	 * @param redisConnectionFactory factory
 	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		log.info("Initializing  *cacheManager");
		RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager
						                                                     .RedisCacheManagerBuilder
						                                                     .fromConnectionFactory(redisConnectionFactory);
		return builder.build();
	}
	
	
	
	/**
	 * redis自定义cache key的生成方式
	 * 解释:
	 * spring boot有默认的缓存键值生成方式,但是有缺陷,当参数不是数组/null时以本身作为key
	 * 这样就会有很多重复的缓存键值
	 * 所以需要自定义: 类名+方法名+参数
	 * @return KeyGenerator
	 */
	@Bean
	public KeyGenerator keyGenerator(){
		log.info("Initializing  *keyGenerator");
		return (target,method,params) -> {
			StringBuilder builder = new StringBuilder();
			builder.append(target.getClass().getName())
							.append(method.getName());
			
			for (Object p : params) {
				builder.append(p.toString());
			}
			log.info("KeyGenerator is : {}",builder.toString());
			return builder.toString();
		};
		
	  /*return new KeyGenerator() {
		  @Override
		  public Object generate(Object o, Method method, Object... objects) {
		  	StringBuilder builder = new StringBuilder();
		  	builder.append(o.getClass().getName())
								.append(method.getName());
			  for (Object o1 : objects) {
				  builder.append(o1.toString());
			  }
			log.info("KeyGenerator is : {}",builder.toString());
			  return builder.toString();
		  }
	  };*/
	}
}


/**
 * 重写序列化器
 * @param <T> 类型
 */
class FastJsonRedisSerializer<T> implements RedisSerializer<T>{
	
	// 默认编码
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	// 参数对象
	private Class<T> clazz;
	
	FastJsonRedisSerializer(Class<T> clazz) {
		super();
		this.clazz = clazz;
	}
	
	/**
	 * 序列化
	 * @param t T
	 * @return byte[]
	 * @throws SerializationException 序列化异常
	 */
	@Override
	public byte[] serialize(T t) throws SerializationException {
		return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(DEFAULT_CHARSET);
	}
	
	/**
	 * 反序列化
	 * @param bytes byte[]
	 * @return T
	 * @throws SerializationException 序列化异常
	 */
	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null || bytes.length <= 0){
			return null;
		}
		
		String str = new String(bytes,DEFAULT_CHARSET);
		return JSON.parseObject(str,clazz);
	}
}

