package com.tw.connect.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tw.connect.cache.LRUCache;

public class SourceJDBC{

	private final DruidDataSourceFactory dataSource = new DruidDataSourceFactory();
	private final String sql = "select node.path path,inverter.esn esn from sc_device_inverter inverter "
			+ "LEFT JOIN sc_device_node node on inverter.path like concat(node.path,'%') where node.type='subarray'";

	public List<Map<String, Object>> getData(){
		Statement statement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> list = new ArrayList<>();
		Connection connection = dataSource.getConnection();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				list.add(getResultMap(resultSet));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public void initCache(LRUCache cache){
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = dataSource.getConnection();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
				String key = resultSet.getString("esn");
				String value = resultSet.getString("path");
				cache.putCache(key, value);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				connection.close();
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private Map<String, Object> getResultMap(ResultSet rs) throws SQLException {
		Map<String, Object> hm = new HashMap<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			hm.put(key, rs.getString(i));
		}
		return hm;
	}
	
	public void close(){
		dataSource.closeDataSource();
	}
	public static void main(String[] args) {
		List<Map<String, Object>> data = new SourceJDBC().getData();
		System.out.println(data.size());
	}
}
