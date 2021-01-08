package com.justbon.lps.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData.Record;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.flink.shaded.curator4.org.apache.curator.shaded.com.google.common.collect.Lists;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.justbon.lps.entity.BehalogElasticsearchEntity;
import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.entity.origin.AppOriginLogUserEventExtendData;
import com.justbon.lps.schema.BehalogHbaseSchema;
import com.justbon.lps.utils.AvroUtils;
import com.justbon.lps.utils.JsonUtil;

public class Test {
	
	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	private static final Schema SCHEMA = new BehalogHbaseSchema().getSchema();
	
	/*public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		BehalogElasticsearchEntity entity = new BehalogElasticsearchEntity();
		AppOriginLogUserEventExtendData tat = new AppOriginLogUserEventExtendData();
		tat.setMethod("test");
		entity.setUserRegisterTime(1891234567890l);
		entity.setData(Lists.newArrayList(tat));
		entity.setEndTime(1891234567890l);
		entity.setUserOrgIdArea(Lists.newArrayList("a"));
		entity.setUserOrgIdAreaFunctional(Lists.newArrayList("b"));
		entity.setUserOrgIdCompany(Lists.newArrayList("c"));
		entity.setUserOrgIdHeadFunctional(Lists.newArrayList("s"));
		entity.setUserOrgIdProject(Lists.newArrayList("w"));
		String json = gson.toJson(entity);
		System.out.println(json);
		try {
			JsonNode parse = JsonUtil.parse(json);
			Schema inferSchema = JsonUtil.inferSchema(parse, "behalogHbase");
			System.out.println(inferSchema.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void main(String[] args) {
		String json = "{\"rowkey\":{\"string\":\"1231\"},\"device_id\":null}";
		BehalogHbaseEntity entity = new BehalogHbaseEntity();
		entity.setDeviceId("123");
		try {
			System.out.println(AvroUtils.json2Avro(json, SCHEMA));
			//System.out.println(AvroUtils.json2Avro(json, SCHEMA));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String str1 = "{\"resourceId\":\"dfead70e4ec5c11e43514000ced0cdcaf\",\"properties\":{\"process_id\":\"process4\",\"name\":\"\",\"documentation\":\"\",\"processformtemplate\":\"\"}}";
		String tmp = StringEscapeUtils.unescapeJson(str1);
		StringEscapeUtils.escapeJson("");
		System.out.println("tmp:" + tmp);*/
	}
}
