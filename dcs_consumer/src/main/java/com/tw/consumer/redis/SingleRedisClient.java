package com.tw.consumer.redis;

import java.util.Map;

import redis.clients.jedis.Jedis;

import com.tw.consumer.config.SingleRedisConfig;
import com.tw.consumer.utils.ObjectTranscoder;
/**
 * 
 * @author xiesc
 * @TODO redis客户端  线程安全
 * @time 2018年9月10日
 * @version 1.0
 */
public class SingleRedisClient extends SingleRedisConfig{

	public Jedis getJedis() {
		return pool.getResource();
	}
	
	public void set(String key,Map<String,Object> value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key.getBytes(), ObjectTranscoder.serialize(value));
		}finally{
			jedis.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> get(String key){
		Jedis jedis = null;
		Map<String,Object> map;
		try{
			jedis = getJedis();
			map = (Map<String, Object>) ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
		}finally{
			jedis.close();
		}
		return map;
	}

}
