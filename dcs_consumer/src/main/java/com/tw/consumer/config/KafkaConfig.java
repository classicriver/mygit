package com.tw.consumer.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.tw.consumer.resources.PropertyResources;
/**
 * 
 * @author xiesc
 * @TODO kafka配置类
 * @time 2018年8月16日
 * @version 1.0
 */
public class KafkaConfig extends PropertyResources{
	/**
	 *KafkaConsumer is not thread safe;
	 */
	protected final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(pro);
	/**
	 * kafka 分区偏移量
	 */
	protected final Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
	
	protected KafkaConfig(){
		consumer.subscribe(Arrays.asList(Config.getInstance()
				.getKafkaTopics()),new ConsumerRebalanceListener() {
					/**
					 * 分区被取消
					 */
			        @Override
		            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		                consumer.commitSync(offsets);
		            }
			        /**
			         * 分区被分配
			         */
		            @Override
		            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		                offsets.clear();
		            }
		        });
	}
	
	@Override
	protected String getProFileName() {
		// TODO Auto-generated method stub
		return "kafka.properties";
	}
}
