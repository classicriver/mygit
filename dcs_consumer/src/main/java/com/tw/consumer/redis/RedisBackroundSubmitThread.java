package com.tw.consumer.redis;

import com.tw.consumer.model.RedisModel;

public class RedisBackroundSubmitThread extends RedisAdapter implements Runnable{

	private final SingleRedisClient redisClient = new SingleRedisClient();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				RedisModel take = queue.take();
				redisClient.set(take.getKey(), take.getMap());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
