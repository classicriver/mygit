package com.tw.connect.core;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroDeserializationSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;

import com.tw.connect.aggregation.CascadeAggregation;
import com.tw.connect.async.source.AsyncJDBCReq;
import com.tw.connect.model.CascadeModel;
import com.tw.connect.model.CascadeModelMap;
import com.tw.connect.resources.PropertyResources;
import com.tw.connect.schema.CascadeAvroSchema;
import com.tw.connect.utils.Utils;

public class CascadeStreamExecution extends PropertyResources {

	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	private final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);
	private final String tableName = "cascade";
	private final CascadeAggregation ca = new CascadeAggregation();

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "flink.properties";
	}

	public void start() {
		//设置时间为事件时间
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		//将kafka读取到的avro格式的消息转为datastream
		DataStream<CascadeModel> stream = env
				.addSource(
						new FlinkKafkaConsumer011<>(
								pro.getProperty("kafka.topic"),
								ConfluentRegistryAvroDeserializationSchema.forGeneric(
										new CascadeAvroSchema().getSchema(),
										pro.getProperty("schema.registry.url")),
								pro))
				.map(new CascadeModelMap())
				.assignTimestampsAndWatermarks(
						new KafkaAssignerWithPunctuatedWatermarks());
		//async io 关联mysql维表
		DataStream<CascadeModel> asyncStream = AsyncDataStream.unorderedWait(stream, new AsyncJDBCReq(), 10000,
				  TimeUnit.MILLISECONDS, 100);
		
		//开启窗口
		Table table = tableEnv
				.fromDataStream(asyncStream,
						Utils.pojo2TableSelectString(CascadeModel.class))
				.window(Tumble.over("1.minute").on("timestamps").as(tableName))
				.groupBy(tableName + ",path")
				//.end 是窗口结束时间，取这个值表示统计结束时间
				.select(ca.getAggregationSqlString() +",path"+","+tableName
						+ ".end as statistics_date");
		// 写入mysql
		String sql = ca.getMysqlInsertString("path", "statistics_date");
		List<TypeInformation<?>> typeList = ca.getTypeList();
		typeList.add(Types.STRING);
		typeList.add(Types.SQL_TIMESTAMP);
		System.out.println(sql);
		JDBCAppendTableSink sink = JDBCAppendTableSink
				.builder()
				.setDrivername(pro.getProperty("sink.jdbc.driverClassName"))
				.setDBUrl(pro.getProperty("sink.jdbc.url"))
				.setUsername(pro.getProperty("sink.jdbc.username"))
				.setPassword(pro.getProperty("sink.jdbc.password"))
				.setQuery(sql)
				.setBatchSize(1)
				.setParameterTypes(typeList
								.toArray(new TypeInformation[typeList.size()]))
				.build();
		table.writeToSink(sink);
		try {
			env.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/**
		 * 因flink 内部时区是UTC，设置全局JVM时区为UTC，避免时区转换出现差8小时的问题
		 */
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		new CascadeStreamExecution().start();
	}

}
