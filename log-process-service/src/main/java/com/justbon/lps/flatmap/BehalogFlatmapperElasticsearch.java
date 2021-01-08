package com.justbon.lps.flatmap;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import com.justbon.lps.entity.BehalogElasticsearchEntity;
import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.schema.BehalogElasticsearchSchema;
import com.justbon.lps.utils.AvroUtils;
/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 行为日志处理逻辑（和行为相关的埋点日志）
 */
public class BehalogFlatmapperElasticsearch implements FlatMapFunction<BehalogHbaseEntity,GenericRecord>{

	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = -2826257913441888086L;
	private static final Schema SCHEMA = new BehalogElasticsearchSchema().getSchema();

	@Override
	public void flatMap(BehalogHbaseEntity hbaseEntity, Collector<GenericRecord> out) throws Exception {
		BehalogElasticsearchEntity esEntity = new BehalogElasticsearchEntity();
		BeanUtils.copyProperties(esEntity, hbaseEntity);
		try {
			out.collect(AvroUtils.object2Avro(esEntity, SCHEMA));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
