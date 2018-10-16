package com.tw.consumer.redis;

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
	
	public void set(byte[] key,byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key, ObjectTranscoder.serialize(value));
		}finally{
			jedis.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T  get(String key,Class<T> clazz){
		Jedis jedis = null;
		T obj;
		try{
			jedis = getJedis();
			obj = (T) ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
		}finally{
			jedis.close();
		}
		return obj;
	}
}
