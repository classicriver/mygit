package com.tw.consumer.redis;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.tw.consumer.model.RedisModel;

/**
 * 
 * @author xiesc
 * @TODO	解析器与redis的适配器
 * @time 2018年9月10日
 * @version 1.0
 */
public class RedisAdapter {
	
	protected final static LinkedBlockingQueue<RedisModel> queue = new LinkedBlockingQueue<RedisModel>(Integer.MAX_VALUE);
	
	public void save(String key,Map<String,Object> value){
		RedisModel e = new RedisModel();
		e.setKey(key);
		e.setMap(value);
		try {
			queue.put(e);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
