package com.tw.ddcs.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.ibatis.session.SqlSession;

import com.tw.ddcs.config.DdcsConfig;
import com.tw.ddcs.datasource.MybatisProvider;
import com.tw.ddcs.mybatis.mapper.BaseMapper;
/**
 * 
 * @author xiesc
 * @TODO 带缓存的抽象dao类（thread safe）
 * @time 2018年8月2日
 * @version 1.0
 */
public abstract class DefaultDao<T> implements BaseDao<T> {
	
	private final SqlSession session;
	private final List<T> list;
	/**
	 * list的size()方法并不是线程安全的，加了一个计数器
	 */
	private final AtomicInteger counter;
	
	protected DefaultDao(){
		session = new MybatisProvider().getSqlSession();
		list = Collections.synchronizedList(new ArrayList<T>());
		counter = new AtomicInteger();
	}
	
	@Override
	public int insert(T t) {
		// TODO Auto-generated method stub
		int state = -1;
		BaseMapper<T> mapper = getMapper(session);
		mapper.insert(t);
		state = 1;
		return state;
	}
	
	@Override
	public int batchInsert(T t) {
		// TODO Auto-generated method stub
		int state = -1;
		list.add(t);
		int size = counter.getAndIncrement();
		if(size % DdcsConfig.getInstance().getBatchNumber() == 0){
			submit();
		}
		state = 1;
		return state;
	}
	
	@Override
	public void submit(){
		synchronized(list){
			if(list.size() > 0){
				BaseMapper<T> mapper = getMapper(session);
				mapper.batchInsert(list);
				list.clear();
			}
		}
	}

	protected abstract BaseMapper<T> getMapper(SqlSession session);
	
}
