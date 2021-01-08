package com.tw.kylinhelper.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.tw.kylinhelper.condition.BaseCondition;
import com.tw.kylinhelper.datasource.MybatisProvider;
import com.tw.kylinhelper.mapper.BaseMapper;

/**
 * 
 * @author xiesc
 * @TODO dao基类
 * @time 2019年4月2日
 * @version 1.0
 */
public abstract class AbstractDao<T> implements BaseDao<T> {

	private final SqlSession session;

	protected AbstractDao() {
		session = new MybatisProvider().getSqlSession();
	}

	@Override
	public List<T> select(BaseCondition where) {
		// TODO Auto-generated method stub
		return getMapper(session).select(where);
	}

	protected abstract BaseMapper<T> getMapper(SqlSession session);

}
