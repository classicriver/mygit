package com.tw.consumer.kfk.producer;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.google.gson.Gson;
import com.tw.consumer.model.GenericDeviceModel;

public class KfkJsonProducer extends AbstactKfkProducer<String,GenericDeviceModel>{
	
	private final static Gson gson = new Gson();
	
	public KfkJsonProducer(){
		pro.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		pro.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<>(pro);
	}
	
	@Override
	public void add(GenericDeviceModel model){
		String json = gson.toJson(model.getValue());
		if(null != json){
			producer.send(new ProducerRecord<String,String>(
					getTopic(), json));
			submit();
		}
	}
	
	public String getTopic(){
		return "jw_yc_process";
	}

}
