package com.tw.mq.client;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;

import com.tw.common.utils.Constants;
import com.tw.common.utils.ListSplitter;
import com.tw.common.utils.Utils;
import com.tw.config.Config;
import com.tw.quartz.AvroWriterJob;
import com.tw.quartz.MQListeningJob;
import com.tw.quartz.QuartzScheduler;
/**
 * 
 * @author xiesc
 * @TODO rocketMQ  生产者实现类
 * @time 2018年5月24日
 * @version 1.0
 */
public class RocketMQClientImpl extends AbstractRocketMQClient {
	/**
	 * 如果连不上MQ,数据都会放到这个队列中
	 */
	private final ConcurrentLinkedQueue<String> serializeQueue;
	/**
	 * MQ下线标识
	 */
	private static volatile Boolean isDown = false;
	/**
	 * 定时任务调度器
	 */
	private final static QuartzScheduler scheduler = new QuartzScheduler();

	private final static ReentrantLock lock = new ReentrantLock();

	public RocketMQClientImpl(ConcurrentLinkedQueue<String> serializeQueue) {
		// TODO Auto-generated constructor stub
		this.serializeQueue = serializeQueue;
	}

	@Override
	public void send0(String msg) {
		// TODO Auto-generated method stub
		// SendResult send;
		Message message;
		try {
			if (!isDown) {
				message = new Message(Constants.DEFUALTTOPIC,
						Constants.DEFUALTTAG, Utils.getStringUniqueId(),
						msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// help gc
		message = null;
	}

	@Override
	public void bathSend(List<Message> list) {
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
				try {
					serializeQueue.add(new String(message.getBody(),
							RemotingHelper.DEFAULT_CHARSET));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} catch (RemotingException | MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setIsDown(Boolean down) {
		isDown = down;
	}

	/**
	 * 检测到MQ下线后，启动2个定时任务线程 1.把数据序列化到本地 2.监听MQ是否上线
	 */
	private void startListeningThread() {
		lock.lock();
		if (!isDown) {
			isDown = true;
			scheduler.setJobData("queue", serializeQueue);
			scheduler.startSimpleScheduler(Constants.AVROSCHEDULERJOBNAME,
					"AvroWritertrigger", Config.getRepeatInterval(),
					AvroWriterJob.class);
			scheduler.setJobData("MQClientImpl", this);
			scheduler.startSimpleScheduler("MQJob", "MQtrigger", 300,
					MQListeningJob.class);
		}
		lock.unlock();
	}
	/**
	 * MQserver上线检测
	 * @return
	 */
	public boolean mqServerIsUp() {
		// 从mq服务器获取所有的Broker
		ClusterInfo brokerClusterInfo;
		try {
			brokerClusterInfo = producer.getDefaultMQProducerImpl()
					.getmQClientFactory().getMQClientAPIImpl()
					.getBrokerClusterInfo(7000);
			HashMap<String, BrokerData> brokerAddrTable = brokerClusterInfo
					.getBrokerAddrTable();
			// MQ上线了
			if (brokerAddrTable.size() > 0) {
				return true;
			}
		} catch (RemotingTimeoutException | RemotingSendRequestException
				| RemotingConnectException | InterruptedException
				| MQBrokerException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return false;
	}
}
