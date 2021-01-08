package com.justbon.lps.constants;
/**
 * 
 * @author xiesc
 * @date 2020年7月20日
 * @version 1.0.0
 * @Description: 常量类
 */
public interface Constants {
	/**
	 * kafka消费者topic
	 */
	public static final String KAFKA_CONSUMER_TOPIC = "lps.kafka.consumer.topic";
	/**
	 * 系统日志
	 */
	public static final String LPS_SYSLOG_NAME = "lps.syslog.name";
	/**
	 * 行为日志
	 */
	public static final String LPS_BEHAVIOURLOG_NAME = "lps.behaviourlog.name";
	/**
	 * 操作日志
	 */
	public static final String LPS_OPERLOG_NAME = "lps.operlog.name";
	/**
	 * schema-registry 地址
	 */
	public static final String KAFKA_SCHEMA_REGISTRY_URL = "lps.kafka.schema-registry.url";
	/**
	 * rfis客户端地址
	 */
	public static final String RFIS_URL = "lps.rfis.base.url";
	/**
	 * rfis token
	 */
	public static final String RFIS_TOKEN = "lps.rfis.token";
	/**
	 * 用户类型  嘉宝生活家app
	 */
	public static final String USER_APP = "00";
	/**
	 * 用户类型 员工生活家
	 */
	public static final String USER_STAFF = "01";
	/**
	 * 行为日志hbase存储topic
	 */
	public static final String BEHALOG_HBASE_TOPIC = "lps.behaviourlog.hbase.topic";
	/**
	 * 行为日志es存储topic
	 */
	public static final String BEHALOG_ES_TOPIC = "lps.behaviourlog.es.topic";
	/**
	 * 查询MySQL日志元数据调度间隔时间
	 */
	public static final String METADATA_SCHEDULER_RATE = "lps.metaData.mysql.schedulerRate";
}
