package com.ptae.auth.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.JedisPoolConfig;
/**
 * 
 * @Description: TODO(redis配置类)
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
@Configuration
public class RedisDataSource {
	
	@Value("${redis.host}")
	String host;
	@Value("${redis.port}")
	int port;
	@Value("${redis.pass}")
	String pass;
	//连接池配置
	@Value("${redis.maxIdle}")
	int maxIdle;
	@Value("${redis.maxActive}")
	int maxActive;
	@Value("${redis.maxWait}")
	long maxWait;
	@Value("${redis.testOnBorrow}")
	boolean testOnBorrow;
	
	@Resource(name = "jedisPoolConfig")
	JedisPoolConfig jedisPoolConfig;
	
	@Bean
    public  JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setTestOnBorrow(testOnBorrow);
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		jedisPoolConfig.setMaxTotal(maxActive);
        return jedisPoolConfig;
    }
	
	@Bean(name="jedisConnectionFactory")
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();//连接池
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		jedisConnectionFactory.setHostName(host);
		jedisConnectionFactory.setPassword(pass);
		jedisConnectionFactory.setPort(port);
		return jedisConnectionFactory;                     
	}
	
}
