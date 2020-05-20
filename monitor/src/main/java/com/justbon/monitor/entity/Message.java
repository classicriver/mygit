package com.justbon.monitor.entity;

import com.justbon.monitor.utils.SnowFlake;
/**
 * 
 * @author xiesc
 * @date 2020年5月14日
 * @version 1.0.0
 * @Description: TODO 
 */
public class Message {
	/*
	 * kafka-connector-jdbc 里有jsonschema和连接器配置文档，jsonschema类型转换在jdbc连接器源码PreparedStatementBinder类中
	 * 连接器doc:https://www.confluent.io/blog/kafka-connect-deep-dive-jdbc-source-connector/?_ga=2.255317832.197039019.1589419866-247723338.1566357847#bytes-decimals-numerics
	 */
	private final String schema = "{"
			+ "\"schema\": {"
			+" \"type\": \"struct\","
			+" \"fields\": [{"
			+" 	\"type\": \"bytes\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Decimal\","
			+"\" version\": 1,"
			+"\"parameters\": {"
		    +"\"scale\": \"2\""
		    +"   },"
			+" 	\"field\": \"cpu\""
			+" }, {"
			+" 	\"type\": \"bytes\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Decimal\","
			+"\" version\": 1,"
			+"\"parameters\": {"
		    +"\"scale\": \"2\""
		    +"   },"
			+" 	\"field\": \"mem\""
			+" }, {"
			+" 	\"type\": \"bytes\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Decimal\","
			+"\" version\": 1,"
			+"\"parameters\": {"
		    +"\"scale\": \"2\""
		    +"   },"
			+" 	\"field\": \"disk\""
			+" }, {"
			+" 	\"type\": \"bytes\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Decimal\","
			+"\" version\": 1,"
			+"\"parameters\": {"
		    +"\"scale\": \"2\""
		    +"   },"
			+" 	\"field\": \"temperature\""
			+" }, {"
			+" 	\"type\": \"int64\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Timestamp\","
			+"\" version\": 1,"
			+" 	\"field\": \"startTime\""
			+" }, {"
			+" 	\"type\": \"int64\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Timestamp\","
			+"\" version\": 1,"
			+" 	\"field\": \"endTime\""
			+" }, {"
			+" 	\"type\": \"int64\","
			+" 	\"optional\": true,"
			+" \"name\": \"org.apache.kafka.connect.data.Timestamp\","
			+"\" version\": 1,"
			+" 	\"field\": \"uploadTime\""
			+" }, {"
			+" 	\"type\": \"string\","
			+" 	\"optional\": true,"
			+" 	\"field\": \"description\""
			+" }, {"
			+" 	\"type\": \"int64\","
			+" 	\"optional\": true,"
			+" 	\"field\": \"primaryid\""
			+" }, {"
			+" 	\"type\": \"string\","
			+" 	\"optional\": true,"
			+" 	\"field\": \"deviceid\""
			+" }],"
			+" \"optional\": false,"
			+" \"name\": \"foobar\""
			+" },"
			+" \"payload\": {";
	private final String endSuffix = "}}";
	private String cpu;
	private String mem;
	private String disk;
	private String temperature;
	private long startTime;
	private long endTime;
	private long uploadTime;
	private String description;
	private String deviceid;
	private SnowFlake sf = new SnowFlake(1);

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getMem() {
		return mem;
	}

	public void setMem(String mem) {
		this.mem = mem;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(long uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "CPU: " + cpu + " Memory: " + mem + " Disk: " + disk + " Temperature: " + temperature;
	}

	/**
	 * 
	 * @Title: getJsonSchema
	 * @Description: TODO kafka-connector-jdbc 需要jsonschema
	 * @param: @return
	 * @return:String 
	 * java类型与json schema类型对应关系 
	 * string	string 
	 * boolean	boolean
	 * float	float 
	 * double	double 
	 * long		int64 
	 * int		int32
	<p>
	 * 格式化的json
	 * {
		"schema": {
			"type": "struct",
			"fields": [{
				"type": "int32",
				"optional": false,
				"field": "c1"
			}, {
				"type": "string",
				"optional": false,
				"field": "c2"
			}, {
				"type": "int64",
				"optional": false,
				"name": "org.apache.kafka.connect.data.Timestamp",
				"version": 1,
				"field": "create_ts"
			}],
			"optional": false,
			"name": "foobar"
		},
		"payload": {
			"c1": 10000,
			"c2": "bar",
			"update_ts": 1501834166000
		}
	}
	</p>
	 */
	public String getJsonSchema() {
		StringBuilder sb = new StringBuilder();
		sb.append(schema);
		sb.append(getPayload());
		sb.append(endSuffix);
		return sb.toString();
	}
	
	public String getPayload(){
		StringBuilder sb = new StringBuilder();
		sb.append("\"cpu\":\""+cpu+"\",");
		sb.append("\"mem\":\""+mem+"\",");
		sb.append("\"disk\":\""+disk+"\",");
		sb.append("\"temperature\":\""+temperature+"\",");
		sb.append("\"startTime\":"+startTime+",");
		sb.append("\"endTime\":"+endTime+",");
		sb.append("\"uploadTime\":"+uploadTime+",");
		sb.append("\"description\":"+"\""+description+"\",");
		sb.append("\"primaryid\":"+sf.nextId()+",");
		sb.append("\"deviceid\":"+"\""+deviceid+"\"");
		return sb.toString();
	}

}
