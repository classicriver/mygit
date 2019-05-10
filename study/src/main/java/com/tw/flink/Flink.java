package com.tw.flink;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.table.api.java.Tumble;
import org.apache.flink.util.Collector;

public class Flink {
	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();
	private final StreamTableEnvironment tableEnv = TableEnvironment
			.getTableEnvironment(env);

	public void start() {
		// 设置时间为事件时间
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		List<Test> l = new ArrayList<>();
		Test t1 = new Test();
		t1.setEsn("11-2");
		t1.setTimestamp(new Long("1550627425000"));//2019-02-20 09:50:25
		t1.setTime(new Long("1"));
		t1.setValue(2);
		Test t2 = new Test();
		t2.setEsn("11-2");
		t2.setTimestamp(new Long("1550627435000"));//2019-02-20 09:50:35
		t2.setTime(new Long("2"));
		t2.setValue(3);
		Test t3 = new Test();
		t3.setEsn("11-4");
		t3.setTimestamp(new Long("1550627455000"));//2019-02-20 09:50:55
		t3.setTime(new Long("3"));
		t3.setValue(4);
		/*
		 * Test t4 = new Test(); t4.setEsn("11-3"); t4.setTimestamp(new
		 * Long("1548732600000")); t4.setTime(new Long("1548732600000"));
		 * t4.setValue(1); Test t5 = new Test(); t5.setEsn("11-2");
		 * t5.setTimestamp(new Long("1548739000000")); t5.setTime(new
		 * Long("1548739000000")); t5.setValue(1); Test t6 = new Test();
		 * t6.setEsn("11-3"); t6.setTimestamp(new Long("1548739000000"));
		 * t6.setTime(new Long("1548739000000")); t6.setValue(1); Test t7 = new
		 * Test(); t7.setEsn("11-4"); t7.setTimestamp(new
		 * Long("1548734000000")); t7.setTime(new Long("1548734000000"));
		 * t7.setValue(1);
		 */
		l.add(t1);
		l.add(t2);
		l.add(t3);
		/*
		 * l.add(t4); l.add(t5); l.add(t6); l.add(t7);
		 */
		List<Test> l1 = new ArrayList<>();
		Test t11 = new Test();
		t11.setEsn("11-2");
		t11.setTimestamp(new Long("1550627435000"));//2019-02-20 09:50:35
		t11.setTime(new Long("1"));
		t11.setValue(11);
		Test t12 = new Test();
		t12.setEsn("11-2");
		t12.setTimestamp(new Long("1550629455000"));//2019-02-20 10:24:15
		t12.setTime(new Long("2"));
		t12.setValue(12);
		Test t13 = new Test();
		t13.setEsn("11-4");
		t13.setTimestamp(new Long("1550627415000"));//2019-02-20 09:50:15
		t13.setTime(new Long("13"));
		t13.setValue(14);
		/*
		 * Test t13 = new Test(); t13.setEsn("11-4"); t13.setTimestamp(new
		 * Long("1548732000000")); t13.setTime(new Long("1548732000000"));
		 * t13.setValue(1);
		 */
		/*
		 * Test t14 = new Test(); t14.setEsn("11-3"); t14.setTimestamp(new
		 * Long("1548732600000")); t14.setTime(new Long("1548732600000"));
		 * t14.setValue(1); Test t15 = new Test(); t15.setEsn("11-2");
		 * t15.setTimestamp(new Long("1548739000000")); t15.setTime(new
		 * Long("1548739000000")); t15.setValue(1); Test t16 = new Test();
		 * t16.setEsn("11-3"); t16.setTimestamp(new Long("1548739000000"));
		 * t16.setTime(new Long("1548739000000")); t16.setValue(1); Test t17 =
		 * new Test(); t17.setEsn("11-4"); t17.setTimestamp(new
		 * Long("1548734000000")); t17.setTime(new Long("1548734000000"));
		 * t17.setValue(1);
		 */
		l1.add(t11);
		l1.add(t12);
		l1.add(t13);
		/*
		 * l1.add(t14); l1.add(t15); l1.add(t16); l1.add(t17);
		 */
		DataStream<Test> leftStream = env.fromCollection(l);
		DataStream<Test> rightStream = env.fromCollection(l1);
		
		DataStream<Test> leftTimestampsAndWatermarksStream = leftStream
				.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<Test>() {
					private static final long serialVersionUID = 1L;
					@Override
					public long extractTimestamp(Test element,
							long previousElementTimestamp) {
						// TODO Auto-generated method stub
						return element.getTimestamp();
					}
					@Override
					public Watermark checkAndGetNextWatermark(Test lastElement,
							long extractedTimestamp) {
						// TODO Auto-generated method stub
						return new Watermark(lastElement.getTimestamp());
					}
				});
		//leftTimestampsAndWatermarksStream.print();
		DataStream<Test> rightTimestampsAndWatermarksStream = rightStream
				.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<Test>() {
					private static final long serialVersionUID = 1L;
					@Override
					public long extractTimestamp(Test element,
							long previousElementTimestamp) {
						// TODO Auto-generated method stub
						return element.getTimestamp();
					}
					@Override
					public Watermark checkAndGetNextWatermark(Test lastElement,
							long extractedTimestamp) {
						// TODO Auto-generated method stub
						return new Watermark(lastElement.getTimestamp());
					}
				});
		//rightTimestampsAndWatermarksStream.print();
		DataStream<Test> apply = leftTimestampsAndWatermarksStream
				.join(rightTimestampsAndWatermarksStream)
				.where(new KeySelector<Test, String>() {
					private static final long serialVersionUID = 1L;
					@Override
					public String getKey(Test value) throws Exception {
						// TODO Auto-generated method stub
						return value.getEsn();
					}
				}).equalTo(new KeySelector<Test, String>() {
					private static final long serialVersionUID = 1L;
					@Override
					public String getKey(Test value) throws Exception {
						// TODO Auto-generated method stub
						return value.getEsn();
					}
				}).window(TumblingEventTimeWindows.of(Time.minutes(5)))
				.apply(new FlatJoinFunction<Test,Test,Test>() {
					private static final long serialVersionUID = 1L;
					@Override
					public void join(Test first, Test second,
							Collector<Test> out) throws Exception {
						// TODO Auto-generated method stub
						first.setValue(second.getValue());
						out.collect(first);
					}
				});
		apply.print();

		DataStream<Test> reduce = apply
				.keyBy("esn").timeWindow(Time.minutes(5)).reduce(new ReduceFunction<Test>() {
					private static final long serialVersionUID = 1L;
					@Override
					public Test reduce(Test value1, Test value2)
							throws Exception {
						// TODO Auto-generated method stub
						return value2;
					}
				});
		Table table = tableEnv
				.fromDataStream(reduce, "esn,timestamp.rowtime,time,value")
				.window(Tumble.over("5.minute").on("timestamp").as("t"))
				.groupBy("t,esn")
				.select("esn,max(time) as count,sum(value) as value,t.end as end");
		DataStream<Test2> dataStream = tableEnv
				.toDataStream(table, Test2.class);
		// DataStream<Test> appendStream = tableEnv.toAppendStream(table,
		// fieldTypes.toArray(new TypeInformation[fieldTypes.size()]));
		dataStream.print();
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
		new Flink().start();
	}
}
