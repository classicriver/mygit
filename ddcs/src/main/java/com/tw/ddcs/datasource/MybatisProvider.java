package com.tw.ddcs.datasource;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.SqlSessionManager;

/**
 * @Description: TODO(mybatis初始化类) 
 * @author: sc
 * @date: 2018年7月24日
 */
public class MybatisProvider{

    private static final String CONFIG_PATH = "mybatis.xml";
    private SqlSessionManager  session;
    
    {
    	InputStream stream;
		try {
			stream = Resources.getResourceAsStream(CONFIG_PATH);
	        final SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(stream);
	        session = SqlSessionManager.newInstance(factory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * SqlSessionManager的session是线程安全，并且自动开启、自动提交、自动关闭
     * @return
     */
    public SqlSession getSqlSession(){
    	return session;
    } 
}
