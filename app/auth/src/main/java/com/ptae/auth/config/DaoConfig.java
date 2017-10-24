package com.ptae.auth.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

<<<<<<< HEAD
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.github.pagehelper.PageInterceptor;
=======
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageInterceptor;
>>>>>>> branch 'master' of https://github.com/classicriver/mygit

/**
 * 
 * @Description: TODO(dao配置类)
 * @author  xiesc
 * @date 2017年10月12日 
 * @version V1.0  
 */
//启用注解事务管理，使用CGLib代理
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
@Import({MysqlDataSourceConfig.class})
@MapperScan(basePackages = {"com.ptae.**.mapper"})
public class DaoConfig {
	
	@Autowired
	DataSource mysqlDataSource;
	
	@Bean(name = "txManager")
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager(mysqlDataSource);
		return txManager;
	}
	
	@Bean(name = "sessionFactory")
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setTypeAliasesPackage("com.ptae.auth.model");//别名设置
		
		Properties mybatis = new Properties();
		mybatis.put("logImpl", "STDOUT_LOGGING");//设置日志显示级别,上线注释掉
		
		sessionFactory.setConfigurationProperties(mybatis);
		//load xml
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			
			sessionFactory.setMapperLocations(resolver
			        .getResources("com/ptae/mapper/*.xml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sessionFactory.setDataSource(mysqlDataSource);
		//set page interceptor params
		Properties p = new Properties();
		p.put("helperDialect", "mysql");
		p.put("rowBoundsWithCount", "true");
		//加载mybatis插件,加载顺序不可改变
		//1.分页拦截器
		//2.resultMap拦截器
		//3.resultset拦截器
		//Interceptor[] interceptors = new Interceptor[3];
		//interceptors[0] = new PageInterceptor();
		//interceptors[0].setProperties(p);
		//interceptors[1] = new ResultMapInterceptor();
		//interceptors[2] = new ResultSetInterceptor();
		//sessionFactory.setPlugins(interceptors);
		return sessionFactory;
	}
	
}


