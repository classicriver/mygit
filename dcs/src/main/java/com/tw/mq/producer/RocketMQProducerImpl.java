package com.tw.mq.producer;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.quartz.JobDataMap;

import com.tw.common.utils.Constants;
import com.tw.common.utils.ListSplitter;
import com.tw.common.utils.MQStatus;
import com.tw.common.utils.Utils;
import com.tw.log.LogFactory;
import com.tw.quartz.QuartzScheduler;
import com.tw.quartz.job.MQListeningJob;
import com.tw.quartz.job.ScheduleWriterJob;
import com.tw.resources.ConfigProperties;

/**
 * 
 * @author xiesc
 * @TODO rocketMQ 生产者实现类
 * @time 2018年5月24日
 * @version 1.0
 */
public class RocketMQProducerImpl extends AbstractRocketMQProducer {
	/**
	 * 如果连不上消息服务器，把数据放到队列中，序列化到本地磁盘保存
	 */
	private final Queue<String> serializeQueue = new ConcurrentLinkedQueue<String>();

	/**
	 * MQ状态标识
	 */
	protected volatile MQStatus status = MQStatus.UP;

	@Override
	protected void send0(String msg) {
		// TODO Auto-generated method stub
		try {
			if (MQStatus.UP.equals(status)) {
				Message message = new Message(Constants.DEFUALTMQTOPIC,
						Constants.DEFUALTMQTAG, Utils.getStringUniqueId(), msg.getBytes());
				producer.send(message);
			} else {
				serializeQueue.add(msg);
			}

		} catch (MQClientException e) {
			serializeQueue.add(msg);
			startListeningThread();
		} catch (RemotingException | MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void bathSend(List<Message> list) {
		try {
			ListSplitter splitter = new ListSplitter(list);
			while (splitter.hasNext()) {
				List<Message> listItem = splitter.next();
				producer.send(listItem);
			}
		} catch (MQClientException e) {
			for (Message message : list) {
				serializeQueue.add(message.getBody().toString());
			}
			startListeningThread();
		} catch (RemotingException | MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 断网或者检测到MQ下线后，启动2个定时任务线程 1.把数据序列化到本地 2.监听MQ是否上线
	 */
	private synchronized void startListeningThread() {
		if (MQStatus.UP.equals(status)) {
			setMQStatus(MQStatus.DOWN);
			final QuartzScheduler scheduler = QuartzScheduler.getInstance();
			JobDataMap jobData = new JobDataMap();
			jobData.put("queue", serializeQueue);
			jobData.put("MQProducer", this);
			scheduler.createRepeatSecondScheduler(Constants.AVROSCHEDULERJOBNAME,
					Constants.AVROSCHEDULERTRIGGERNAME, ConfigProperties.getInstance().getRepeatInterval(),jobData,
					ScheduleWriterJob.class);
			scheduler.createRepeatSecondScheduler(Constants.MQLISTENINGJOBNAME, Constants.MQLISTENINGTRIGGERNAME, 300,jobData,
					MQListeningJob.class);
			LogFactory.getLogger().warn(
					"----> MQ is offline,start mq listening thread....");
		}
	}

	@Override
	public void setMQStatus(MQStatus status) {
		// TODO Auto-generated method stub
		this.status = status;
	}

}
