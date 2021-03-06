/**
 * Copyright 2016 Evokly S.A.
 * See LICENSE file for License
 **/

package com.evokly.kafka.connect.mqtt;

import com.evokly.kafka.connect.mqtt.util.Version;

import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * MqttSourceTask is a Kafka Connect SourceTask implementation that reads from
 * MQTT and generates Kafka Connect records.
 */
public class MqttSourceTask extends SourceTask{
	private static final Logger log = LoggerFactory
			.getLogger(MqttSourceConnector.class);
	MqttClient mClient;
	String mKafkaTopic;
	String mMqttClientId;
	BlockingQueue<MqttMessageProcessor> mQueue = new LinkedBlockingQueue<>();
	MqttSourceConnectorConfig mConfig;
	MqttConnectOptions connectOptions;

	/**
	 * Get the version of this task. Usually this should be the same as the
	 * corresponding {@link MqttSourceConnector} class's version.
	 * @return the version, formatted as a String
	 */
	@Override
	public String version() {
		return Version.getVersion();
	}

	/**
	 * Start the task.
	 * @param props
	 *            initial configuration
	 */
	@Override
	public void start(Map<String, String> props) {
		log.info("Start a MqttSourceTask");
		mConfig = new MqttSourceConnectorConfig(props);
		mMqttClientId = mConfig.getString(MqttSourceConstant.MQTT_CLIENT_ID) != null ? mConfig
				.getString(MqttSourceConstant.MQTT_CLIENT_ID) : MqttClient
				.generateClientId();
		// Setup Kafka
		mKafkaTopic = mConfig.getString(MqttSourceConstant.KAFKA_TOPIC);
		// Setup MQTT Connect Options
		connectOptions = new MqttConnectOptions();

		/*String sslCa = mConfig.getString(MqttSourceConstant.MQTT_SSL_CA_CERT);
		String sslCert = mConfig.getString(MqttSourceConstant.MQTT_SSL_CERT);
		String sslPrivateKey = mConfig
				.getString(MqttSourceConstant.MQTT_SSL_PRIV_KEY);

		if (sslCa != null && sslCert != null && sslPrivateKey != null) {
			try {
				connectOptions.setSocketFactory(SslUtils.getSslSocketFactory(
						sslCa, sslCert, sslPrivateKey, ""));
			} catch (Exception e) {
				log.info("[{}] error creating socketFactory", mMqttClientId);
				e.printStackTrace();
				return;
			}
		}*/
		connectOptions.setCleanSession(mConfig.getBoolean(MqttSourceConstant.MQTT_CLEAN_SESSION));
		
		/*connectOptions.setConnectionTimeout(mConfig
				.getInt(MqttSourceConstant.MQTT_CONNECTION_TIMEOUT));
		connectOptions.setKeepAliveInterval(mConfig
				.getInt(MqttSourceConstant.MQTT_KEEP_ALIVE_INTERVAL));*/
		connectOptions.setServerURIs(mConfig.getString(
				MqttSourceConstant.MQTT_SERVER_URIS).split(","));

		if (mConfig.getString(MqttSourceConstant.MQTT_USERNAME) != null) {
			connectOptions.setUserName(mConfig
					.getString(MqttSourceConstant.MQTT_USERNAME));
		}

		if (mConfig.getString(MqttSourceConstant.MQTT_PASSWORD) != null) {
			connectOptions.setPassword(mConfig.getString(
					MqttSourceConstant.MQTT_PASSWORD).toCharArray());
		}
		// 自动重连
		connectOptions.setAutomaticReconnect(true);
		try {
			// Address of the server to connect to, specified as a URI, is
			// overridden using
			// MqttConnectOptions#setServerURIs(String[]) bellow.
			mClient = new MqttClient("tcp://127.0.0.1:1883", mMqttClientId,
					new MemoryPersistence());
			mClient.connect(connectOptions);
			log.info("[{}] Connected to Broker", mMqttClientId);
		} catch (MqttException e) {
			log.error("[{}] Connection to Broker failed!", mMqttClientId, e);
		}
		// Setup topic
		this.subscribe();
	}

	/**
	 * Stop this task.
	 */
	@Override
	public void stop() {
		log.info("Stoping the MqttSourceTask");
		try {
			mClient.disconnect();
			log.info("[{}] Disconnected from Broker.", mMqttClientId);
		} catch (MqttException e) {
			log.error("[{}] Disconnecting from Broker failed!", mMqttClientId,
					e);
		}
	}

	/**
	 * Poll this SourceTask for new records. This method should block if no data
	 * is currently available.
	 *
	 * @return a list of source records
	 *
	 * @throws InterruptedException
	 *             thread is waiting, sleeping, or otherwise occupied, and the
	 *             thread is interrupted, either before or during the activity
	 */
	@Override
	public List<SourceRecord> poll() throws InterruptedException {
		List<SourceRecord> records = new ArrayList<>();
		MqttMessageProcessor message = mQueue.take();
		log.debug("[{}] Polling new data from queue for '{}' topic.",
				mMqttClientId, mKafkaTopic);
		Collections.addAll(records, message.getRecords(mKafkaTopic));
		return records;
	}

	public void subscribe() {
		String[] topics = mConfig.getString(MqttSourceConstant.MQTT_TOPIC)
				.split(",");
		int defaultQos = mConfig.getInt(MqttSourceConstant.MQTT_QUALITY_OF_SERVICE);
		Map<String, IMqttMessageListener> listenersCache = new HashMap<>();
		IMqttMessageListener emqListener = new EmqListener(mQueue,mConfig);
		//qos默认值
		int[] qos = new int[topics.length];
		//Paho topic matcher does not work with shared subscription topic filter of emqttd
		IMqttMessageListener[] listeners = new IMqttMessageListener[topics.length];
		for(int i=0;i<=(topics.length-1);i++){
			String topic = topics[i];
			if (topic.startsWith("$queue/")) {
				topic = topic.replaceFirst("\\$queue/", "");
			} else if (topic.startsWith("$share/")) {
				topic = topic.replaceFirst("\\$share/", "");
			}
			//截取topic第一个/前的内容做key,所有相同开头的topic共用一个listener
			listenersCache.put(topic.split("/")[0], emqListener);
			qos[i] = defaultQos;
			listeners[i] = new EmqListener(mQueue,mConfig);
		}
		mClient.setCallback(new SharedSubCallbackRouter(listenersCache,this));
		try {
			mClient.subscribe(topics, qos,listeners);
			log.info("[{}] Subscribe to '{}' with QoS '{}'", mMqttClientId,
					topics, qos);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			log.error("[{}] Subscribe failed! ", mMqttClientId, e);
			e.printStackTrace();
		}
	}
}
