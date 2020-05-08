package com.evokly.kafka.connect.mqtt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解決paho客户端丢弃EMQX 共享订阅的问题
 * 
 * @author xiesc
 * @date 2019年9月9日
 * @version 1.0.0
 * @Description: TODO
 */
public class SharedSubCallbackRouter implements MqttCallbackExtended {
	private Map<String, IMqttMessageListener> topicFilterListeners;
	private static final Logger log = LoggerFactory.getLogger(MqttSourceConnector.class);
	private final MqttSourceTask mqttSourceTask;

	public SharedSubCallbackRouter(Map<String, IMqttMessageListener> topicFilterListeners, MqttSourceTask mqttSourceTask) {
		this.topicFilterListeners = topicFilterListeners;
		this.mqttSourceTask = mqttSourceTask;
	}

	public void addSubscriber(String topicFilter, IMqttMessageListener listener) {
		if (this.topicFilterListeners == null) {
			this.topicFilterListeners = new HashMap<>();
		}
		this.topicFilterListeners.put(topicFilter, listener);
	}

	/**
	 * This method is called when the connection to the server is lost.
	 *
	 * @param cause
	 *            the reason behind the loss of connection.
	 */
	@Override
	public void connectionLost(Throwable cause) {
		log.error("MQTT connection lost!", cause);
	}

	@Override
	public void messageArrived(String mqttTopic, MqttMessage message) throws Exception {
		IMqttMessageListener iMqttMessageListener = topicFilterListeners.get(mqttTopic.split("/")[0]);
		if(null != iMqttMessageListener){
			iMqttMessageListener.messageArrived(mqttTopic, message);
		}
	}

	/**
	 * Called when delivery for a message has been completed, and all
	 * acknowledgments have been received.
	 *
	 * @param token
	 *            the delivery token associated with the message.
	 */
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// Nothing to implement.
	}

	// mqtt重连成功后，要重新订阅topic
	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		// TODO Auto-generated method stub
		mqttSourceTask.subscribe();
	}

	/**
	 * Paho topic matcher does not work with shared subscription topic filter of
	 * emqttd
	 * https://github.com/eclipse/paho.mqtt.java/issues/367#issuecomment-300100385
	 * <p>
	 * http://emqtt.io/docs/v2/advanced.html#shared-subscription
	 *
	 * @param topicFilter
	 *            the topicFilter for mqtt
	 * @param topic
	 *            the topic
	 * @return boolean for matched
	 */
	/*private boolean isMatched(String topicFilter, String topic) {
		if (topicFilter.startsWith("$queue/")) {
			topicFilter = topicFilter.replaceFirst("\\$queue/", "");
		} else if (topicFilter.startsWith("$share/")) {
			topicFilter = topicFilter.replaceFirst("\\$share/", "");
			topicFilter = topicFilter.substring(topicFilter.indexOf('/'));
		}
		return MqttTopic.isMatched(topicFilter, topic);
	}*/
}
