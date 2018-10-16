package com.tw.consumer.analysizer;

import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.gson.Gson;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.hbase.HbaseClientManager;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.model.YhMessage;
import com.tw.consumer.redis.RedisAdapter;
import com.tw.consumer.utils.Constants;
import com.tw.consumer.utils.ObjectTranscoder;
import com.tw.consumer.utils.RowKeyHelper;
/**
 * @author xiesc
 * @TODO 研华数据解析
 * @time 2018年8月31日
 * @version 1.0
 */
public class AnalyzerYanHua01 implements Analyzer{
	
	private final static Gson gson = new Gson();
	private final static HbaseClientManager manager = SingleBeanFactory.getBean(HbaseClientManager.class);
	private final RedisAdapter redis = new RedisAdapter();
	/**
	 * hbase rowkey生成器
	 */
	private final static RowKeyHelper rowKeyHelper = SingleBeanFactory.getBean(RowKeyHelper.class);
	private final static byte[] snColumnBytes = "sn".getBytes();
	private final static byte[] timeColumnBytes = "time".getBytes();
	@Override
	public void analysize(OriginMessage message) {
		// TODO Auto-generated method stub
		YhMessage yhMessage = gson.fromJson(message.getMessage(), YhMessage.class);
		saveData(yhMessage);
		//减轻GC压力
		message.setMessage(null);
	}
	/**
	 * 遥测、遥信两个列簇
	 * @param yhMessage
	 */
	private void saveData(YhMessage yhMessage){
		//遥测
		Map<String, Object> data_yc = yhMessage.getData_yc();
		//遥信
		Map<String, Object> data_yx = yhMessage.getData_yx();
		/** <p>
		 * data_yc map 格式{ipv1:1.0,ipv2:2.0...}
		 * <p>
		 */
		String sn = yhMessage.getSn();
		long currentTime = System.currentTimeMillis();
		Put put = new Put(rowKeyHelper.getRowKey(currentTime,sn));
		getSnAndTimeColumn(sn ,currentTime ,put);
		mapTransverter(data_yc,Constants.FAMILYYC,put);
		mapTransverter(data_yx,Constants.FAMILYYX,put);
		//写入redis缓存
		redis.save((sn+"_yc").getBytes(), ObjectTranscoder.serialize(data_yc));
		redis.save((sn+"_yx").getBytes(), ObjectTranscoder.serialize(data_yx));
		manager.save(put);
	}
	
	//遥测、遥信两个列簇 冗余sn和时间，方便hive统计查询
	private void getSnAndTimeColumn(String sn,long time,Put put){
		byte[] currentTimeBytes = Bytes.toBytes(String.valueOf(time));
		byte[] snBytes = Bytes.toBytes(sn);
		put.addColumn(Constants.FAMILYYC,timeColumnBytes,currentTimeBytes);
		put.addColumn(Constants.FAMILYYX,timeColumnBytes,currentTimeBytes);
		put.addColumn(Constants.FAMILYYC,snColumnBytes,snBytes);
		put.addColumn(Constants.FAMILYYX,snColumnBytes,snBytes);
	}
	/**
	 * 遥测或遥信的map转换成put
	 * @param map
	 * @param put
	 */
	private void mapTransverter(Map<String,Object> map,byte[] family,Put put){
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			put.addColumn(family,Bytes.toBytes(key),Bytes.toBytes(String.valueOf(map.get(key))));
		}
	}

}
