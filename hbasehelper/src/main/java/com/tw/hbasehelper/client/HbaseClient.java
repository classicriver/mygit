package com.tw.hbasehelper.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

public class HbaseClient {

	private HTable table;

	public HbaseClient(Connection conn, String tableName) {
		try {
			table = (HTable) conn.getTable(TableName.valueOf(tableName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		// TODO Auto-generated method stub
		table.close();
	}

	public List<Map<String, Object>> query(Scan scan) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
			for (Result res : scanner) {
				result2Map( result,res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			scanner.close();
		}

		return result;
	}
	
	public List<Map<String, Object>> batchGet(List<Get> getList){
		List<Map<String, Object>> rt = new ArrayList<Map<String, Object>>();
		try {
			Result[] results = table.get(getList);
			for(Result res : results){
				result2Map( rt,res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rt;
	}
	
	private List<Map<String, Object>> result2Map(List<Map<String, Object>> rt,Result res){
		NavigableMap<byte[], NavigableMap<byte[], byte[]>> noVersionMap = res
				.getNoVersionMap();
		Iterator<byte[]> it = noVersionMap.keySet().iterator();
		while (it.hasNext()) {
			byte[] family = it.next();
			rt.add(byteMap2StringMap(noVersionMap.get(family)));
		}
		return rt;
		
	}

	private Map<String, Object> byteMap2StringMap(Map<byte[], byte[]> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		Iterator<byte[]> it = map.keySet().iterator();
		while (it.hasNext()) {
			byte[] key = it.next();
			try {
				newMap.put(new String(key, "UTF-8"), new String(map.get(key),
						"Utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newMap;
	}

	public void save(List<Put> data) {
		// TODO Auto-generated method stub
		try {
			table.put(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public void delete(List<Delete> deletes) {
		// TODO Auto-generated method stub
		try {
			table.delete(deletes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
