package com.tw.connect.flink.deprecated;

import javax.annotation.Nullable;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import com.tw.connect.core.AbstractAggregation;
import com.tw.connect.core.AbstractSchema;

public class FlinkStreamHandler extends AbstractAggregation {

	//private final Gson gson = new Gson();
	private final StreamExecutionEnvironment env = StreamExecutionEnvironment
			.getExecutionEnvironment();

	public void run() {
		// TODO Auto-generated method stub
		// 以EventTime作为处理时间
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		DataStream<String> stream = env.addSource(new FlinkKafkaConsumer011<>(
				pro.getProperty(""), new SimpleStringSchema(),
				pro));
		//把kafka队列中的json映射成对象
		DataStream<Order> inputMap = stream
				.map(new MapFunction<String, Order>() {
					private static final long serialVersionUID = -8812094804806854937L;
					@Override
					public Order map(String value) throws Exception {
						//gson.fromJson(value, Order.class)
						return new Order();
					}
				});
		//设置水位线
		DataStream<Order> watermark = inputMap
				.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks<Order>() {
					private static final long serialVersionUID = 8252616297345284790L;
					Long currentMaxTimestamp = 0L;
					Long maxOutOfOrderness = 10000L;// 最大允许的乱序时间是10s
					Watermark watermark = null;

					@Nullable
					@Override
					public Watermark getCurrentWatermark() {
						watermark = new Watermark(currentMaxTimestamp
								- maxOutOfOrderness);
						return watermark;
					}

					@Override
					public long extractTimestamp(Order order,
							long previousElementTimestamp) {
						Long timestamp = order.getTIME();
						currentMaxTimestamp = Math.max(timestamp,
								currentMaxTimestamp);
						return timestamp;
					}
				});
		//groupby sn，1小时汇总一次
		SingleOutputStreamOperator<Order> sum = watermark.keyBy("TGSN")
				.timeWindow(Time.minutes(1))
				.reduce(new KfkReduceFunction(), new KfkWindowFunction());
		// .window(TumblingEventTimeWindows.of(Time.minutes(1))).sum(1);
		/*
		 * .apply(new WindowFunction<Order, String, Tuple, TimeWindow>() {
		 * private static final long serialVersionUID = 7813420265419629362L;
		 * 
		 * @Override public void apply(Tuple tuple, TimeWindow window,
		 * Iterable<Order> input, Collector<String> out) throws Exception {
		 * System.out.println(tuple); SimpleDateFormat format = new
		 * SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS"); out.collect("window  "
		 * + format.format(window.getStart()) + "   window  " +
		 * format.format(window.getEnd())); } });
		 */
		sum.print();
		try {
			env.execute("window test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public static void main(String[] args) {
		new FlinkStreamHandler().run();
	}
	@Override
	protected AbstractSchema getSchema() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTopic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getFlinkTableSourceName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getMysqlTableName() {
		// TODO Auto-generated method stub
		return null;
	}
}
