package com.tw.consumer.analysizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.gson.Gson;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.hbase.HbaseClientManager;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.model.YhMessage;
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
	
	@Override
	public void analysize(OriginMessage message) {
		// TODO Auto-generated method stub
		String jsonString = message.getMessage();
		YhMessage yhMessage = gson.fromJson(jsonString, YhMessage.class);
		saveData2Hbase(yhMessage);
	}
	/**
	 * 遥测、遥信两个列簇
	 * @param yhMessage
	 */
	@SuppressWarnings("unchecked")
	private void saveData2Hbase(YhMessage yhMessage){
		//遥测
		Map<String, Object> data_yc = yhMessage.getData_yc();
		//遥信
		Map<String, Object> data_yx = yhMessage.getData_yx();
		Iterator<String> it = data_yc.keySet().iterator();
		while(it.hasNext()){
			String sn = it.next();
			Put put = new Put(new RowKeyHelper().getRowKey(sn));
			ArrayList<Map<String,String>> ycList = (ArrayList<Map<String,String>>) data_yc.get(sn);
			ArrayList<Map<String,String>> yxList = (ArrayList<Map<String,String>>) data_yx.get(sn);
			for(int i = 0; i < ycList.size();i++){
				getPuts(put,"yc".getBytes(),ycList.get(i));
				getPuts(put,"yx".getBytes(),yxList.get(i));
			}
			manager.save(put);
		}
	}
	
	private Put getPuts(Put put,byte[] family,Map<String,String> qualifierMap){
		put.addColumn(family,Bytes.toBytes(qualifierMap.get("desc")),Bytes.toBytes(qualifierMap.get("value")));
		return put;
	}
}
