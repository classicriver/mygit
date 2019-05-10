package com.tw.consumer.analysizer;

import java.text.ParseException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.google.gson.Gson;
import com.tw.consumer.calculate.Calculation;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.hbase.HbaseClientManager;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.model.RedisModel;
import com.tw.consumer.model.YhMessage;
import com.tw.consumer.redis.RedisOperation;
import com.tw.consumer.sender.RedisSender;
import com.tw.consumer.strategy.CascadeStrategy;
import com.tw.consumer.strategy.CentralizedStrategy;
import com.tw.consumer.strategy.CombinerStrategy;
import com.tw.consumer.strategy.Strategy;
import com.tw.consumer.utils.Constants;
import com.tw.consumer.utils.DeviceType;
import com.tw.consumer.utils.RowKeyHelper;

/**
 * @author xiesc
 * @TODO 研华数据解析
 * @time 2018年8月31日
 * @version 1.0
 */
public class AnalyzerYanHua implements Analyzer {

	private final Gson gson;
	private final HbaseClientManager hbaseManager;
	private final RedisSender rs;
	private final Set<String> snCache;
	private final Calculation calculation;
	private final Strategy cascadeStrategy;
	private final Strategy centralizedStrategy;
	private final Strategy combinerStrategy;
	//private final Strategy envirStrategy = new EnvirStrategy();
	private final LinkedBlockingQueue<GenericDeviceModel> queue;
	private final RowKeyHelper rowKeyHelper;
	private final static String snColumnBytes = "esn";
	private final static String timeColumnBytes = "timestamps";

	public AnalyzerYanHua(LinkedBlockingQueue<GenericDeviceModel> queue) {
		// TODO Auto-generated constructor stub
		this.queue = queue;
		gson = new Gson();
		rs = SingleBeanFactory.getBean(RedisSender.class);
		snCache = Collections
				.synchronizedSet(new HashSet<String>());
		rowKeyHelper = SingleBeanFactory
				.getBean(RowKeyHelper.class);
		hbaseManager = SingleBeanFactory
				.getBean(HbaseClientManager.class);
		calculation = new Calculation();
		cascadeStrategy = new CascadeStrategy();
		centralizedStrategy = new CentralizedStrategy();
		combinerStrategy = new CombinerStrategy();
	}

	@Override
	public void analysize(OriginMessage message) {
		YhMessage yhMessage = gson.fromJson(message.getMessage(),
				YhMessage.class);
		try {
			saveData(yhMessage);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// help gc
		yhMessage = null;
		message.setMessage(null);
	}

	/**
	 * 遥测、遥信两个列簇
	 * 
	 * @param yhMessage
	 * @throws ParseException
	 */
	private void saveData(YhMessage yhMessage) throws ParseException {
		Map<String, Object> yc = yhMessage.getYc();
		Map<String, Object> yx = yhMessage.getYx();
		String sn = yhMessage.getEsn();
		String timestamps = yhMessage.getTime();
		if (timestamps.length() == 10) {
			timestamps = timestamps + "000";
		}
		Put put = new Put(rowKeyHelper.getRowKey(timestamps, sn));
		// 冗余sn和时间，方便统计和查询
		yc.put(snColumnBytes, sn);
		yc.put(timeColumnBytes, timestamps);
		DeviceType type = DeviceType.OTHER;
		if (sn.matches("(.*)-N1-(.*)")) {
			type = DeviceType.CASCADE;
			calculation.calculate(yc, cascadeStrategy);
		} else if (sn.matches("(.*)-H1-(.*)")) {
			type = DeviceType.COMBINER;
			calculation.calculate(yc, combinerStrategy);
		} else if (sn.matches("(.*)-N3-(.*)")) {
			type = DeviceType.CENTRALIZED;
			calculation.calculate(yc, centralizedStrategy);
		} else if (sn.matches("(.*)-HJ-(.*)")) {
			type = DeviceType.ENVIR;
		}
		// 遥信
		if (null != yx) {
			yx.put(snColumnBytes, sn);
			yx.put(timeColumnBytes, timestamps);
			putsTransverter(yx, Constants.FAMILYYX, put);
		}
		yc.remove("piList");
		putsTransverter(yc, Constants.FAMILYYC, put);
		saveEsn2Redis(sn);
		handleDataByType(type, yc);
		hbaseManager.save(put);
	}

	/**
	 * 遥测或遥信的map转换成put
	 * 
	 * @param map
	 * @param put
	 */
	private void putsTransverter(Map<String, Object> map, byte[] family, Put put) {
		for(Map.Entry<String, Object> m : map.entrySet()){
			Object value = m.getValue();
			if (null != value && !"".equals(value)) {
				put.addColumn(family, Bytes.toBytes(m.getKey()),
						Bytes.toBytes(value.toString()));
			}
		}
	}
	/**
	 * 写入队列
	 * @param type  
	 * @param yc
	 */
	private void handleDataByType(DeviceType type,Map<String, Object> yc) {
		GenericDeviceModel pModel = new GenericDeviceModel();
		pModel.setType(type);
		pModel.setValue(yc);
		try {
			queue.put(pModel);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 写入redis队列
	 * @param sn
	 */
	private void saveEsn2Redis(String sn) {
		if (snCache.add(sn)) {
			// 把sn写入redis方便统计哪些sn上传过数据
			RedisModel redisModel = new RedisModel();
			redisModel.setKey("tw.snlist".getBytes());
			redisModel.setOperation(RedisOperation.sadd);
			redisModel.setValue(sn.getBytes());
			rs.putMessage(redisModel);
		}
	}
}
