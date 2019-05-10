package com.tw.consumer.kfk.producer;

import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

import com.tw.consumer.config.Config;
import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.schema.SchemaFactory;
import com.tw.consumer.schema.TwAbstractSchema;
import com.tw.consumer.utils.CommonUtils;

/**
 * 
 * @author xiesc
 * @TODO kfk生产者,把数据按照设备类型用avro序列化，由hdfs connector写到hive
 * @time 2018年11月21日
 * @version 1.0
 */
public class KfkAvroProducer extends AbstactKfkProducer<GenericRecord,GenericDeviceModel>{

	private final SchemaFactory schemaFactory;
	
	public KfkAvroProducer() {
		pro.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		pro.put("value.serializer",
				"io.confluent.kafka.serializers.KafkaAvroSerializer");
		pro.put("schema.registry.url", Config.getInstance().getSchemaRegistryUrl());
		pro.put("compression.codec", "gzip");
		producer = new KafkaProducer<>(pro);
		schemaFactory = new SchemaFactory();
	}
	
	@Override
	public void add(GenericDeviceModel model) {
		Map<String, Object> yc = model.getValue();
		long time = new Long(yc.get("timestamps").toString());
		yc.put("day_string", CommonUtils.getDay(time));
		yc.put("hour_string", CommonUtils.getHour(time));
		yc.put("datetime_string", CommonUtils.getDateTime(time));
		TwAbstractSchema schema = schemaFactory.getSchema(model.getType());
		if(null != schema){
			producer.send(schema.getRecord(model.getValue()));
			submit();
		}
	}

}
