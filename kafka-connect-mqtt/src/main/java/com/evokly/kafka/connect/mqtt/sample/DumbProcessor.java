package com.evokly.kafka.connect.mqtt.sample;

import com.evokly.kafka.connect.mqtt.MqttMessageProcessor;

import java.io.UnsupportedEncodingException;

import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright 2016 Evokly S.A.
 *
 * <p>
 * See LICENSE file for License
 **/
public class DumbProcessor implements MqttMessageProcessor {
	private static final Logger log = LoggerFactory
			.getLogger(DumbProcessor.class);
	private MqttMessage mMessage;
	private String mqttTopic;
	
	/*private static Schema mqttSchema;
	static{
		mqttSchema = SchemaBuilder.struct()
		          .name("twmqtt")
		          .field("message", Schema.OPTIONAL_STRING_SCHEMA)
		          .build();
	}*/

	@Override
	public MqttMessageProcessor process(String mqttTopic,MqttMessage message) {
		log.debug("processing data for topic: {}; with message {}", message);
		this.mMessage = message;
		this.mqttTopic = mqttTopic;
		return this;
	}

	@Override
	public SourceRecord getRecords(String kafkaTopic) {
		//Struct messageStruct = new Struct(mqttSchema);
		//messageStruct.put("message", new String(mMessage.getPayload()));
		String payLoad ="";
		try {
			payLoad = new String(mMessage.getPayload(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String json = "{\"topic\":\""+mqttTopic+"\",\"body\":"+payLoad+"}";
		byte[] jsonBytes = null;
		try {
			jsonBytes = json.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SourceRecord(null, null, kafkaTopic,Schema.BYTES_SCHEMA, jsonBytes);
	}

}
