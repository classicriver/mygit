package com.justbon.monitor.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.log.LogFactory;

/**
 * 
 * @author xiesc
 * @date 2020年4月28日
 * @version 1.0.0
 * @Description: TODO sqlite数据源
 */
public class SqliteDataSource {

	private static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);
	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static Connection connection;
	private static final String tableName = PropertyKeys.MONITOR_SQLITE_RECORDSTABLE;

	static {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(
					"jdbc:sqlite:"+PropertiesConfig.getInstance().getStr(PropertyKeys.MONITOR_SQLITE_RECORDSTABLE)+"sqlite.db");
			List<Map<String, Object>> rs = executeQuerySQL(
					"select count(1) as cut from SQLITE_MASTER where type = 'table' and name = '" + tableName + "'");
			LogFactory.info("sqlite init.....");
			if (Integer.parseInt(rs.get(0).get("cut").toString()) == 0) {
				StringBuilder sb = new StringBuilder();
				sb.append("CREATE TABLE " + tableName + " (id BIGINT (32)  PRIMARY KEY NOT NULL,");
				sb.append("startTime DATETIME NOT NULL,endTime DATETIME NOT NULL,");
				sb.append("description VARCHAR (64) NOT NULL)");
				executeUpdateSQL(sb.toString());
				sb.delete(0, sb.length());
				sb.append("CREATE INDEX " + tableName + "_index_starttime ON " + tableName + " (startTime)");
				executeUpdateSQL(sb.toString());
				LogFactory.info("greate sqlite table.....");
			} 
		} catch (Exception e) {
			LogFactory.error("sqlite异常：" + e.getMessage(), e);
		}
	}

	private static Statement getStatement() throws SQLException {
		if (null == connection || connection.isClosed()) {
			connection = DriverManager.getConnection("jdbc:sqlite:" + 
					PropertiesConfig.getInstance().getStr(PropertyKeys.SQLITE_PATH) + "/sqlite.db");
		}
		return connection.createStatement();
	}

	public static List<Map<String, Object>> executeQuerySQL(String sql) {
		List<Map<String, Object>> rs = null;
		try (Statement statement = getStatement()) {
			rs = result2Map(statement.executeQuery(sql));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}

	private static List<Map<String, Object>> result2Map(ResultSet rs) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			while(rs.next()) {
				Map<String, Object> hm = new HashMap<>();
				for (int i = 1; i <= count; i++) {
					String key = rsmd.getColumnName(i);
					String value = rs.getString(i);
					hm.put(key, value);
				}
				results.add(hm);
			}
			return results;
		} finally {
			rs.close();
		}
	}

	public static void executeUpdateSQL(String sql) {
		try (Statement statement = getStatement()) {
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			LogFactory.error("数据库关闭失败：" + e.getMessage(), e);
		}
	}

	/**
	 * 时间戳转yyyy-MM-dd HH:mm:ss
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String getDateTimeByTimestamp(long timestamp) {
		return Instant.ofEpochMilli(timestamp).atOffset(ZONE_OFFSET).format(DATE_TIME_FORMATTER);
	}

}
