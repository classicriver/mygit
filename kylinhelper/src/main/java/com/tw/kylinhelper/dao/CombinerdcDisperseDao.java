package com.tw.kylinhelper.dao;

import org.apache.ibatis.session.SqlSession;

import com.tw.kylinhelper.mapper.BaseMapper;
import com.tw.kylinhelper.mapper.CombinerdcDisperseMapper;
import com.tw.kylinhelper.model.CombinerdcDisperse;

/**
 * 
 * @author xiesc
 * @TODO 汇流箱离散率dao
 * @time 2019年4月2日
 * @version 1.0
 */
public class CombinerdcDisperseDao extends AbstractDao<CombinerdcDisperse>{

	@Override
	protected BaseMapper<CombinerdcDisperse> getMapper(SqlSession session) {
		// TODO Auto-generated method stub
		return session.getMapper(CombinerdcDisperseMapper.class);
	}


}
