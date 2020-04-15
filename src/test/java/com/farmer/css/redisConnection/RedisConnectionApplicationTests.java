package com.farmer.css.redisConnection;

import ai.grakn.redismock.RedisServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RunWith(SpringRunner.class)
public class RedisConnectionApplicationTests {

	public static final String DEFAULT_KEY = "defaultKey";
	public static final String DEFAULT_VALUE = "defaultValue";

	@Test
	public void checkConnections() throws Exception {
		RedisServer server = RedisServer.newRedisServer();
		server.start();
		JedisPool jedisPool = new JedisPool(server.getHost(), server.getBindPort());
		Jedis jedis = jedisPool.getResource();
		jedis.set(DEFAULT_KEY, DEFAULT_VALUE);
		String sd = jedis.get(DEFAULT_KEY);
		System.out.println("RETRIEVED VALUES : "+sd);
		jedis.close();
		server.stop();
	}

}
