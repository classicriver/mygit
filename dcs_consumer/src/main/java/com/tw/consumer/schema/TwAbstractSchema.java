package com.tw.consumer.schema;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.tw.consumer.resources.InputStreamResources;

/**
 * 
 * @author xiesc
 * @TODO avro转换类
 * @time 2018年11月23日
 * @version 1.0
 */
public abstract class TwAbstractSchema extends InputStreamResources {

	protected Schema schema;

	public Schema getSchema() {
		return schema;
	}

	@Override
	public void init(InputStream stream) {
		// TODO Auto-generated method stub
		try {
			this.schema = new Schema.Parser().parse(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 遥测数据转avro
	 * @param ycValue
	 * @return
	 */
	public ProducerRecord<String, GenericRecord> getRecord(Map<String, Object> ycValue) {
		GenericRecord record = new GenericData.Record(schema);
		typeTransform(record,ycValue);
		ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>(
				getTopic(), record);
		return producerRecord;
	}
	/**
	 * 数据类型转换
	 * @param type
	 * @param record
	 * @param ycValue
	 */
	private void typeTransform(GenericRecord record,Map<String, Object> ycValue){
		for(Field field : record.getSchema().getFields()){
			String fieldName = field.name();
			Object fieldValue = null;
			switch(field.schema().getType()){
			case INT:
				fieldValue = Integer.parseInt(ycValue.get(fieldName) == null ?  "0" : ycValue.get(fieldName).toString());
				break;
			case DOUBLE:
				fieldValue = new Double(ycValue.get(fieldName) == null ?  "0" :  ycValue.get(fieldName).toString());
				break;
			case LONG:
				fieldValue = new Long(ycValue.get(fieldName) == null ?  "0" : ycValue.get(fieldName).toString());
				break;
			default:
				fieldValue = ycValue.get(fieldName) == null ?  "0" : ycValue.get(fieldName).toString();
				break;
			}
			record.put(fieldName, fieldValue);
		}
	}
	/**
	 * 要发送的主题
	 * @return
	 */
	protected abstract String getTopic();

}
