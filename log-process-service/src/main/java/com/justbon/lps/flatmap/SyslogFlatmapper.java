package com.justbon.lps.flatmap;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.justbon.lps.entity.SystemlogEntity;
import com.justbon.lps.schema.SyslogSchema;
import com.justbon.lps.utils.AvroUtils;
/**
 * 
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 系统日志
 */
public class SyslogFlatmapper implements FlatMapFunction<JsonObject,GenericRecord>{
	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = -4967831291790465672L;
	private static final Schema SCHEMA = new SyslogSchema().getSchema();
	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	public void flatMap(JsonObject json, Collector<GenericRecord> out) throws Exception {
		SystemlogEntity syslog = gson.fromJson(json, SystemlogEntity.class);
		//增加日志校验逻辑
		try {
			out.collect(AvroUtils.object2Avro(syslog, SCHEMA));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
