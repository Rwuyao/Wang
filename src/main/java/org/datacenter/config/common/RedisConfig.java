package org.datacenter.config.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;


@Configuration
public class RedisConfig {
	/*
	 * @Autowired private RedisConnectionFactory factory;
	 * 
	 * @Bean public RedisTemplate<String, Object> redisTemplate() {
	 * RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
	 * redisTemplate.setKeySerializer(new StringRedisSerializer());
	 * redisTemplate.setHashKeySerializer(new StringRedisSerializer());
	 * redisTemplate.setHashValueSerializer(new StringRedisSerializer());
	 * redisTemplate.setValueSerializer(new FastJsonRedisSerializer(Object.class));
	 * redisTemplate.setConnectionFactory(factory); return redisTemplate; }
	 */
	
}
