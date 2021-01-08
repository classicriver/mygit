package com.tw.consumer.sender;

import java.util.concurrent.LinkedBlockingQueue;

import com.tw.consumer.config.Config;

public abstract class AbstractSender<T> {
	
	protected final LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>(Config.getInstance().getMaxRingBuffer() * 2);
	
	public  void putMessage(T msg){
		try {
			queue.put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  T getMessage(){
		T model = null;
		try {
			model = queue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}
}
