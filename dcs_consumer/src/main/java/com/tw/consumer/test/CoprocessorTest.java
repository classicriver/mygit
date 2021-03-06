package com.tw.consumer.test;
/*package com.tw.consumer.coprocessor.autogenerated;

import java.io.IOException;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.coprocessor.Batch;
import org.apache.hadoop.hbase.ipc.BlockingRpcCallback;

import com.google.protobuf.ServiceException;
import com.tw.coprocessor.autogenerated.Sum.SumRequest;
import com.tw.coprocessor.autogenerated.Sum.SumResponse;
import com.tw.coprocessor.autogenerated.Sum.SumService;

public class CoprocessorTest {

	public void test() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.zookeeper.quorum", "hbase");

		// Use below code for HBase version 1.x.x or above.
		Connection connection = ConnectionFactory.createConnection(conf);
		TableName tableName = TableName.valueOf("t1");
		
		String path = "hdfs://172.20.90.55:9000/coprocessor/coprocessor.jar";
		Admin admin = connection.getAdmin();
		admin.disableTable(tableName);
		HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
		hTableDescriptor.addCoprocessor(
				RowCountProtocol.class.getCanonicalName(), new Path(path),
				Coprocessor.PRIORITY_USER, null);
		hTableDescriptor.removeCoprocessor(SumService.class.getCanonicalName());
		HColumnDescriptor columnFamily1 = new HColumnDescriptor("f1");
		columnFamily1.setMaxVersions(3);
		hTableDescriptor.addFamily(columnFamily1);
		admin.modifyTable(tableName, hTableDescriptor);
		admin.enableTable(tableName);
		Table table = connection.getTable(tableName);
		// Use below code HBase version 0.98.xx or below.
		// HConnection connection = HConnectionManager.createConnection(conf);
		// HTableInterface table = connection.getTable("users");

		final SumRequest request = SumRequest.newBuilder().setFamily("f1")
				.setColumn("aa").build();
		try {
			Map<byte[], Long> results = table.coprocessorService(
					SumService.class, null, null,
					new Batch.Call<SumService, Long>() {
						@Override
						public Long call(SumService aggregate)
								throws IOException {
							BlockingRpcCallback<SumResponse> rpcCallback = new BlockingRpcCallback<SumResponse>();
							aggregate.getSum(null, request, rpcCallback);
							SumResponse response = (SumResponse) rpcCallback
									.get();
							return response.hasSum() ? response.getSum() : 0L;
						}
					});
			for (Long sum : results.values()) {
				System.out.println("Sum = " + sum);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new CoprocessorTest().test();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
*/