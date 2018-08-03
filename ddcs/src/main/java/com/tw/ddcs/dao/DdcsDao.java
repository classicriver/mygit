package com.tw.ddcs.dao;

public interface DdcsDao<T> {
	
	public int insert(T t);
	
	public int batchInsert(T t);
	
	public void submit();
	
}
