package com.contactBook.contactBook;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisConnection extends DBCOnnectionTest {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void redisConnectiontest() {
		Assert.assertNotNull(redisTemplate);
		redisTemplate.opsForValue().set("name", "nabeel");
		System.out.println(redisTemplate.opsForValue().get("name"));
	}
}
