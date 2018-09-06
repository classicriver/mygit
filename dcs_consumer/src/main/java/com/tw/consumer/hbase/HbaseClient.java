package com.tw.consumer.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import com.tw.consumer.config.Config;
import com.tw.consumer.config.HbaseConfig;

public class HbaseClient extends HbaseConfig implements HbaseClientInterface {

	private HTable table;

	public HbaseClient() {
		try {
			table = (HTable) conn.getTable(TableName.valueOf(Config
					.getInstance().getHbaseTableName()));
			//关闭自动提交
			table.setAutoFlush(false, true);
			//缓冲区1MB，满了提交
			table.setWriteBufferSize(1 * 1024 * 1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Put put) {
		try {
			table.put(put);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void save(List<Put> data) {
		// TODO Auto-generated method stub
		try {
			table.put(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void flush(){
		try {
			table.flushCommits();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeConnection(){
		try {
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
