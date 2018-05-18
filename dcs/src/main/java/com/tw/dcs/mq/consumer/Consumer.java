package com.tw.dcs.mq.consumer;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import com.tw.config.Config;

public class Consumer {
	 public static void main(String[] args) throws InterruptedException, MQClientException {

	        //指定一个Consumer Group 来创建一个consumer
	        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Config.getProducerName());
	        //指定NameServer 地址,consumer和NameServer建立长链接,并且获取topic信息以及broker的ip和地址
	        consumer.setNamesrvAddr(Config.getNameServer());
	        //指定消费offset的位置。TIMESTAMP表示从consumer建立后,producer向broker新发送的消息开始
	        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
	        //指定消费topic,和过滤的TAG
	        consumer.subscribe("PushTopic", "*");
	        //注册一个回调函数,当comsumer接收到消息时,执行的动作
	        consumer.registerMessageListener(new MessageListenerConcurrently() {
	            @Override
	            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
	                ConsumeConcurrentlyContext context) {
	                System.out.printf(Thread.currentThread().getName() + " Receive New Messages: " + msgs + "%n");
	                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	            }
	        });
	        //启动comsumer实例
	        consumer.start();
	        System.out.printf("Consumer Started.%n");
	    }
}
