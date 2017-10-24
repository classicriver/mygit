package com.ptae.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

<<<<<<< HEAD
//import com.danga.MemCached.MemCachedClient;
//import com.danga.MemCached.SockIOPool;

/**
 * 
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
//@Configuration
//@PropertySource({"classpath:redis.properties"})
//@ConfigurationProperties(prefix = "memcache",locations = "classpath:application.properties")
public class MemcacheDataSource {
	
	@Value("${memcache.servers}")
	String server;
	@Value("${memcache.weights}")
	int weight;
	@Value("${memcache.initConn}")
	int initConn;
	@Value("${memcache.minConn}")
	int minConn;
	@Value("${memcache.maxConn}")
	int maxConn;
	@Value("${memcache.maintSleep}")
	int maintSleep;
	@Value("${memcache.nagle}")
	boolean nagle;
	@Value("${memcache.socketTO}")
	int socketTO;
/*	
    @Bean
    public SockIOPool sockIOPool(){
        //获取连接池的实例
        SockIOPool pool = SockIOPool.getInstance();
        //服务器列表及其权重
        String[] servers = new String[1];
        servers[0] = server;
        Integer[] weights = new Integer[1];
        weights[0] = weight;
        //设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        //设置初始连接数、最小连接数、最大连接数、最大处理时间
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        //设置连接池守护线程的睡眠时间
        pool.setMaintSleep(maintSleep);
        //设置TCP参数，连接超时
        pool.setNagle(nagle);
        pool.setSocketConnectTO(socketTO);
        //初始化并启动连接池
        pool.initialize();
        return pool;
    }

    @Bean
    @ConditionalOnBean(SockIOPool.class)
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }*/
=======
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * 
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
//@Configuration
//@PropertySource({"classpath:redis.properties"})
//@ConfigurationProperties(prefix = "memcache",locations = "classpath:application.properties")
public class MemcacheDataSource {
	
	@Value("${memcache.servers}")
	String server;
	@Value("${memcache.weights}")
	int weight;
	@Value("${memcache.initConn}")
	int initConn;
	@Value("${memcache.minConn}")
	int minConn;
	@Value("${memcache.maxConn}")
	int maxConn;
	@Value("${memcache.maintSleep}")
	int maintSleep;
	@Value("${memcache.nagle}")
	boolean nagle;
	@Value("${memcache.socketTO}")
	int socketTO;
	
    @Bean
    public SockIOPool sockIOPool(){
        //获取连接池的实例
        SockIOPool pool = SockIOPool.getInstance();
        //服务器列表及其权重
        String[] servers = new String[1];
        servers[0] = server;
        Integer[] weights = new Integer[1];
        weights[0] = weight;
        //设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        //设置初始连接数、最小连接数、最大连接数、最大处理时间
        pool.setInitConn(initConn);
        pool.setMinConn(minConn);
        pool.setMaxConn(maxConn);
        //设置连接池守护线程的睡眠时间
        pool.setMaintSleep(maintSleep);
        //设置TCP参数，连接超时
        pool.setNagle(nagle);
        pool.setSocketConnectTO(socketTO);
        //初始化并启动连接池
        pool.initialize();
        return pool;
    }

    @Bean
    @ConditionalOnBean(SockIOPool.class)
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }
>>>>>>> branch 'master' of https://github.com/classicriver/mygit

	
	
}
