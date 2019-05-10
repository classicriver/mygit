package com.tw.phoenixhelper.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.tw.phoenixhelper.config.Config;
/**
 * @author xiesc
 * @TODO phoenix jdbc连接
 * @time 2019年1月11日
 * @version 1.0
 */
public class PhoenixJdbcConnection {
	
	static{
		try {
			Class.forName(Config.getPhoenixDriverClass());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Connection conn;

	public PhoenixJdbcConnection(){
		createConnection();
	}
	
	private void createConnection(){
        try {
			conn = DriverManager.getConnection(Config.getPhoenixUrl());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection(){
		try {
			if(conn == null || conn.isClosed()){
				createConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close(){
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
