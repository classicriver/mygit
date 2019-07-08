package com.tw.connect.core;

import java.math.BigDecimal;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.io.jdbc.JDBCAppendTableSink;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.util.Collector;

import com.tw.connect.aggregation.MatrixAggregation;
import com.tw.connect.async.source.AsyncJDBCReq;
import com.tw.connect.model.Inverter;
import com.tw.connect.model.InverterModelMap;
import com.tw.connect.model.OutPutPowerMap;
import com.tw.connect.resources.PropertyResources;
import com.tw.connect.utils.Utils;

public class MatrixStreamExecution extends PropertyResources {

	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	private final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);
	private final String tableName = "matrix";
	private final MatrixAggregation ma = new MatrixAggregation();

	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "flink.properties";
	}

	public void start() {
		// 设置时间为事件时间
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// 从kafka读取原始消息
		DataStream<Inverter> stream = env
				.addSource(
						new FlinkKafkaConsumer011<>(pro
								.getProperty("kafka.source.topic"),
								new SimpleStringSchema(), pro))
				.map(new InverterModelMap())
				.assignTimestampsAndWatermarks(
						new KafkaAssignerWithPunctuatedWatermarks())
				.keyBy("esn").timeWindow(Time.minutes(5))
				.reduce(new InverterReduce());
		/*// 从kafka读取日发电量
		DataStream<Inverter> leftStream = env
				.addSource(
						new FlinkKafkaConsumer011<>(pro
								.getProperty("kafka.leftjoin.topic"),
								new SimpleStringSchema(), pro))
				.map(new OutPutPowerMap())
				.assignTimestampsAndWatermarks(
						new KafkaAssignerWithPunctuatedWatermarks())
				.keyBy("esn").timeWindow(Time.minutes(5))
				.reduce(new InverterReduce());
		// join
		DataStream<Inverter> joinedStream = leftStream.coGroup(rightStream)
				.where(new JoinKeySelector()).equalTo(new JoinKeySelector())
				.window(TumblingEventTimeWindows.of(Time.minutes(5)))
				.apply(new CoGroupFunction<Inverter, Inverter, Inverter>() {
					private static final long serialVersionUID = 1L;
					@Override
					public void coGroup(Iterable<Inverter> first,
							Iterable<Inverter> second, Collector<Inverter> out)
							throws Exception {
						// TODO Auto-generated method stub
						for (Inverter i1 : first) {
							boolean hadElements = false;
							for (Inverter i2 : second) {
								i2.setOutput_power_day(i1.getOutput_power_day());
								out.collect(i2);
								hadElements = true;
							}
							// 除了日发电量，其他都重置为0
							if (!hadElements) {
								i1.setActive_power(new BigDecimal(0));
								i1.setInput_power(new BigDecimal(0));
								i1.setReactive_power(new BigDecimal(0));
								i1.setTotal_output_power(new BigDecimal(0));
								out.collect(i1);
							}
						}
					}
				});*/
		// async io 关联子阵id
		DataStream<Inverter> asyncStream = AsyncDataStream.unorderedWait(
				stream, new AsyncJDBCReq(), 10000, TimeUnit.MILLISECONDS,
				100);
		//asyncStream.print();
		// 子阵聚合
		Table table = tableEnv
				.fromDataStream(asyncStream,
						Utils.pojo2TableFieldsString(Inverter.class))
				.window(Tumble.over("5.minute").on("timestamps").as(tableName))
				.groupBy(tableName + ",matrix_id")
				// .end 是窗口结束时间，取这个值表示统计结束时间
				.select(ma.getFlinkSqlString() + ",matrix_id,max(region_id) as region_id,max(station_id) as station_id" + "," + tableName
						+ ".end as created");
		// .select(ca.getFlinkSqlString());
		// 写入mysql
		String sql = ma.getMysqlInsertString("matrix_id","region_id","station_id","created");
		List<TypeInformation<?>> typeList = ma.getTypeList(Types.STRING,Types.STRING,Types.STRING,
				Types.SQL_TIMESTAMP);
		JDBCAppendTableSink sink = JDBCAppendTableSink
				.builder()
				.setDrivername(pro.getProperty("sink.jdbc.driverClassName"))
				.setDBUrl(pro.getProperty("sink.jdbc.url"))
				.setUsername(pro.getProperty("sink.jdbc.username"))
				.setPassword(pro.getProperty("sink.jdbc.password"))
				.setQuery(sql)
				.setBatchSize(1)
				.setParameterTypes(
						typeList.toArray(new TypeInformation[typeList.size()]))
				.build();
		table.writeToSink(sink);
		try {
			env.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*private class JoinKeySelector implements KeySelector<Inverter, String> {
		private static final long serialVersionUID = 1L;
		@Override
		public String getKey(Inverter value) throws Exception {
			// TODO Auto-generated method stub
			return value.getEsn();
		}
	}*/
	//只要时间戳最大的那条数据
	private class InverterReduce implements ReduceFunction<Inverter> {
		private static final long serialVersionUID = 1L;
		@Override
		public Inverter reduce(Inverter first, Inverter second)
				throws Exception {
			// TODO Auto-generated method stub
			if (first.getTimestamps() <= second.getTimestamps()) {
				return second;
			}
			return first;
		}
	}

	public static void main(String[] args) {
		/**
		 * 因flink 内部时区是UTC，设置全局JVM时区为UTC，避免时区转换出现差8小时的问题
		 *//*
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));*/
		new MatrixStreamExecution().start();
	}

}
