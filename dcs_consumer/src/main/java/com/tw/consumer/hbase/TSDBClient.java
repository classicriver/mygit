package com.tw.consumer.hbase;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.opentsdb.core.Aggregators;
import net.opentsdb.core.DataPoint;
import net.opentsdb.core.DataPoints;
import net.opentsdb.core.Query;
import net.opentsdb.core.SeekableView;
import net.opentsdb.core.TSDB;
import net.opentsdb.utils.Config;

import org.hbase.async.HBaseClient;

public class TSDBClient {
	
	private final static TSDB tsdb;
	
	static{
		HBaseClient client = new HBaseClient("hbase");
		Config config = null;
		try {
			config = new Config(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      config.overrideConfig("tsd.storage.hbase.data_table", "tsdb");
	      config.overrideConfig("tsd.storage.hbase.uid_table", "tsdb-uid");
	      config.overrideConfig("tsd.core.auto_create_metrics", "true");
	      //config.overrideConfig("tsd.storage.salt.buckets", "8");
	     //config.overrideConfig("tsd.storage.enable_compaction", "false");
		tsdb = new TSDB(client,config);
	}
	public void query(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(format.parse("2018-06-11 15:03:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String,String> tags = new HashMap<>();
		//tags.put("station", "123");
		//tags.put("dt", "11");
		tags.put("did", "3");
		Query q = tsdb.newQuery();
		q.setTimeSeries("com.tw.station", tags, Aggregators.NONE, false);
		q.setStartTime(calendar.getTimeInMillis());
		//q.downsample(Long.MAX_VALUE, Aggregators.SUM);
		try {
			calendar.setTime(format.parse("2018-06-20 08:35:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		q.setEndTime(calendar.getTimeInMillis());
		DataPoints[] run = q.run();
		int count = 0;
		for(DataPoints d : run){
			SeekableView iterator = d.iterator();
			while(iterator.hasNext()){
				//iterator.next().
				DataPoint next = iterator.next();
				//System.out.println(iterator.next().toDouble());
				count++;
			}
		}
		System.out.println(count);
		tsdb.shutdown();
	}
	
	public void write(){
		Random r= new Random();
		Map<String,String> tags = new HashMap<>();
		tags.put("station", String.valueOf(r.nextInt(99)));
		tags.put("dt", String.valueOf(r.nextInt(99)));
		tags.put("did", String.valueOf(r.nextInt(99)));
		tsdb.addPoint("com.tw.station", System.currentTimeMillis(), r.nextInt(99), tags);
	}
	
	public void shutdown(){
		tsdb.shutdown();
	}
	
	public static void main(String[] args) {
		TSDBClient client = new TSDBClient();
		long start = System.currentTimeMillis();
		/*for(int i = 0;i<10000000;i++){
			client.write();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		client.query();
		client.shutdown();
		long end = System.currentTimeMillis();
		System.out.println("timeleft---------->" + (end-start));
	}
}
