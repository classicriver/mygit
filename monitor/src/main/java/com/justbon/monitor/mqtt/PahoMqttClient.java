package com.justbon.monitor.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.log.LogFactory;
/**
 * @author xiesc
 * @date 2019年9月29日
 * @version 1.0.0
 * @Description: TODO eclipse paho mqtt 客户端
 */
public class PahoMqttClient implements MqttCallback {

	private MqttClient client;
	private final PropertiesConfig config = PropertiesConfig.getInstance();
	private String host;
	private String userName;
	private String pwd;
	private String topic;
	private MqttConnectOptions options;

	//mqtt服务器下线标识
	private volatile Boolean serverIsConnected;

	public PahoMqttClient() {
		host = config.getStr(PropertyKeys.MQTT_ENDPOINT);
		userName = config.getStr(PropertyKeys.MQTT_USERNAME);
		pwd = config.getStr(PropertyKeys.MQTT_PWD);
		topic = config.getStr(PropertyKeys.MQTT_TOPIC);
		createConnection();
		serverIsConnected = isConnected();
		
	}
	
	public boolean isConnected(){
		return client.isConnected();
	}
	
	public void setMqttServerStatus(Boolean status) {
		this.serverIsConnected = status;
	}
	/**
	 * 
	 * @Title: sendData   
	 * @Description: 发送数据
	 * @param: topic 主题
	 * @param: payload 数据
	 * @param: @return
	 * @return:MqttDeliveryToken
	 */
	public boolean sendData(byte[] payload) {
		MqttDeliveryToken token = null;
		if(serverIsConnected){
			try {
				MqttMessage msg = new MqttMessage();
				msg.setQos(2);
				msg.setRetained(false);
				msg.setPayload(payload);
				token = client.getTopic(topic).publish(msg);
				token.waitForCompletion();
			} catch (Exception e) {
				e.printStackTrace();
				LogFactory.error("发送mqtt数据失败，异常： "+e.getMessage());
			}
		}
		if(null != token && true == token.isComplete()){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @Title: createConnection   
	 * @Description: 创建连接  
	 * @param: 
	 * @return:void
	 */
	public void createConnection() {
		try {
			// host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
			client = new MqttClient("tcp://10.0.0.188:1883", "monitor_"+MqttClient.generateClientId(), new MemoryPersistence());
			// MQTT的连接设置
			options = new MqttConnectOptions();
			String[] stringHosts = host.split(",");
			options.setServerURIs(stringHosts);
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(true);
			//options.setWill("m_will/"+ topic.split("/")[1], "connection lost".getBytes(), 0, true);
			// 设置连接的用户名
			options.setUserName(userName);
			options.setAutomaticReconnect(false);
			// 设置连接的密码
			options.setPassword(pwd.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(1000);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(30);
			client.connect(options);
			client.setCallback(this);
		} catch (Exception e) {
			e.printStackTrace();
			LogFactory.error("创建mqtt连接失败，异常： "+e.getMessage());
		}
	}
	/**
	 * @Title: reConnection   
	 * @Description: 重连  
	 * @param: 
	 * @return:void
	 */
	public void reConnection(){
		if(null != client) {
            try {
				client.connect(options);
				client.setCallback(this);
			} catch (MqttSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	/**
	 * 
	 * @Title: close   
	 * @Description: 关闭连接  
	 * @param: 
	 * @return:void
	 */
	public void close() {
		try {
			client.disconnectForcibly();
			client.close();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogFactory.error("关闭mqtt连接失败，异常： "+e.getMessage());
		}
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		LogFactory.error("monitor mqtt connection lost! "+cause.getMessage());
		reConnection();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
	}

}
