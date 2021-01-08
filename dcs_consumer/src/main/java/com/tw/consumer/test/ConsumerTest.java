package com.tw.consumer.test;

import java.util.Collections;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.tw.consumer.config.KafkaConfig;

public class ConsumerTest extends KafkaConfig{
	
	public void con(){
		pro.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	        // 使用Confluent实现的KafkaAvroDeserializer
		pro.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
	        // 添加schema服务的地址，用于获取schema
		pro.put("schema.registry.url", "http://kafka2:18081");
		pro.put("group.id", "test.jw.123");
	        KafkaConsumer<String, GenericRecord> consumer = new KafkaConsumer<>(pro);
	        consumer.subscribe(Collections.singletonList("jw_combinerdc"));
	        try {
	            while (true) {
	                ConsumerRecords<String, GenericRecord> records = consumer.poll(1000);
	                for (ConsumerRecord<String, GenericRecord> record : records) {
	                    GenericRecord user = record.value();
	                    System.out.println(user.toString());
	                }
	            }
	        } finally {
	            consumer.close();
	        }
	}
	
	public static void main(String[] args) {

		new ConsumerTest().con();
	      
	}
}
