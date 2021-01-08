package com.justbon.monitor.constants;
/**
 * 
 * @author xiesc
 * @date 2020年4月22日
 * @version 1.0.0
 * @Description: TODO 常量类
 */
public interface PropertyKeys {
	/**
	 * 目标应用main函数全路径,agent通过这个路径找到jvm的PID
	 */
	String TARGET_MAIN_PATH = "TargetMainPath";
	/**
	 * 配置文件路径
	 */
    String PRO_FILE_PATH = "ConfigPath";
    /**
	 * 被agent类
	 */
    String MONITOR_AGENT_KLASSES = "monitor.agent.klasses";
    /**
     * agent端写入sqlite调度间隔时间，分钟
     */
    String MONITOR_AGENT_RECORDERINTERVAL = "monitor.agent.recorderSchedulerInterval";
    /**
     * sqlite存数据的表名
     */
    String MONITOR_SQLITE_RECORDSTABLE = "records";
    /**
     * mqtt地址
     */
    String MQTT_ENDPOINT = "monitor.mqtt.endpoint";
    /**
     * mqtt用户名
     */
    String MQTT_USERNAME = "monitor.mqtt.username";
    /**
     * mqtt密码
     */
    String MQTT_PWD = "monitor.mqtt.pwd";
    /**
     * mqtt topic
     */
    String MQTT_TOPIC = "monitor.mqtt.topic";
    /**
     * sqlite path
     */
    String SQLITE_PATH="monitor.sqlite.path";
    /**
     * sqlite path
     */
    String PI_ID="monitor.pi.id";
}
