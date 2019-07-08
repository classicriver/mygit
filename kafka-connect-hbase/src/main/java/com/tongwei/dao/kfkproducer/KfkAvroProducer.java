package com.tongwei.dao.kfkproducer;

import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;

import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.hbase.util.CommonUtils;
import com.tongwei.schema.AvroSchema;

/**
 * 
 * @author xiesc
 * @TODO kfk生产者,把数据按照设备类型用avro序列化，由hdfs connector写到hive
 * @time 2018年11月21日
 * @version 1.0
 */
public class KfkAvroProducer extends AbstactKfkProducer<GenericRecord>{

	public KfkAvroProducer(HBaseSinkConfig config) {
		pro.put("key.serializer",
				"org.apache.kafka.common.serialization.StringSerializer");
		pro.put("value.serializer",
				"io.confluent.kafka.serializers.KafkaAvroSerializer");
		pro.put("schema.registry.url", config.getPropertyValue(""));
		//pro.put("compression.type", "gzip");
		producer = new KafkaProducer<>(pro);
	}
	
	public void add(final AvroSchema schema,Map<String, Object> ycData) {
		long time = new Long(ycData.get("timestamps").toString());
		ycData.put("day_string", CommonUtils.getDay(time));
		ycData.put("hour_string", CommonUtils.getHour(time));
		ycData.put("datetime_string", CommonUtils.getDateTime(time));
		if(null != schema){
			producer.send(schema.getRecord(ycData));
		}
	}

}
