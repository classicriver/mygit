package com.tw.connect.core;

import java.util.concurrent.TimeUnit;

import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.formats.avro.AvroDeserializationSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.table.descriptors.Json;
import org.apache.flink.table.descriptors.Kafka;
import org.apache.flink.types.Row;

import com.tw.connect.async.source.AsyncJDBCReq;
import com.tw.connect.resources.PropertyResources;

/**
 * @author xiesc
 * @TODO 聚合运算类
 * @time 2018年10月16日
 * @version 1.0
 */
public abstract class AbstractAggregation extends PropertyResources {
	// flink运行环境
	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	// flinktable环境
	private final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);
	private final String flinkTableSourceName = "tb" + System.nanoTime();
	private final AbstractSchema schema;
	private final String windowAlias;

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "flink.properties";
	}

	protected AbstractAggregation() {
		schema = getSchema();
		windowAlias = schema.getWindowAlias();
	}

	public void start() {
		// TODO Auto-generated method stub
		// 设置窗口时间为 eventTime
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// 设置job并行数，不可大于flink slot
		// env.setParallelism(Integer.valueOf(pro.getProperty("tw.flink.parallelism")));
		// 创建kafkaTableSource
		tableEnv.connect(
				new Kafka().version("0.11").topic(getTopic()).properties(pro)
						.startFromLatest())
				.withFormat(
						new Json().jsonSchema(schema.getJsonSchema())
								.failOnMissingField(false))
				.withSchema(schema.getSchema()).inAppendMode()
				.registerTableSource(flinkTableSourceName);
		// tableEnv.registerFunction("", new AggScala());
		// 创建window
		Table table = tableEnv
				.scan(flinkTableSourceName)
				.window(Tumble.over("1.hour").on("timestamps".toUpperCase())
						.as(windowAlias)).table().select(" * ");
		table.printSchema();
		// .where("esn.like('%NBQ%')");
		//转换为stream，异步关联子阵维表
		/*DataStream<Row> stream = tableEnv.toAppendStream(table, Row.class);
		tableEnv.registerDataStream("joinedTable", AsyncDataStream
				.unorderedWait(stream, new AsyncJDBCReq(), 10000,
						TimeUnit.MILLISECONDS, 100));*/
		//按子阵group by
		Table joinedTable = tableEnv.scan("joinedTable").as("jt").groupBy("jt,path").select(
				schema.getSelectString());
		// 写入mysql
		JDBCAppendTableSink sink = JDBCAppendTableSink.builder()
				.setDrivername(pro.getProperty("sink.jdbc.driverClassName"))
				.setDBUrl(pro.getProperty("sink.jdbc.url"))
				.setUsername(pro.getProperty("sink.jdbc.username"))
				.setPassword(pro.getProperty("sink.jdbc.password"))
				.setQuery(schema.getInsertString(getMysqlTableName()))
				.setBatchSize(1).setParameterTypes(schema.getFieldTypes())
				.build();
		joinedTable.writeToSink(sink);
		try {
			env.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * jsonschema、flink schema
	 * 
	 * @return
	 */
	protected abstract AbstractSchema getSchema();

	/**
	 * subscribe kafka topic
	 * 
	 * @return
	 */
	protected abstract String getTopic();

	/**
	 * mysql table
	 * 
	 * @return
	 */
	protected abstract String getMysqlTableName();
}
