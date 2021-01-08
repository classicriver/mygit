package com.justbon.lps.main;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.avro.generic.GenericRecord;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.formats.avro.registry.confluent.ConfluentRegistryAvroSerializationSchema;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.util.OutputTag;

import com.google.gson.JsonObject;
import com.justbon.lps.broadcast.LogMetaDataBroadcast;
import com.justbon.lps.config.ServiceConfig;
import com.justbon.lps.constants.Constants;
import com.justbon.lps.datasource.AsyncRfisSource;
import com.justbon.lps.datasource.LogMetaDataSource;
import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.entity.MetaEntity;
import com.justbon.lps.flatmap.BehalogFlatmapperElasticsearch;
import com.justbon.lps.flatmap.BehalogFlatmapperHbase;
import com.justbon.lps.flatmap.OperlogFlatmapper;
import com.justbon.lps.flatmap.SyslogFlatmapper;
import com.justbon.lps.schema.BehalogElasticsearchSchema;
import com.justbon.lps.schema.BehalogHbaseSchema;
import com.justbon.lps.schema.OperlogSchema;
import com.justbon.lps.schema.SyslogSchema;

/**
 * @author xiesc
 * @date 2020年7月20日
 * @version 1.0.0
 * @Description: flink启动函数
 */
public class LogStreamingService {

	private final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
	private final Properties properties = new Properties();
	private static final ServiceConfig config = ServiceConfig.getInstance();
	private static final String OPERLOG = config.getAsString(Constants.LPS_OPERLOG_NAME);
	private static final String BEHAVIOURLOG = config.getAsString(Constants.LPS_BEHAVIOURLOG_NAME);
	private static final String SYSLOG = config.getAsString(Constants.LPS_SYSLOG_NAME);
	private static final String SCHEMA_SUBJECT_SUFFIX = "-value";
	private static final String BEHALOG_HBASE = config.getAsString(Constants.BEHALOG_HBASE_TOPIC);
	private static final String BEHALOG_ES = config.getAsString(Constants.BEHALOG_ES_TOPIC);
	
	public void start() {
		env.setParallelism(2);
		MapStateDescriptor<String, MetaEntity> metaDataDescriptor = new MapStateDescriptor<>("logMetaData",
				BasicTypeInfo.STRING_TYPE_INFO, TypeInformation.of(new TypeHint<MetaEntity>() {}));
		try {
			properties.load(LogStreamingService.class.getClassLoader().getResourceAsStream("kafka.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 从kafka获取json字符串
		SingleOutputStreamOperator<String> jsonSource = env
				.addSource(new FlinkKafkaConsumer<>(config.getAsString(Constants.KAFKA_CONSUMER_TOPIC),
						new SimpleStringSchema(), properties), "kafkaSource");
		// 广播日志元数据信息
		BroadcastStream<MetaEntity> metaBroadcast = env.addSource(new LogMetaDataSource())
				.setParallelism(1)
				.broadcast(metaDataDescriptor);

		// 系统日志
		OutputTag<JsonObject> syslogTag = new OutputTag<JsonObject>(OPERLOG) {
			private static final long serialVersionUID = 1828198425214102320L;
		};
		// 行为日志
		OutputTag<JsonObject> behalogTag = new OutputTag<JsonObject>(BEHAVIOURLOG) {
			private static final long serialVersionUID = 6648857026418467409L;
		};
		// 操作日志
		OutputTag<JsonObject> operlogTag = new OutputTag<JsonObject>(SYSLOG) {
			private static final long serialVersionUID = -7811834286666573494L;
		};

		// 分流、关联日志元数据信息
		SingleOutputStreamOperator<JsonObject> sideOutStream = jsonSource.connect(metaBroadcast)
				.process(new LogMetaDataBroadcast(metaDataDescriptor,syslogTag,behalogTag,operlogTag));

		// 行为日志--补齐app用户数据
		SingleOutputStreamOperator<BehalogHbaseEntity> behalogAsyncStream = AsyncDataStream.unorderedWait(
				sideOutStream.getSideOutput(behalogTag), new AsyncRfisSource(), 10000, TimeUnit.MILLISECONDS, 100);

		// 行为日志--写入存hbase的kafka topic
		behalogAsyncStream.flatMap(new BehalogFlatmapperHbase()).addSink(new FlinkKafkaProducer<GenericRecord>(
				BEHALOG_HBASE,
				ConfluentRegistryAvroSerializationSchema.forGeneric(
						BEHALOG_HBASE+SCHEMA_SUBJECT_SUFFIX,
						new BehalogHbaseSchema().getSchema(), config.getAsString(Constants.KAFKA_SCHEMA_REGISTRY_URL)),
				properties, null, FlinkKafkaProducer.Semantic.EXACTLY_ONCE, 5));

		// 行为日志--写入存ES的kafka topic
		behalogAsyncStream.flatMap(new BehalogFlatmapperElasticsearch())
				.addSink(new FlinkKafkaProducer<GenericRecord>(BEHALOG_ES,
						ConfluentRegistryAvroSerializationSchema.forGeneric(
								BEHALOG_ES+SCHEMA_SUBJECT_SUFFIX,
								new BehalogElasticsearchSchema().getSchema(),
								config.getAsString(Constants.KAFKA_SCHEMA_REGISTRY_URL)),
						properties, null, FlinkKafkaProducer.Semantic.EXACTLY_ONCE, 5));

		// 系统日志
		sideOutStream.getSideOutput(syslogTag).flatMap(new SyslogFlatmapper())
				.addSink(new FlinkKafkaProducer<GenericRecord>(SYSLOG,
						ConfluentRegistryAvroSerializationSchema.forGeneric(
								SYSLOG+SCHEMA_SUBJECT_SUFFIX,
								new SyslogSchema().getSchema(),
								config.getAsString(Constants.KAFKA_SCHEMA_REGISTRY_URL)),
						properties, null, FlinkKafkaProducer.Semantic.EXACTLY_ONCE, 5));

		// 操作日志
		sideOutStream.getSideOutput(operlogTag).flatMap(new OperlogFlatmapper())
				.addSink(new FlinkKafkaProducer<GenericRecord>(OPERLOG,
						ConfluentRegistryAvroSerializationSchema.forGeneric(
								OPERLOG+SCHEMA_SUBJECT_SUFFIX,
								new OperlogSchema().getSchema(),
								config.getAsString(Constants.KAFKA_SCHEMA_REGISTRY_URL)),
						properties, null, FlinkKafkaProducer.Semantic.EXACTLY_ONCE, 5));
		try {
			env.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new LogStreamingService().start();
	}
}
