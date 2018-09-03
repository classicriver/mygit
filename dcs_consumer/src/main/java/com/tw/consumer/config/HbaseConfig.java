package com.tw.consumer.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;
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
	
	protected static Connection conn ;
	
	static {
			Configuration conf = new Configuration();
			InputStream stream = HbaseConfig.class.getClassLoader().getResourceAsStream("hbase.properties");
			conf.addResource(stream);
			try {
				conn = ConnectionFactory.createConnection(conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					stream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}

}
