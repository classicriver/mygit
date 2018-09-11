package com.tw.consumer.config;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * @author xiesc
 * @TODO redis单机版
 * @time 2018年9月10日
 * @version 1.0
 */
public class SingleRedisConfig {
	
	protected final static JedisPool pool;
	
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Config.getInstance().getRedisTotal());
        config.setMaxIdle(Config.getInstance().getRedisIdle());
        config.setMaxWaitMillis(3000);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        pool = new JedisPool(config, Config.getInstance().getRedisHost(), Config.getInstance().getRedisPort());
    }
}
