package com.tw.hbasehelper.core;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.tw.hbasehelper.utils.FamilyType;

public interface HbaseDao {

	/**
	 * @param sn
	 * @param startTime 时间戳
	 * @param endTime
	 * @param family 列簇
	 *            遥测or遥信
	 * @param column 
	 *            列   （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public abstract Map<String,List<Map<String, Object>>> rangeQuery(String tableName,List<String> sns,
			long startTime, long endTime, FamilyType family,
			String... column) throws Exception;


	
	/**
	 * 
	 * @param tableName  hbasetable
	 * @param data    datamap
	 * @param family  列簇
	 * @throws IOException 
	 */
	public abstract void saveList(String tableName, List<Map<String, Object>> data,
			byte[] family) throws IOException;
	
	/**
	 * 
	 * @param tableName 表名
	 * @param keys  主键
	 * @throws IOException
	 */
	public abstract void deleteList(String tableName, List<String> keys) throws IOException;

}