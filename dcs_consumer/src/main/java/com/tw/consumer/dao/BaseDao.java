package com.tw.consumer.dao;

import java.util.List;

public interface BaseDao<T> {
	
	public int insert(T t);
	
	public int batchInsert(List<T> t);
	
	public void submit();
	
}
