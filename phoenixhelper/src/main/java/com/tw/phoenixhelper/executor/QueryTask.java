package com.tw.phoenixhelper.executor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import com.tw.phoenixhelper.connection.PhoenixJdbcConnection;
import com.tw.phoenixhelper.utils.Constants;

public class QueryTask implements Callable<Map<String,List<Map<String, Object>>>> {

	private final String esn;
	private final String[] columns;
	private final String sql;
	private final CountDownLatch latch;

	public QueryTask(String esn, long startTime, long endTime,CountDownLatch latch,String... columns) {
		this.esn = esn;
		this.columns = columns;
		this.latch = latch;
		this.sql = "select "+columns2String()+" from " + getTableNameByEsn()
				+ " where \"esn\" = '" + esn + "' and \"timestamps\" >= "
				+ startTime + " and \"timestamps\" <= " + endTime;
	}

	@Override
	public Map<String,List<Map<String, Object>>> call() throws Exception {
		// TODO Auto-generated method stub
		PhoenixJdbcConnection conn = new PhoenixJdbcConnection();
		Map<String,List<Map<String, Object>>> result = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();
		Statement stat;
		try {
			stat = conn.getConnection().createStatement();
			ResultSet res = stat.executeQuery(sql);
			while(res.next()){
				list.add(getResultMap(res));
			}
			res.close();
			stat.close();
			conn.close();
			latch.countDown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.put(esn, list);
		return result;
	}

	private String getTableNameByEsn() {
		if (esn.matches("(.*)-N1-(.*)")) {
			// 组串式逆变器
			return Constants.CASCADE;
		} else if (esn.matches("(.*)-H1-(.*)")) {
			// 直流汇流箱
			return Constants.COMBINER;
		} else if (esn.matches("(.*)-N3-(.*)")) {
			// 集中式逆变器
			return Constants.CENTRALIZED;
		} else if (esn.matches("(.*)-HJ-(.*)")) {
			// 环境仪
			return Constants.ENVIR;
		}
		return "";
	}
	
	private String columns2String(){
		if(null != columns && columns.length > 0){
			StringBuffer sb = new StringBuffer();
			for(String column : columns){
				sb.append("\""+column +"\",");
			}
			return sb.substring(0, sb.length()-1);
		}
		return "*";
	}
	
	private Map<String, Object> getResultMap(ResultSet rs) throws SQLException {
		Map<String, Object> hm = new HashMap<>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			int columnType = rsmd.getColumnType(i);
			Object value = 0;
			switch (columnType) {
			case -5:
				value = rs.getLong(i);
				break;
			case 3:
				value = rs.getBigDecimal(i) == null ? 0 : rs.getBigDecimal(i);
				break;
			default:
				value = rs.getString(i);
				break;
			}
			hm.put(key, value);
		}
		return hm;
	}

}
