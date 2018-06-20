package com.tw.consumer.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.hbase.async.HBaseClient;


public class HbaseClient {
	static Configuration conf = null;
	static Connection conn = null;

	static {
	    try {
	        conf = HBaseConfiguration.create();
	       // conf.set("hbase.zookeeper.property.clientPort", "2181");
	        conf.set("hbase.zookeeper.quorum", "hbase");
	        //conf.set("zookeeper.znode.parent","/hbase-unsecure");
	        conn = ConnectionFactory.createConnection(conf);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void test(){
		try {
			HTable table = (HTable) conn.getTable(TableName.valueOf("t1"));
			/*table.setAutoFlush(false,true);
			ResultScanner scanner = table.getScanner(Bytes.toBytes("f1"), Bytes.toBytes("cc"));
			byte[] value = scanner.next().getValue(Bytes.toBytes("f1"), Bytes.toBytes("cc"));
			System.out.println(new String(value));
			scanner.close();
			for(int i = 0;i<100;i++){
				Put put = new Put(new RowKeyHelper().getRowKey());
				put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("cc"), Bytes.toBytes(String.valueOf(System.currentTimeMillis())));
				table.put(put);
				Thread.sleep(1);
			}
			table.getRegionLocator().getAllRegionLocations().get(0).getRegionInfo().getRegionName();
			Put put = new Put(CommonUtils.getRowKey());
			put.addColumn(Bytes.toBytes("f1"), Bytes.toBytes("cc"), Bytes.toBytes("value222222"));
			table.put(put);
			table.flushCommits();*/
			// 扫描table，获取r2到r4区间中value已a或b结尾的cell
			//Filter filter1 = new RowFilter(CompareFilter.CompareOp.LESS, new RegexStringComparator(".*-9223370508174980781"));
																									   //9223370508174980255
																									   //9223370508175254160
			//Filter filter2 = new RowFilter(CompareFilter.CompareOp.GREATER, new RegexStringComparator(".*-9223370508174980255"));
			//Filter filter2 = new RowFilter(CompareFilter.CompareOp.LESS, new SubstringComparator("1528429974887"));
			//Filter filter3 = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("2911"));
			//List<Filter> list = new ArrayList<Filter>();
			//list.add(filter1);
		//	list.add(filter2);
			//list.add(filter3);
			Filter filter2 = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator("-6-6-"));
			List<Filter> list = new ArrayList<Filter>();
			list.add(filter2);
			Scan scan = new Scan();
			// 通过将operator参数设置为Operator.MUST_PASS_ONE,达到list中各filter为"或"的关系
			// 默认operator参数的值为Operator.MUST_PASS_ALL,即list中各filter为"并"的关系
			//Filter filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, list);
			//scan.setFilter(filterList);
			long currentTime = System.currentTimeMillis();
			scan.setStartRow("8-1529463599189".getBytes());
			scan.setStopRow(("8-"+String.valueOf(currentTime)).getBytes());
			Filter filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, list);
			scan.setFilter(filterList);
			ResultScanner scanner = table.getScanner(scan);
			int i = 0;
			 for (Result res : scanner) {
                 System.out.println(new String(res.getValue("f1".getBytes(), "aa".getBytes())));
                 i++;
			 }
			 System.out.println(i);
			 scanner.close();
			table.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		new HbaseClient().test();
		try {
			conn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}
}