package com.justbon.lps.jdbc;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justbon.lps.annotation.JdbcColumnMapping;
/**
 * 
 * @author xiesc
 * @date 2020年8月19日
 * @version 1.0.0
 * @Description: TODO
 */
public class SourceJDBC {

	private final DruidDataSourceFactory dataSource = new DruidDataSourceFactory();
	private final static MethodHandles.Lookup lookup = MethodHandles.lookup();

	/**
	 * @Title: getData   
	 * @Description: TODO  
	 * @param: @param sql
	 * @param: @return
	 * @return:List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getData(String sql) {
		List<Map<String, Object>> list = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				list.add(getResultMap(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
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
	/**
	 * @Title: getData   
	 * @Description: TODO  
	 * @param: @param sql
	 * @param: @param klass
	 * @param: @return
	 * @return:List<T>
	 */
	public <T> List<T> getData(String sql,Class<T> klass) {
		List<T> list = new ArrayList<>();
		try (Connection conn = dataSource.getConnection();
				Statement statement = conn.createStatement();
				ResultSet rs = statement.executeQuery(sql);) {
			while (rs.next()) {
				list.add(result2Object(klass,rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T result2Object(Class<T> klass, ResultSet rs) {
		Object obj = null;
		try {
			obj = lookup.findConstructor(klass, MethodType.methodType(void.class)).invokeWithArguments();
			Field[] fields = klass.getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				Class<?> fieldType = f.getType();
				String fieldName = f.getName();
				Object value = null;
				JdbcColumnMapping mapping = f.getDeclaredAnnotation(JdbcColumnMapping.class);
				if(null != mapping){
					fieldName = mapping.value();
				}
				if (fieldType.equals(String.class)) {
					value = rs.getString(fieldName);
				} else if (fieldType.equals(Integer.class)) {
					value = rs.getInt(fieldName);
				} else if (fieldType.equals(Long.class)) {
					value = rs.getLong(fieldName);
				} else if (fieldType.equals(BigDecimal.class)) {
					value = rs.getBigDecimal(fieldName);
				}
				lookup.unreflectSetter(f).invokeWithArguments(obj, value);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return (T) obj;
	}

	public void close() {
		dataSource.closeDataSource();
	}
}
