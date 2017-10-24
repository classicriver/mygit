package com.ptae.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/*import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheClientFactory;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;*/

/**
 * 
 * @author xiesc
 * <br> memcache配置类
 */
//@Configuration
//@PropertySource({"classpath:redis.properties"})
//@ConfigurationProperties(prefix = "memcache",locations = "classpath:application.properties")
public class MemcacheDataSource2 {
	
	@Value("${memcache.servers}")
	String servers;
	@Value("${memcache.weights}")
	int weights;
	@Value("${memcache.initConn}")
	String initConn;
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
	@Bean("defaultMemcachedClient")
	public CacheFactory getDefaultMemcachedClient() {
		CacheFactory defaultMemcachedClient = new CacheFactory();
		defaultMemcachedClient.setCacheClientFactory(getCacheClientFactory());
		defaultMemcachedClient.setAddressProvider(getAddressProvider());
		defaultMemcachedClient.setConfiguration(getConfiguration());
		return defaultMemcachedClient;
	}
	
	@Bean("cacheClientFactory")
	public CacheClientFactory getCacheClientFactory() {
		return new MemcacheClientFactoryImpl();
	}
	
	@Bean("addressProvider")
	public DefaultAddressProvider getAddressProvider() {
		DefaultAddressProvider addressProvider = new DefaultAddressProvider();
		addressProvider.setAddress(servers);
		return addressProvider;
	}
	
	@Bean("configuration")
	public CacheConfiguration getConfiguration() {
		CacheConfiguration configuration =  new CacheConfiguration();
		configuration.setConsistentHashing(true);
		return configuration;
	}*/
	
	
}
