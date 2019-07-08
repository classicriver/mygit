package com.tw.kylinhelper.mapper;

import java.util.List;

import com.tw.kylinhelper.condition.BaseCondition;

/**
 * 
 * @author xiesc
 * @TODO mapper基础接口
 * @time 2018年8月2日
 * @version 1.0
 */
public interface BaseMapper<T> {
	
	public List<T> select(BaseCondition where);
	
}
