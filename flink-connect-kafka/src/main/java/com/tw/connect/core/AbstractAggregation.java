package com.tw.connect.core;

import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;

import com.tw.connect.resources.PropertyResources;
/**
 * @author xiesc
 * @TODO  聚合运算类
 * @time 2018年10月16日
 * @version 1.0
 */
public abstract class AbstractAggregation extends PropertyResources implements Runnable {
	// flink运行环境
	protected final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	// flinktable环境
	protected final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "flink.properties";
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		AbstractSchema schema = getSchema();
		// 设置窗口时间为 eventTime
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// 设置job并行数，不可大于flink slot
		env.setParallelism(Integer.valueOf(pro.getProperty("tw.flink.parallelism")));
		// 创建kafkaTableSource
		tableEnv.connect(
				new Kafka()
				.version("0.11")
				.topic(getTopic())
				.properties(pro)
				.startFromLatest())
				.withFormat(new Json()
								.jsonSchema(schema.getJsonSchema())
								.failOnMissingField(false))
				.withSchema(schema.getSchema())
				.inAppendMode()
				.registerTableSource(getFlinkTableSourceName());
		// 创建window，执行查询、groupby等操作
		Table window = tableEnv.scan(getFlinkTableSourceName())
				.window(Tumble.over("2.minutes").on("TIMESTAMPS").as("w"))
				.groupBy("w,ESN").select(schema.getSelectString());
		// 写入mysql
		JDBCAppendTableSink sink = JDBCAppendTableSink
				.builder()
				.setDrivername(pro.getProperty("jdbc.driverClassName"))
				.setDBUrl(pro.getProperty("jdbc.url"))
				.setUsername(pro.getProperty("jdbc.username"))
				.setPassword(pro.getProperty("jdbc.password"))
				.setQuery(schema.getInsertString(getMysqlTableName()))
				.setBatchSize(1)
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
	/**
	 * jsonschema、flink schema  
	 * @return
	 */
	protected abstract AbstractSchema getSchema();

	protected abstract String getFlinkTableSourceName();

	/**
	 *subscribe kafka topic
	 * @return 
	 */
	protected abstract String getTopic();
	/**
	 * mysql table
	 * @return 
	 */
	protected abstract String getMysqlTableName();
}
