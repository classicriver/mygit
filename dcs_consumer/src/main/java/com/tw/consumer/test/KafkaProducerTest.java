package com.tw.consumer.test;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerTest {
	
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("bootstrap.servers", "10.0.0.167:9092,10.0.0.168:9092,10.0.0.169:9092");
		props.put("group.id", "test");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
				"mqtttest", "{\"topic\":\"mqtttest\"}");
		while(true){
			kafkaProducer.send(producerRecord);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
