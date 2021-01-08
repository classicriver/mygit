package com.tw.ddcs.dao;

import org.apache.ibatis.session.SqlSession;

import com.tw.ddcs.model.SolarMan;
import com.tw.ddcs.mybatis.mapper.BaseMapper;
import com.tw.ddcs.mybatis.mapper.SolarManMapper;
/**
 * 
 * @author xiesc
 * @TODO 遥测mapper
 * @time 2018年8月2日
 * @version 1.0
 */
public class SolarManDao extends DefaultDao<SolarMan>{
	
	@Override
	protected BaseMapper<SolarMan> getMapper(SqlSession session) {
		// TODO Auto-generated method stub
		return session.getMapper(SolarManMapper.class);
	}

}
