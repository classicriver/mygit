package com.tw.consumer.mq;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class KafkaConsumer {
	public static void main(String[] args) {
		 Properties props = new Properties();  
	        props.put("bootstrap.servers", "172.20.90.179:9092");
	        props.put("group.id", "test");  
	        props.put("enable.auto.commit", "true");  
	        props.put("auto.commit.interval.ms", "1000");  
	        props.put("session.timeout.ms", "30000");
	        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");  
	        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
	        consumer.subscribe(Arrays.asList("kktest"));
	        while (true) {
	            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));  
	            for (ConsumerRecord<String, String> record : records)  
	                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());  
	        }
	}
}
