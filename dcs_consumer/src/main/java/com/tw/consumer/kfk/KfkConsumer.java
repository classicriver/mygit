package com.tw.consumer.kfk;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.lmax.disruptor.RingBuffer;
import com.tw.consumer.config.Config;
import com.tw.consumer.config.KafkaConfig;
import com.tw.consumer.log.LogFactory;
import com.tw.consumer.model.OriginMessage;
/**
 * 
 * @author xiesc
 * @TODO kafka消费者
 * @time 2018年8月23日
 * @version 1.0
 */
public class KfkConsumer extends KafkaConfig implements Runnable {

	private final RingBuffer<OriginMessage> ringBuffer;
	private final Semaphore semaphore;
	private Boolean flag = true;
	/**
	 *KafkaConsumer is not thread safe;
	 */
	protected final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(pro);
	/**
	 * kafka 分区偏移量
	 */
	protected final Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
	
	public KfkConsumer(RingBuffer<OriginMessage> ringBuffer,Semaphore semaphore) {
		this.ringBuffer = ringBuffer;
		this.semaphore = semaphore;
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
	public void run() {
		// TODO Auto-generated method stub
		while (flag) {
			ConsumerRecords<String, String> records = consumer.poll(Duration
					.ofMillis(500));
			for (TopicPartition partition : records.partitions()) {
				try {
					List<ConsumerRecord<String, String>> partitionRecords = records
							.records(partition);
					for (ConsumerRecord<String, String> record : partitionRecords) {
						if(!"close".equals(record.value())){
							semaphore.acquire();
							long sequence = ringBuffer.next();
							try {
								// Get the entry in the Disruptor for the sequence
								OriginMessage event = ringBuffer.get(sequence);
								// Fill with data
								event.setMessage(record.value());
							} finally {
								ringBuffer.publish(sequence);
							}
						}
						//System.out.println(record.value());
					}
					// 上报位移信息
					long lastOffset = partitionRecords.get(
							partitionRecords.size() - 1).offset();
					if (!offsets.containsKey(partition)) {
						offsets.put(partition, new OffsetAndMetadata(
								lastOffset + 1));
					} else {
						long curr = offsets.get(partition).offset();
						if (curr <= lastOffset + 1) {
							offsets.put(partition, new OffsetAndMetadata(
									lastOffset + 1));
						}
					}
					if (offsets.isEmpty()) {
						return;
					}
				} catch(Exception e){
					e.printStackTrace();
					LogFactory.getLogger().error("exception happened.",e.getMessage());
				}finally {
					consumer.commitSync(offsets);
					offsets.clear();
				}
			}
		}
		consumer.close();
	}

	public void stop() {
		// TODO Auto-generated method stub
		this.flag = false;
	}
}
