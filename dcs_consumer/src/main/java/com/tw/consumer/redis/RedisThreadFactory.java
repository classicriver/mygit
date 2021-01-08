package com.tw.consumer.redis;

import java.util.concurrent.ExecutorService;

import com.tw.consumer.config.Config;
import com.tw.consumer.core.AutoShutdown;
import com.tw.consumer.core.ThreadFactoryBean;

public class RedisThreadFactory implements AutoShutdown{
	
	private final int redisThreadCount = Config.getInstance().getRedisThreads();
	private final ExecutorService redisPool = ThreadFactoryBean.getFixedThreadPool("redisThread",redisThreadCount);
	private final SentinelRedisClient redisClient = new SentinelRedisClient();
	
	public void start(){
		for(int i=0;i < redisThreadCount;i++){
			RedisTask redisThread = new RedisTask(redisClient);
			redisPool.execute(redisThread);
		}
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		redisClient.close();
		redisPool.shutdown();
	}
}
