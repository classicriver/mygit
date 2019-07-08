package com.tongwei.facade;

import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.tongwei.calculate.Calculation;
import com.tongwei.dao.kfkproducer.KfkAvroProducer;
import com.tongwei.dao.kfkproducer.KfkJsonProducer;
import com.tongwei.hbase.HBaseClient;
import com.tongwei.hbase.HBaseConnectionFactory;
import com.tongwei.hbase.config.HBaseSinkConfig;
import com.tongwei.hbase.util.RowKeyHelper;
import com.tongwei.schema.AvroSchema;
import com.tongwei.strategy.Strategy;

public abstract class BaseFacade {
	
	private final Calculation cal = new Calculation();
	private final KfkAvroProducer avroProducer;
	private final KfkJsonProducer jsonProducer;
	private final Strategy strategy;
	private final AvroSchema schema;
	private final RowKeyHelper rowKey;
	private final HBaseClient hBaseClient;
	private final HBaseSinkConfig config;
	
	protected BaseFacade(Strategy strategy,AvroSchema schema,HBaseSinkConfig config){
		this.config = config;
		this.strategy = strategy;
		this.schema = schema;
		avroProducer = new KfkAvroProducer(config);
		jsonProducer = new KfkJsonProducer(config);
		rowKey = new RowKeyHelper();

		final String zookeeperQuorum = config
				.getString(HBaseSinkConfig.ZOOKEEPER_QUORUM_CONFIG);
		final Configuration configuration = HBaseConfiguration.create();
		configuration.set(HConstants.ZOOKEEPER_QUORUM, zookeeperQuorum);

		final HBaseConnectionFactory connectionFactory = new HBaseConnectionFactory(
				configuration);
		this.hBaseClient = new HBaseClient(connectionFactory,config);
	}
	
	public void execute(Map<String, Object> ycData){
		cal.calculate(ycData, strategy);
		String esn = ycData.get("esn").toString();
		String time = ycData.get("time").toString();
		final byte[] rowkey = rowKey.getRowKey(time, esn);
		final Put put = new Put(rowkey);
		ycData.entrySet()
				.stream()
				.forEach(
						entry -> {
							final String qualifier = entry.getKey();
							final byte[] value = Bytes.toBytes(entry.getValue()
									.toString());
							put.addColumn(
									Bytes.toBytes(config
											.getPropertyValue(
													HBaseSinkConfig.DEFAULT_HBASE_COLUMN_FAMILY,
													"yc")), Bytes
											.toBytes(qualifier), value);
						});
		hBaseClient.add(put);
		jsonProducer.add(ycData);
		avroProducer.add(schema, ycData);
	}
	
}