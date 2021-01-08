package com.justbon.lps.flatmap;

import org.apache.avro.Schema;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.justbon.lps.entity.OperlogEntity;
import com.justbon.lps.schema.OperlogSchema;
import com.justbon.lps.utils.AvroUtils;

import com.justbon.lps.utils.StringUtils;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: 操作、审计日志
 */
public class OperlogFlatmapper implements FlatMapFunction<JsonObject, GenericRecord> {

	private static final long serialVersionUID = -4967831291790465672L;
	private static final Schema SCHEMA = new OperlogSchema().getSchema();
	private static final String[] FIELDS = { "logType", "fun", "funName", "sysName", "timestamps" };
	private static final String[] ACCESSFIELDS = { "accessUrl", "responseTime" };
	private static final String REGEXSTR = "^[\\u4E00-\\u9FA5A-Za-z0-9_-]+$";
	// 验证域名
	private static final String URLREGEX = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/).*";
	// 验证IP地址
	private static final String IPREGEX = "^((http://)|(https://))?(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})(\\.(2(5[0-5]{1}|[0-4]\\d{1})|[0-1]?\\d{1,2})){3}.*";
	// 验证localhost
	private static final String LOCALHOSTREGEX = "^((http://)|(https://))?(localhost).*";
	private static final Logger log = LogManager.getLogger(OperlogFlatmapper.class);
	private static final Gson gson = new GsonBuilder().serializeNulls().create();

	@Override
	public void flatMap(JsonObject json, Collector<GenericRecord> out) throws Exception {
		String jsonStr = json.toString();
		// 业务系统未传入日志内容、报错
		if (StringUtils.isEmpty(jsonStr)) {
			log.error("日志上传数据值为空，请检查！");
			return;
		}
		// 日志内容超过1M报错utf-8编码
		if (jsonStr.getBytes("utf-8").length > 1024 * 1024) {
			log.error("日志上传数据值长度超过1024 * 1024字节，请检查！");
			return;
		}
		// 验证"logType","fun","funName"这几个 重要字段是否在日志内容中出现
		for (String str : FIELDS) {
			String value = json.get(str).getAsString().trim();
			if (json.get(str) == null || StringUtils.isEmpty(value)) {
				log.error("日志中必填字段{}值为空，请检查！", str);
				return;
			}
			if (!value.matches(REGEXSTR)) {
				log.warn("日志中必填字段{}值非法，请检查！字段内容为{}必须为汉字，字母,横杆或下划线组成，请检查！", str, value);
			}
		}
		// 校验accessUrl 及responseTime
		JsonObject access = json.get("accessInfo").getAsJsonObject();
		for (String str : ACCESSFIELDS) {
			String value = access.get(str).getAsString().trim();
			if (StringUtils.isEmpty(value)) {
				log.error("日志中必填字段{}值为空，请检查！", str);
				return;
			}
			// 校验返回时间如果在日志服务器当前时间一小时外，报错
			/*
			 * if (str.equals("responseTime")) { try { long responseTime =
			 * access.get("responseTime").getAsLong(); long nowt =
			 * System.currentTimeMillis(); if (responseTime > nowt + 3600000 ||
			 * responseTime < nowt - 3600000) {
			 * log.error("日志中responseTime值时间格式有误 ", jsonStr,
			 * access.get("responseTime").getAsString()); return; } } catch
			 * (Exception e) {
			 * log.error("日志中responseTime值时间格式有误 [{}]responseTime[{}]", jsonStr,
			 * access.get("responseTime").getAsLong()); return; } }
			 */
			// 校验accessUrl地址格式
			if (str.equals("accessUrl")) {
				if (!value.matches(URLREGEX) && !value.matches(IPREGEX) && !value.matches(LOCALHOSTREGEX)) {
					log.error("{} accessUrl 校验未通过", str);
					return;
				}
			}
		}
		// 设置日志接收时间
		json.addProperty("receivingTime", System.currentTimeMillis());
		// 设置
		UserAgent userAgent = null;
		try {
			String userAgentStr = (String) access.get("userAgent").getAsString();
			if (!StringUtils.isEmpty(userAgentStr)) {
				log.info("userAgentStr:[{}]", userAgentStr);
			}
			userAgent = UserAgent.parseUserAgentString(userAgentStr);
			Browser browser = userAgent.getBrowser();
			// 浏览器
			String browserName = browser.getName();
			Version browserVersion = browser.getVersion(userAgentStr);
			String browserVersionStr = browserVersion.getVersion();
			if (!StringUtils.isEmpty(browserName)) {
				String[] split = browserName.split(" ", 2);
				json.addProperty("browserName", split[0]);
				if (!StringUtils.isEmpty(browserVersionStr)) {
					json.addProperty("browserVersion", browserVersionStr);
				} else if (split.length > 1) {
					json.addProperty("browserVersion", split[1]);
				} else {
					json.addProperty("browserVersion", "unknown");
				}
			}
			// 系统
			OperatingSystem operatingSystem = userAgent.getOperatingSystem();
			String operatingSystemNameString = operatingSystem.getName();
			if (!StringUtils.isEmpty(operatingSystemNameString)) {
				String[] split = operatingSystemNameString.split(" ", 2);
				json.addProperty("operatingSystemName", split[0]);
				if (split.length > 1) {
					json.addProperty("operatingSystemVersion", split[1]);
				} else {
					json.addProperty("operatingSystemVersion", "unknown");
				}
			}
		} catch (Exception e) {
			log.error("解析userAgent时出现错误", e.getMessage(), e);
			return;
		}
		// Record json2Avro = AvroUtils.json2Avro(json.toString(), SCHEMA);
		// System.out.println(StringEscapeUtils.escapeJson(json.toString()));
		try {
			out.collect(AvroUtils.object2Avro(gson.fromJson(json, OperlogEntity.class), SCHEMA));
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// out.collect(AvroUtils.json2Avro(json.toString(), SCHEMA));
	}

}
