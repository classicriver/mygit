package com.tw.consumer.redis;

import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.model.RedisModel;
import com.tw.consumer.sender.RedisSender;

public class RedisTask implements Runnable{

	private final RedisSender redisSender = SingleBeanFactory.getBean(RedisSender.class);
	private final SentinelRedisClient redisClient;
	
	public RedisTask(SentinelRedisClient redisClient){
		this.redisClient = redisClient;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			RedisModel model = redisSender.getMessage();
			switch(model.getOperation()){
			case setex:
				redisClient.setex(model.getKey(), 600 , model.getValue());
				break;
			case setnx :
				redisClient.setnx(model.getKey(), model.getValue());
				break;
			case sadd :
				redisClient.sadd(model.getKey(), model.getValue());
				break;
			default:
				redisClient.set(model.getKey() , model.getValue());
				break;
			}
		}		
	}
}
