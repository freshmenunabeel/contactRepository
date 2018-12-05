package com.contactBook.contactBook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class CachingConfig {

	@Value("${cache.redis.port}")
	private int redisPort;

	@Value("${cache.redis.host}")
	private String redisHost;

	@Primary
	@Bean
	public JedisConnectionFactory getRedisJedisConnection() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setTestWhileIdle(true);
		jedisPoolConfig.setTestOnBorrow(true);
		JedisConnectionFactory connection = new org.springframework.data.redis.connection.jedis.JedisConnectionFactory(
				jedisPoolConfig);
		connection.setPort(redisPort);
		connection.setHostName(redisHost);

		return connection;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

}
