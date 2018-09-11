package com.tw.consumer.utils;
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
	public static final String DEFUALTTOPIC = "PushTopic";
	/**
	 *rocketmq默认tag 
	 */
	public static final String DEFUALTTAG = "tag";
	/**
	 * avro定时序列化的jobname
	 */
	public static final String AVROSCHEDULERJOBNAME = "AvroWriterJob";
	/**
	 * 汇流箱
	 */
	public static final String COMBINER = "001";
	/**
	 * 逆变器
	 */
	public static final String INVERTER = "002";
	/**
	 * 遥测列簇
	 */
	public static final byte[] FAMILYYC = "yc".getBytes();
	/**
	 * 遥信列簇
	 */
	public static final byte[] FAMILYYX = "yx".getBytes();
}
