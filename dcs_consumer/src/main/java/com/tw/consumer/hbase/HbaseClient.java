package com.tw.consumer.hbase;

import java.io.IOException;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.tw.consumer.utils.RowKeyHelper;

public class HbaseClient {
	
	private final static Configuration conf = HBaseConfiguration.create();
	private static Connection conn ;
	private HTable table;

	static{
		conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "hbase");
        //conf.set("zookeeper.znode.parent","/hbase-unsecure");
        try {
			conn = ConnectionFactory.createConnection(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public HbaseClient(){
		try {
			table = (HTable) conn.getTable(TableName.valueOf("t1"));
			//table.setAutoFlush(false,true);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void save(){
		Random r = new Random();
		Put put = new Put(new RowKeyHelper().getRowKey());
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("aa"), Bytes.toBytes(String.valueOf(r.nextInt(99))));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("bb"), Bytes.toBytes(String.valueOf(r.nextInt(99))));
		put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("cc"), Bytes.toBytes(String.valueOf(r.nextInt(99))));
		try {
			table.put(put);
			Thread.sleep(1);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutdown(){
		try {
			table.close();
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
