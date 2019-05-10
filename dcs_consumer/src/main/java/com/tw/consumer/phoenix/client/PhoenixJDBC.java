package com.tw.consumer.phoenix.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.tw.consumer.config.Config;
import com.tw.consumer.core.AutoShutdown;
/**
 * 
 * @author xiesc
 * @TODO phoenix jdbc连接
 * @time 2019年1月11日
 * @version 1.0
 */
public abstract class PhoenixJDBC implements AutoShutdown {
	
	static{
		try {
			Class.forName(Config.getInstance().getPhoenixDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected final List<Map<String,Object>> list;
	/**
	 * list的size()方法并不是线程安全的，加了一个计数器
	 */
	protected final AtomicInteger counter;
	protected Connection conn;
	private final static int batchNumber = Config.getInstance().getPhoenixBatchNumber();
	
	protected PhoenixJDBC(){
		list = Collections.synchronizedList(new ArrayList<>());
		counter = new AtomicInteger();
		createConnection();
	}
	
	private void createConnection(){
        try {
			conn = DriverManager.getConnection(Config.getInstance().getPhoenixUrl());
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Connection getConnection() throws SQLException{
		if(conn == null || conn.isClosed()){
			createConnection();
		}
		return conn;
	}
	
	protected void close() throws SQLException{
		if(conn != null && !conn.isClosed()){
			conn.close();
		}
	}
	
	public void flush(){
		submit();
	}
	
	public void add(Map<String,Object> data){
		list.add(data);
		if(counter.incrementAndGet() % batchNumber == 0){
			submit();
		}
	}
	//phoenix的连接是线程不安全的，list的iterator也是线程不安全的，必须手动加锁.
	protected synchronized void submit(){
		PreparedStatement ps = null;
		try {
			synchronized(list){
				if(list.size() > 0){
					ps = getConnection().prepareStatement(getSqlString());
					for(Map<String,Object> data : list){
						prepareData(ps,data);
						ps.addBatch();
					}
					ps.executeBatch();
					ps.clearBatch();
					getConnection().commit();
					list.clear();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(null != ps){
					ps.close();
				}
				close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		flush();
		try {
			close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract String getSqlString();
	
	protected abstract void prepareData(PreparedStatement ps,Map<String,Object> data);

}
