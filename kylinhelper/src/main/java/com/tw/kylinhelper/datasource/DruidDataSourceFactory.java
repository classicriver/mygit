package com.tw.kylinhelper.datasource;

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
		dds.setDriverClassName(props.getProperty("driver"));
		dds.setUrl(props.getProperty("url"));
		dds.setUsername(props.getProperty("username"));
		dds.setPassword(props.getProperty("password"));
		dds.setMaxActive(Integer.valueOf(props.getProperty("maxActive")));
		dds.setMinIdle(Integer.valueOf(props.getProperty("minIdle")));
		dds.setMaxWait(Integer.valueOf(props.getProperty("maxWait")));
		dds.setInitialSize(Integer.valueOf(props.getProperty("initialSize")));
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
