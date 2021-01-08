package com.justbon.lps.flatmap;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.schema.BehalogHbaseSchema;
import com.justbon.lps.utils.AvroUtils;

/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 行为日志处理逻辑（和行为相关的埋点日志）
 */
public class BehalogFlatmapperHbase implements FlatMapFunction<BehalogHbaseEntity,GenericRecord>{
	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = -4967831291790465672L;
	private static final Schema SCHEMA = new BehalogHbaseSchema().getSchema();

	@Override
	public void flatMap(BehalogHbaseEntity hbaseEntity, Collector<GenericRecord> out) throws Exception {
		try {
			out.collect(AvroUtils.object2Avro(hbaseEntity, SCHEMA,true));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
