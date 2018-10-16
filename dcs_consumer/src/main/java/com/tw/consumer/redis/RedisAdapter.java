package com.tw.consumer.redis;

import java.util.concurrent.LinkedBlockingQueue;

import com.tw.consumer.model.RedisModel;

abstract class RedisService{
	protected final static LinkedBlockingQueue<RedisModel> queue = new LinkedBlockingQueue<RedisModel>(Integer.MAX_VALUE);
}

/**
 * @author xiesc
 * @TODO	redis的适配器
 * @time 2018年9月10日
 * @version 1.0
 */
public class RedisAdapter extends RedisService{
	
	public void save(byte[] key,byte[] value){
		RedisModel e = new RedisModel();
		e.setKey(key);
		e.setValue(value);
		try {
			queue.put(e);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
