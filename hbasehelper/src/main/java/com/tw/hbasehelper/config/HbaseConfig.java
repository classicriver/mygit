package com.tw.hbasehelper.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
/**
 * 
 * @author xiesc
 * @TODO hbase配置类
 * @time 2018年8月16日
 * @version 1.0
 */
public class HbaseConfig{

	private Connection conn ;
	
	public HbaseConfig() {
		createConnection();
	}
	
	private void createConnection(){
		Configuration conf = HBaseConfiguration.create();
		try(InputStream stream = HbaseConfig.class.getClassLoader().getResourceAsStream("hbase-site.xml");) {
			conf.addResource(stream);
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		if(conn.isClosed()){
			createConnection(); 
		}
		return conn;
	}
	
	public void closeConnection(){
		if(!conn.isClosed()){
			try {
				conn.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
