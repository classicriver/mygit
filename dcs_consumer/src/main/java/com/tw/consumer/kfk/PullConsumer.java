package com.tw.consumer.kfk;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumerScheduleService;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.PullTaskCallback;
import org.apache.rocketmq.client.consumer.PullTaskContext;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import com.lmax.disruptor.RingBuffer;
import com.tw.consumer.config.Config;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.utils.Constants;

public class PullConsumer {

	private static final DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("Dcs_Group_1");
	private final RingBuffer<OriginMessage> ringBuffer;

	static {
		consumer.setNamesrvAddr(Config.getInstance().getNameServer());
	}

	public PullConsumer(RingBuffer<OriginMessage> ringBuffer){
		this.ringBuffer = ringBuffer;
	}
	
	public void pullData() {
		final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(
				"Dcs_Group_1");
		scheduleService.setDefaultMQPullConsumer(consumer);
		scheduleService.setMessageModel(MessageModel.CLUSTERING);
		scheduleService.registerPullTaskCallback(Constants.DEFUALTTOPIC,
				new PullTaskCallback() {
					@Override
					public void doPullTask(MessageQueue mq,
							PullTaskContext context) {
						// TODO Auto-generated method stub
						MQPullConsumer consumer = context.getPullConsumer();
						try {
							long offset = consumer
									.fetchConsumeOffset(mq, false);
							if (offset < 0)
								offset = 0;
							PullResult pullResult = consumer.pull(mq, "*",
									offset, 32);
							switch (pullResult.getPullStatus()) {
							case FOUND:
								if(pullResult.getMsgFoundList() != null && pullResult.getMsgFoundList().size() > 0){
									List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
									for(MessageExt msg : msgFoundList){
										long sequence = ringBuffer.next();
										try {
											 // Get the entry in the Disruptor for the sequence
											OriginMessage event = ringBuffer.get(sequence);
											// Fill with data
											event.setMessage(new String(msg.getBody())); 
										} finally {
											ringBuffer.publish(sequence);
										}
									}
								}
								break;
							case NO_MATCHED_MSG:
								break;
							case NO_NEW_MSG:
								break;
							case OFFSET_ILLEGAL:
								break;
							default:
								break;
							}
							consumer.updateConsumeOffset(mq,
									pullResult.getNextBeginOffset());
							context.setPullNextDelayTimeMillis(100);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		try {
			scheduleService.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
