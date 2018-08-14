package com.tw.mq.producer;

import java.util.HashMap;
import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.protocol.body.ClusterInfo;
import org.apache.rocketmq.common.protocol.route.BrokerData;
import org.apache.rocketmq.remoting.exception.RemotingConnectException;
import org.apache.rocketmq.remoting.exception.RemotingSendRequestException;
import org.apache.rocketmq.remoting.exception.RemotingTimeoutException;

import com.tw.common.utils.MQStatus;
import com.tw.resources.ConfigProperties;

public abstract class AbstractRocketMQProducer implements MQProducer {

	/**
	 * MQ生产者
	 */
	protected final static DefaultMQProducer producer;

	static {
		producer = new DefaultMQProducer(ConfigProperties.getInstance().getProducerName());
		producer.setNamesrvAddr(ConfigProperties.getInstance().getNameServer());
		// producer.setInstanceName(UUID.randomUUID().toString());
		producer.setVipChannelEnabled(false);
		producer.setRetryTimesWhenSendFailed(3);
		producer.setDefaultTopicQueueNums(8);
		try {
			producer.start();
		} catch (MQClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		producer.shutdown();
	}

	@Override
	public void send(String msg) {
		send0(msg);
		// LogFactory.getLog(AbstractMQClient.class).info(msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void send(List<?> list) {
		bathSend((List<Message>) list);
	}
	
	/**
	 * MQserver上线检测
	 * @return
	 */
	@Override
	public MQStatus getMQStatus() {
		// 从mq服务器获取所有的Broker
		ClusterInfo brokerClusterInfo;
		try {
			brokerClusterInfo = producer.getDefaultMQProducerImpl()
					.getmQClientFactory().getMQClientAPIImpl()
					.getBrokerClusterInfo(5000);
			HashMap<String, BrokerData> brokerAddrTable = brokerClusterInfo
					.getBrokerAddrTable();
			// MQ上线了
			if (brokerAddrTable.size() > 0) {
				return MQStatus.UP;
			}
		} catch (RemotingTimeoutException | RemotingSendRequestException
				| RemotingConnectException | InterruptedException
				| MQBrokerException e) {
			return MQStatus.DOWN;
		}
		return MQStatus.DOWN;
	}

	protected abstract void send0(String msg);

	protected abstract void bathSend(List<Message> msg);

}
