package com.tw.common.utils;
/**
 * 
 * @author xiesc
 * @TODO 常量
 * @time 2018年5月24日
 * @version 1.0
 */
public final class Constants {
	/**
	 * avro文件后缀名
	 */
	public static final String AVROSUFFIX = ".avro";
	/**
	 * rocketmq默认topic
	 */
	public static final String DEFUALTMQTOPIC = "PushTopic";
	/**
	 *rocketmq默认tag 
	 */
	public static final String DEFUALTMQTAG = "tag";
	/**
	 * avro定时序列化的jobname
	 */
	public static final String AVROSCHEDULERJOBNAME = "AvroWriterJob";
	/**
	 * avro定时序列化的trigger
	 */
	public static final String AVROSCHEDULERTRIGGERNAME = "AvroWriterTrigger";
	/**
	 * MQ监听线程jobname
	 */
	public static final String MQLISTENINGJOBNAME = "AvroWriterJob";
	/**
	 * MQ监听线程trigger
	 */
	public static final String MQLISTENINGTRIGGERNAME = "AvroWriterTrigger";
}
