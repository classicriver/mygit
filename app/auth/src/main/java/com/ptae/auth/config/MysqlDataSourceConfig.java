package com.ptae.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 * @Description: TODO(mysql配置类)
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
@Configuration
public class MysqlDataSourceConfig {
	
	@Value("${jdbc.driver}")
	String driverClass;
	@Value("${jdbc.url}")
	String url;
	@Value("${jdbc.username}")
	String userName;
	@Value("${jdbc.password}")
	String passWord;
	//连接池配置
	@Value("${druid.initialSize}")
	int initialSize;
	@Value("${druid.maxActive}")
	int maxActive;
	@Value("${druid.minIdle}")
	int minIdle;
	@Value("${druid.maxWait}")
	long maxWait;
	
	@Bean(name="mysqlDataSource",destroyMethod = "close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setMinIdle(minIdle);
		dataSource.setMaxWait(maxWait);
		return dataSource;
	}
}

