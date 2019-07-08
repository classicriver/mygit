package com.tw.kylinhelper.dao;

import java.util.List;

import com.tw.kylinhelper.condition.BaseCondition;

public interface BaseDao<T> {

	public List<T> select(BaseCondition where);
}
