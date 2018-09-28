package com.tw.connect.flink;

import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.table.descriptors.Json;


public class FlinkTable extends FlinkConfig {

	// flink运行环境
	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	// flinktable环境
	private final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);
	// jsonschema转换
	private final FlinkTableSchema schema = new FlinkTableSchema();
	private final String tableName = "kfk";

	@Override
	void init() {
		// TODO Auto-generated method stub
		// 设置窗口时间为 eventTime
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// 创建kafkaTableSource
		tableEnv.connect(
				new Kafka()
				.version("0.11")
				.topic(pro.getProperty("tw.flink.topic"))
				.properties(pro)
				.startFromLatest())
				.withFormat(new Json()
									.jsonSchema(schema.getJsonSchema())
									.failOnMissingField(false))
				.withSchema(schema.getSchema())
				.inAppendMode()
				.registerTableSource(tableName);
		// 创建window，执行查询、groupby等操作
		Table sqlQuery = tableEnv.scan(tableName);
		Table window = sqlQuery
				.window(Tumble.over("3.minutes").on("timestamps").as("w"))
				.groupBy("w,TGSN").select(schema.getSelectString());
		// 写入mysql
		JDBCAppendTableSink sink = JDBCAppendTableSink
				.builder()
				.setDrivername(pro.getProperty("jdbc.driverClassName"))
				.setDBUrl(pro.getProperty("jdbc.url"))
				.setUsername(pro.getProperty("jdbc.username"))
				.setPassword(pro.getProperty("jdbc.password"))
				.setQuery(schema.getInsertString(pro.getProperty("tw.mysql.tableName")))
				.setBatchSize(10)
				.setParameterTypes(schema.getFieldTypes())
				.build();
		window.writeToSink(sink);
		try {
			env.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new FlinkTable().start();
	}
}
