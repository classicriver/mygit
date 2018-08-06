package com.tw.consumer.dao;

public interface BaseDao<T> {
	
	public int insert(T t);
	
	public int batchInsert(T t);
	
	public void submit();
	
}
