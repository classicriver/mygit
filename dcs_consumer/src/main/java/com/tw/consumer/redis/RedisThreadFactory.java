package com.tw.consumer.redis;

import java.util.concurrent.ExecutorService;

import com.tw.consumer.config.Config;
import com.tw.consumer.core.AutoShutdown;
import com.tw.consumer.core.ThreadFactoryBean;

public class RedisThreadFactory implements AutoShutdown{
	
	private ExecutorService redisPool = ThreadFactoryBean.getFixedThreadPool("redisThread:", Config.getInstance().getRedisThreads());
	
	public void start(){
		for(int i=0;i < Config.getInstance().getRedisThreads();i++){
			RedisBackroundSubmitThread redisThread = new RedisBackroundSubmitThread();
			redisPool.execute(redisThread);
		}
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		redisPool.shutdown();
	}
}
