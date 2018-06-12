package com.tw.mq.producer;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import com.tw.common.utils.Constants;
import com.tw.common.utils.ListSplitter;
import com.tw.common.utils.Utils;
import com.tw.config.Config;
import com.tw.quartz.QuartzScheduler;
import com.tw.quartz.job.MQListeningJob;
import com.tw.quartz.job.ScheduleWriterJob;
/**
 * 
 * @author xiesc
 * @TODO rocketMQ  生产者实现类
 * @time 2018年5月24日
 * @version 1.0
 */
public class RocketMQProducerImpl extends AbstractRocketMQProducer {
	/**
	 * 定时任务调度器
	 */
	private final static QuartzScheduler scheduler = new QuartzScheduler();
	
	/**
	 * 如果连不上消息服务器，把数据放到队列中，序列化到本地磁盘保存
	 */
	private final ConcurrentLinkedQueue<String> serializeQueue = new ConcurrentLinkedQueue<String>();
	
	/**
	 * MQ下线标识
	 */
	protected volatile Boolean isDown = false;

	private final static ReentrantLock lock = new ReentrantLock();

	@Override
	protected void send0(String msg) {
		// TODO Auto-generated method stub
		// SendResult send;
		Message message =  new Message(Constants.DEFUALTTOPIC,
					Constants.DEFUALTTAG, Utils.getStringUniqueId(),
					msg.getBytes());
		try {
			if (!isDown) {
				producer.send(message);
			} else {
				serializeQueue.add(msg);
			}

		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			startListeningThread();
			serializeQueue.add(msg);
			// e.printStackTrace();
		} catch (RemotingException | MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			// help gc
			message = null;
		}
		
	}

	@Override
	protected void bathSend(List<Message> list) {
		// TODO Auto-generated method stub
		try {
			ListSplitter splitter = new ListSplitter(list);
			while (splitter.hasNext()) {
				List<Message> listItem = splitter.next();
				producer.send(listItem);
			}
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			startListeningThread();
			for (Message message : list) {
					serializeQueue.add(message.getBody().toString());
			}
			// e.printStackTrace();
		} catch (RemotingException | MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检测到MQ下线后，启动2个定时任务线程 1.把数据序列化到本地 2.监听MQ是否上线
	 */
	private void startListeningThread() {
		lock.lock();
		if (!isDown) {
			setIsDown(true);
			scheduler.setJobData("queue", serializeQueue);
			scheduler.setJobData("MQProducer", this);
			scheduler.startSimpleScheduler(Constants.AVROSCHEDULERJOBNAME,
					"AvroWritertrigger", Config.getRepeatInterval(),
					ScheduleWriterJob.class);
			scheduler.startSimpleScheduler("MQJob", "MQtrigger", 300,
					MQListeningJob.class);
		}
		lock.unlock();
	}
	
	public void setIsDown(Boolean down) {
		isDown = down;
	}
	
}
