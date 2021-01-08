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

public class SourceJDBC {

	private final DruidDataSourceFactory dataSource = new DruidDataSourceFactory();
	/**
	 * 
	 * @param sql
	 * @return  
	 */
	public List<Map<String, Object>> getData(String sql) {
		Statement statement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> list = new ArrayList<>();
		Connection connection = dataSource.getConnection();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				list.add(getResultMap(resultSet));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String,Object> getCapacityAsMap(String sql){
		Map<String, Object> hm = new HashMap<>();
		Statement statement = null;
		ResultSet resultSet = null;
		Connection connection = dataSource.getConnection();
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				hm.put(resultSet.getString("id"), resultSet.getBigDecimal("pvTotal"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return hm;
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

	public void close() {
		dataSource.closeDataSource();
	}
}
