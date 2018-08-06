package com.tw.consumer.mapper;

import java.util.List;

/**
 * 
 * @author xiesc
 * @TODO mapper基础接口
 * @time 2018年8月2日
 * @version 1.0
 */
public interface BaseMapper<T> {
	
	public int insert(T t);
	
	public int batchInsert(List<T> list);
	
}
