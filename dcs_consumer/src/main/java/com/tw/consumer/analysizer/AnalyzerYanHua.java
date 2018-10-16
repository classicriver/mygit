package com.tw.consumer.analysizer;

import java.util.ArrayList;
import java.util.HashMap;
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
public class AnalyzerYanHua implements Analyzer{
	
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
	@SuppressWarnings("unchecked")
	private void saveData(YhMessage yhMessage){
		//遥测
		Map<String, Object> data_yc = yhMessage.getData_yc();
		//遥信
		Map<String, Object> data_yx = yhMessage.getData_yx();
		Iterator<String> it = data_yc.keySet().iterator();
		/** <p>
		 * map 格式{逆变器sn1:[{desc:ipv1,value:1.0},{desc:ipv2,value:2.0}],逆变器sn2:[{......}]}
		 * <p>
		 */
		while(it.hasNext()){
			String sn = it.next();
			long currentTime = System.currentTimeMillis();
			Put put = new Put(rowKeyHelper.getRowKey(currentTime,sn));
			getSnAndTimeColumn(sn ,currentTime ,put);
			//处理遥测和遥信数据
			ArrayList<Map<String,Object>> ycList = (ArrayList<Map<String,Object>>) data_yc.get(sn);
			ArrayList<Map<String,Object>> yxList = (ArrayList<Map<String,Object>>) data_yx.get(sn);
			
			Map<String,Object> ycMap = new HashMap<String, Object>();
			Map<String,Object> yxMap = new HashMap<String, Object>();
			for(int i = 0; i < ycList.size();i++){
				getDataPuts(put,Constants.FAMILYYC,ycList.get(i));
				getDataPuts(put,Constants.FAMILYYX,yxList.get(i));
				mapTransverter(ycMap,ycList.get(i));
				mapTransverter(yxMap,yxList.get(i));
			}
			//写入redis缓存
			redis.save((sn+"_yc").getBytes(), ObjectTranscoder.serialize(ycMap));
			redis.save((sn+"_yx").getBytes(), ObjectTranscoder.serialize(yxMap));
			manager.save(put);
		}
	}
	
	private void getDataPuts(Put put,byte[] family,Map<String,Object> qualifierMap){
		put.addColumn(family,Bytes.toBytes((String)qualifierMap.get("desc")),Bytes.toBytes((String)qualifierMap.get("value")));
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
	
	private Map<String,Object> mapTransverter(Map<String,Object> map , Map<String,Object> qualifierMap){
		map.put((String)qualifierMap.get("desc"), (String)qualifierMap.get("value"));
		return map;
	}
}
