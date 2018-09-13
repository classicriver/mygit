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
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import com.tw.hbasehelper.config.HbaseConfig;


public class HbaseClient extends HbaseConfig implements HbaseClientInterface {

	private HTable table;

	public HbaseClient() {
		try {
			table = (HTable) conn.getTable(TableName.valueOf("kktest"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		table.close();
	}
	
	public static void closeConnection(){
		try {
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String,Map<String,Object>>> query(Scan scan) {
		// TODO Auto-generated method stub
		List<Map<String,Map<String,Object>>> result = new ArrayList<Map<String,Map<String,Object>>>();
		ResultScanner scanner = null;
		try {
			scanner = table.getScanner(scan);
			 for (Result res : scanner) {
				 NavigableMap<byte[], NavigableMap<byte[], byte[]>> noVersionMap = res.getNoVersionMap();
				 Iterator<byte[]> it = noVersionMap.keySet().iterator();
				 Map<String,Map<String,Object>> map = new HashMap<String, Map<String,Object>>();
				 while(it.hasNext()){
					 byte[] family = it.next();
					 map.put(new String(family,"UTF-8"), byteMap2StringMap(noVersionMap.get(family)));
				 }
				 result.add(map);
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			scanner.close();
		}
		
		return result;
	}
	
	private Map<String,Object> byteMap2StringMap(Map<byte[],byte[]> map){
		Map<String,Object> newMap = new HashMap<String, Object>();
		Iterator<byte[]> it = map.keySet().iterator();
		while(it.hasNext()){
			byte[] key = it.next();
			try {
				newMap.put(new String(key,"UTF-8"), new String(map.get(key),"Utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newMap;
	}
}
