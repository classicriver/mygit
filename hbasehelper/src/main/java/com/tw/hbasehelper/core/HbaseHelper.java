package com.tw.hbasehelper.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.tw.hbasehelper.utils.FamilyType;

public class HbaseHelper{
	
	private final Query executor = new QueryExecutor();
	
	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time)
			throws Exception {
		// TODO Auto-generated method stub
		return executor.query(sn, time);
	}
	
	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            列簇 遥测or遥信
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time, FamilyType family)
			throws Exception {
		// TODO Auto-generated method stub
		return executor.query(sn, time, family);
	}
	
	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            列簇 遥测or遥信
	 * @param column
	 *            列 （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time, FamilyType family,
			String... column) throws Exception {
		// TODO Auto-generated method stub
		return executor.query(sn, time, family, column);
	}


	/**
	 * @param sn
	 * @param time
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            遥测or遥信
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T query(String sn, String time, FamilyType family, Class<T> clazz)
			throws Exception {
		// TODO Auto-generated method stub
		return executor.query(sn, time, family, clazz);
	}
	
	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @return 嵌套 map 格式{yx : {ipv1 : 1},yc:{e1 : 1}}
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Map<String, Object>>> rangeQuery(String sn,
			String startTime, String endTime) throws Exception {
		// TODO Auto-generated method stub
		return executor.rangeQuery(sn, startTime, endTime);
	}
	
	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family
	 *            遥测or遥信
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family) throws Exception {
		// TODO Auto-generated method stub
		return executor.rangeQuery(sn, startTime, endTime, family);
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family 列簇
	 *            遥测or遥信
	 * @param column 
	 *            列   （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family, String... column)
			throws Exception {
		// TODO Auto-generated method stub
		return executor.rangeQuery(sn, startTime, endTime, family, column);
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family
	 *            遥测or遥信
	 * @param clazz
	 *            需要转换的类对象
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> rangeQuery(String sn, String startTime, String endTime,
			FamilyType family, Class<T> clazz) throws Exception {
		// TODO Auto-generated method stub
		return executor.rangeQuery(sn, startTime, endTime, family, clazz);
	}

}
