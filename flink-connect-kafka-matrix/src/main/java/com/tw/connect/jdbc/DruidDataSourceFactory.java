package com.tw.connect.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;
import com.tw.connect.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO 阿里连接池
 * @time 2018年8月2日
 * @version 1.0
 */
public class DruidDataSourceFactory extends PropertyResources{

	private final DruidDataSource dds = new DruidDataSource();
	
	public DruidDataSourceFactory() {
		try {
			dds.setDriverClassName(pro.getProperty("jdbc.driverClassName"));
			dds.setUrl(pro.getProperty("jdbc.url"));
			dds.setUsername(pro.getProperty("jdbc.username"));
			dds.setPassword(pro.getProperty("jdbc.password"));
			dds.setMaxActive(Integer.valueOf(pro.getProperty("druid.maxActive")));
			dds.setMinIdle(Integer.valueOf(pro.getProperty("druid.minIdle")));
			dds.setMaxWait(Integer.valueOf(pro.getProperty("druid.maxWait")));
			dds.setFilters(pro.getProperty("druid.filters"));
			dds.setInitialSize(Integer.valueOf(pro.getProperty("druid.initialSize")));
			dds.setTimeBetweenEvictionRunsMillis(Long.parseLong(pro.getProperty("druid.timeBetweenEvictionRunsMillis")));
			dds.setMinEvictableIdleTimeMillis(Long.parseLong(pro.getProperty("druid.minEvictableIdleTimeMillis")));
			dds.setValidationQuery(pro.getProperty("druid.validationQuery"));
			dds.setTestWhileIdle(Boolean.valueOf(pro.getProperty("druid.testWhileIdle")));
			dds.setTestOnBorrow(Boolean.valueOf(pro.getProperty("druid.testOnBorrow")));
			dds.setTestOnReturn(Boolean.valueOf(pro.getProperty("druid.testOnReturn")));
			dds.setMaxOpenPreparedStatements(Integer.valueOf(pro.getProperty("druid.maxOpenPreparedStatements")));
			dds.setRemoveAbandoned(Boolean.valueOf(pro.getProperty("druid.removeAbandoned")));
			dds.setRemoveAbandonedTimeout(Integer.valueOf(pro.getProperty("druid.removeAbandonedTimeout")));
			dds.setLogAbandoned(Boolean.valueOf(pro.getProperty("druid.logAbandoned")));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dds.configFromPropety(pro);
		// 其他配置可以根据MyBatis主配置文件进行配置
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

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "druid.properties";
	}
}
