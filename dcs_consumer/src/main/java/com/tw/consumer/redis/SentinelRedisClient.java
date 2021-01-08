package com.tw.consumer.redis;

import redis.clients.jedis.BinaryJedisPubSub;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.tw.consumer.config.SentinelRedisConfig;
import com.tw.consumer.utils.ObjectTranscoder;
/**
 * 
 * @author xiesc
 * @TODO redis客户端  线程安全
 * @time 2018年9月10日
 * @version 1.0
 */
public class SentinelRedisClient extends SentinelRedisConfig{

	public Jedis getJedis() {
		return pool.getResource();
	}
	
	/**
	 * The command is exactly equivalent to the following group of commands: SET + EXPIRE. The operation is atomic. 
	 * @param key
	 * @param seconds
	 * @param value
	 */
	public void setex(byte[] key,int seconds, byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.setex(key , seconds , value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	/**
	 * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1 GB). 
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.set(key , value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	/**
	 * SETNX actually means "SET if Not eXists". 
	 * @param key
	 * @param value
	 */
	public void setnx(byte[] key, byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.setnx(key, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	/**
	 * Add the specified member to the set value stored at key. 
	 * @param key
	 * @param value
	 */
	public void sadd(byte[] key, byte[] value){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			p.sadd(key, value);
			p.sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	/**
	 * Add the specified member to the set value stored at key. use pipelined. 
	 * @param key
	 * @param value
	 */
	public void saddByPipeLine(byte[] key, byte[]... values){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			Pipeline p = jedis.pipelined();
			p.sadd(key, values);
			p.sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T  get(String key,Class<T> clazz){
		Jedis jedis = null;
		T obj = null;
		try{
			jedis = getJedis();
			obj = (T) ObjectTranscoder.deserialize(jedis.get(key.getBytes()));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
		return obj;
	}
	
	/**
	 * redis订阅
	 * @param jedisPubSub
	 * @param channels
	 */
	public void subscribe(final BinaryJedisPubSub jedisPubSub,byte[]... channels){
		Jedis jedis = null;
		try{
			jedis = getJedis();
			jedis.subscribe(jedisPubSub, channels);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			jedis.close();
		}
	}
	
	@Override
	protected void close() {
		// TODO Auto-generated method stub
		pool.close();
	}

}
