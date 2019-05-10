package com.tw.consumer.device.dao;

import com.tw.consumer.core.AutoShutdown;

public interface Dao<T> extends AutoShutdown{

	public void add(T data);
	
}
