package com.tw.consumer.config;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * 
 * @author xiesc
 * @TODO redis哨兵版
 * @time 2018年9月10日
 * @version 1.0
 */
public abstract class SentinelRedisConfig {

	protected final static JedisSentinelPool pool;

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(Config.getInstance().getRedisTotal());
		config.setMaxIdle(Config.getInstance().getRedisIdle());
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		pool = new JedisSentinelPool("mymaster", Config.getInstance().getRedisSentinels(),
				config, 5000 , Config.getInstance()
						.getRedisPwd());
	}

	protected abstract void close();
}
