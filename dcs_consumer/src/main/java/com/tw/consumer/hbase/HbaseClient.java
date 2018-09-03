package com.tw.consumer.hbase;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.tw.consumer.config.Config;
import com.tw.consumer.config.HbaseConfig;
import com.tw.consumer.utils.RowKeyHelper;

public class HbaseClient extends HbaseConfig implements HbaseClientInterface {

	private HTable table;

	public HbaseClient() {
		try {
			table = (HTable) conn.getTable(TableName.valueOf(Config
					.getInstance().getHbaseTableName()));
			table.setAutoFlush(false, true);
			table.setWriteBufferSize(2 * 1024 * 1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(byte[] data) {
		Put put = new Put(new RowKeyHelper().getRowKey());
		put.addColumn(Bytes.toBytes(Config.getInstance().getHbaseFamily()),
				Bytes.toBytes(Config.getInstance().getHbaseQualifier()), data);
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
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
