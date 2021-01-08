package com.justbon.lps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;
import com.justbon.lps.config.ServiceConfig;

/** 
 * @author xiesc
 * @TODO 阿里连接池
 * @time 2018年8月2日
 * @version 1.0
 */
public class DruidDataSourceFactory{

	private final DruidDataSource dds = new DruidDataSource();
	private static final Properties pro = ServiceConfig.getInstance().getProperties();
	
	public DruidDataSourceFactory() {
		dds.configFromPropety(pro);
		try {
			dds.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取jdbc连接
	 * @return
	 */
	public Connection getConnection(){
		Connection conn = null;
		try {
			conn = dds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 关闭连接池
	 */
	public void closeDataSource(){
		dds.close();
	}
}
