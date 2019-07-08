package com.tongwei.schema;

import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

public interface AvroSchema {
	
	public ProducerRecord<String, GenericRecord> getRecord(Map<String, Object> ycValue);
	
}
