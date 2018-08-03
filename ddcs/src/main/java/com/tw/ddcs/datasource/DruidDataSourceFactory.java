package com.tw.ddcs.datasource;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;
/**
 * 
 * @author xiesc
 * @TODO 阿里连接池
 * @time 2018年8月2日
 * @version 1.0
 */
public class DruidDataSourceFactory implements DataSourceFactory {

	private Properties props;

	@Override
	public void setProperties(Properties props) {
		this.props = props;
	}

	@Override
	public DataSource getDataSource() {
		DruidDataSource dds = new DruidDataSource();
		try {
			dds.setDriverClassName(props.getProperty("driver"));
			dds.setUrl(props.getProperty("url"));
			dds.setUsername(props.getProperty("username"));
			dds.setPassword(props.getProperty("password"));
			dds.setMaxActive(Integer.valueOf(props.getProperty("maxActive")));
			dds.setMinIdle(Integer.valueOf(props.getProperty("minIdle")));
			dds.setMaxWait(Integer.valueOf(props.getProperty("maxWait")));
			dds.setFilters(props.getProperty("filters"));
			dds.setInitialSize(Integer.valueOf(props.getProperty("initialSize")));
			dds.setTimeBetweenEvictionRunsMillis(Long.parseLong(props.getProperty("timeBetweenEvictionRunsMillis")));
			dds.setMinEvictableIdleTimeMillis(Long.parseLong(props.getProperty("minEvictableIdleTimeMillis")));
			dds.setValidationQuery(props.getProperty("validationQuery"));
			dds.setTestWhileIdle(Boolean.valueOf(props.getProperty("testWhileIdle")));
			dds.setTestOnBorrow(Boolean.valueOf(props.getProperty("testOnBorrow")));
			dds.setTestOnReturn(Boolean.valueOf(props.getProperty("testOnReturn")));
			dds.setMaxOpenPreparedStatements(Integer.valueOf(props.getProperty("maxOpenPreparedStatements")));
			dds.setRemoveAbandoned(Boolean.valueOf(props.getProperty("removeAbandoned")));
			dds.setRemoveAbandonedTimeout(Integer.valueOf(props.getProperty("removeAbandonedTimeout")));
			dds.setLogAbandoned(Boolean.valueOf(props.getProperty("logAbandoned")));
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dds.configFromPropety(props);
		// 其他配置可以根据MyBatis主配置文件进行配置
		try {
			dds.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dds;
	}
}
