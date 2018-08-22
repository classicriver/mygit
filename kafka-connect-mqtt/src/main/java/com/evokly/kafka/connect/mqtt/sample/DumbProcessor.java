package com.evokly.kafka.connect.mqtt.sample;

import com.evokly.kafka.connect.mqtt.MqttMessageProcessor;

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

	@Override
	public MqttMessageProcessor process(MqttMessage message) {
		log.debug("processing data for topic: {}; with message {}",
				message);
		this.mMessage = message;
		return this;
	}

	@Override
	public SourceRecord[] getRecords(String kafkaTopic) {
		//SourceRecord 的Schema格式要和producer的converter一致，如果不一致会导致无法触发roundrobin.
		  return new SourceRecord[]{new SourceRecord(null, null, kafkaTopic,
		  null, null, null, Schema.BYTES_SCHEMA, mMessage.getPayload())};
	}
}
