package com.tw.hbasehelper.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.tw.hbasehelper.utils.FamilyType;

public interface Query {

	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public abstract Map<String, Object> query(String sn, String time)
			throws Exception;

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
	public abstract Map<String, Object> query(String sn, String time,
			FamilyType family) throws Exception;

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
	public abstract Map<String, Object> query(String sn, String time,
			FamilyType family, String... column) throws Exception;

	/**
	 * 
	 * @param sn
	 * @param time
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            遥测or遥信
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public abstract <T> T query(String sn, String time, FamilyType family,
			Class<T> clazz) throws Exception;

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @return 嵌套 map 格式{yx : {ipv1 : 1},yc:{e1 : 1}}
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public abstract List<Map<String, Map<String, Object>>> rangeQuery(
			String sn, String startTime, String endTime)
			throws Exception;

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
	public abstract List<Map<String, Object>> rangeQuery(String sn,
			String startTime, String endTime, FamilyType family)
			throws Exception;

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
	public abstract List<Map<String, Object>> rangeQuery(String sn,
			String startTime, String endTime, FamilyType family,
			String... column) throws Exception;

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
	public abstract <T> List<T> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family, Class<T> clazz) throws Exception;

}