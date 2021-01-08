package com.justbon.oms.flink.App;

import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.JdbcSink;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import com.justbon.oms.model.Shop;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author xiesc
 * @date 2019年11月13日
 * @version 1.0.0
 * @Description: TODO  fink demo,业务场景：求出每家店1分钟内销售额统计。
 */
@SuppressWarnings("deprecation")
public class GroupedProcessingTimeWindowSample {
	/**
	 * @author xiesc
	 * @date 2019年11月8日
	 * @version 1.0.0
	 * @Description: TODO 数据源
	 */
	private static class DataSource extends RichParallelSourceFunction<Shop> {
		private static final long serialVersionUID = 1394181241510193038L;
		private volatile boolean isRunning = true;
		@Override
		public void run(SourceContext<Shop> ctx) throws Exception {//每隔5秒生成一条数据
			Random random = new Random();
			while (isRunning) {
				Thread.sleep((getRuntimeContext().getIndexOfThisSubtask() + 1) * 1000 * 5);
				String key = String.valueOf((char) ('A' + random.nextInt(3)));
				int value = random.nextInt(100) + 1;
				long time = System.currentTimeMillis();
				System.out.println(String.format("Emits\t(%s, %d, %tT)", key, value,time));
				Shop com = new Shop();
				com.setShopName(key);
				com.setSales(value);
				com.setTime(time);
				ctx.collect(com);
			}
		}
		@Override
		public void cancel() {
			isRunning = false;
		}
	}
	public static AtomicInteger atomicInteger = new AtomicInteger(0);

	public static void main(String[] args) throws Exception {
		//获取执行环境
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// 设置时间为事件时间
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		//设置并行度
		env.setParallelism(2);
		//添加数据源
		DataStream<Shop> ds = env.addSource(new DataSource());
		//刷新水位线
		ds.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<Shop>() {
			private static final long serialVersionUID = -4118491935701818305L;
			private static final long maxWaitTime = 1000;//水位线最大等待时间
			@Override
			public long extractTimestamp(Shop element, long previousElementTimestamp) {
				return element.getTime();
			}
			@Override
			public Watermark checkAndGetNextWatermark(Shop lastElement, long extractedTimestamp) {
				return new Watermark(extractedTimestamp - maxWaitTime);
			}
		})
		//按商店名称分组
		.keyBy("shopName")
		//一分钟聚合
		.window(TumblingEventTimeWindows.of(Time.minutes(1)))
		.reduce((shop1, shop2) -> {
			shop1.setSales(shop1.getSales()+shop2.getSales());
			return shop2;
		})
		//添加jdbc sink
		.addSink(JdbcSink.sink(
                "insert into shop_sales (id,shop_name, sales, time) values (?,?,?,?)",
                (ps, t) -> {
                	ps.setString(1, String.valueOf(atomicInteger.incrementAndGet()));
                	ps.setString(2, t.getShopName());
                    ps.setInt(3, t.getSales());
                    ps.setTimestamp(4, new java.sql.Timestamp(t.getTime()));
                },
                new JdbcExecutionOptions.Builder().withBatchSize(2).build(),
                new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                        .withUrl("jdbc:postgresql://122.112.192.129:8000/financedws")
                        .withDriverName("org.postgresql.Driver")
                        .withUsername("test")
                        .withPassword("TK7GmAcv")
                        .build()));
		env.execute();
	}
}
