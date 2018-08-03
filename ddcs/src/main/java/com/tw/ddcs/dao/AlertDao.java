package com.tw.ddcs.dao;

import org.apache.ibatis.session.SqlSession;

import com.tw.ddcs.model.Alert;
import com.tw.ddcs.mybatis.mapper.AlertMapper;
import com.tw.ddcs.mybatis.mapper.BaseMapper;

/**
 * 
 * @author xiesc
 * @TODO 遥信mapper
 * @time 2018年8月2日
 * @version 1.0
 */
public class AlertDao extends DefaultDao<Alert>{

	@Override
	protected BaseMapper<Alert> getMapper(SqlSession session) {
		// TODO Auto-generated method stub
		return session.getMapper(AlertMapper.class);
	}

}
