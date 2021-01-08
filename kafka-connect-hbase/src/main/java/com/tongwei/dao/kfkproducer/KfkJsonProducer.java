package com.tongwei.dao.kfkproducer;

import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;
import com.tongwei.hbase.config.HBaseSinkConfig;

public class KfkJsonProducer extends AbstactKfkProducer<String>{
	
	private final static Gson gson = new Gson();
	private final HBaseSinkConfig config;
	
	public KfkJsonProducer(HBaseSinkConfig config){
		this.config = config;
		config.getPropertyValue(HBaseSinkConfig.KFKJOSN_TOPIC);
		pro.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		pro.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(pro);
	}
	
	public void add(Map<String,Object> ycData){
		String json = gson.toJson(ycData);
		if(null != json){
			producer.send(new ProducerRecord<String,String>(
					getTopic(), json));
		}
	}
	
	public String getTopic(){
		return config.getPropertyValue(HBaseSinkConfig.KFKJOSN_TOPIC,"jw_yc_process");
	}

}
