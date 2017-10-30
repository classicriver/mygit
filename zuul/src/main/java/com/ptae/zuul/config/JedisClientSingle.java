package com.ptae.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

/**
 * 
 * @Description: TODO(单机版redis客户端)
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
@Component("jedisClient")
public class JedisClientSingle implements JedisClient{
	
	@Autowired
	private JedisConnectionFactory jedisPool; 
	
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getShardInfo().createResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}


}
