package com.tw.consumer.mq;

import java.time.Duration;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.lmax.disruptor.RingBuffer;
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
	private Boolean flag = true;

	public KfkConsumer(RingBuffer<OriginMessage> ringBuffer) {
		this.ringBuffer = ringBuffer;
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
