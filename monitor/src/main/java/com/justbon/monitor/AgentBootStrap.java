package com.justbon.monitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.entity.Message;
import com.justbon.monitor.log.LogFactory;
import com.justbon.monitor.mqtt.PahoMqttClient;
import com.justbon.monitor.scheduler.SchedulerFactory;
import com.justbon.monitor.sqlite.SqliteDataSource;
import com.justbon.monitor.system.PI;
import com.justbon.monitor.system.PI.SystemInfo;
import com.justbon.pi.upgrader.UpgraderMain;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @author xiesc
 * @date 2020年4月22日
 * @version 1.0.0
 * @Description: TODO 探针主函数--找到对应的jvm注入agent jar包
 */
public class AgentBootStrap extends AbstractBootStrap {

	//private static String agentAppMainName = "com.justbon.bodhi.iot.gateway.IotGateWay";
	private static String agentAppMainName = "";
	private static PahoMqttClient mqttClient;
	private static String configFilePath = "";
//	private static String configFilePath = "C:\\Users\\Administrator.DESKTOP-HAAH264"
//			+ "\\git\\pi\\pi\\monitor\\src\\main\\resources\\application.properties";

	// 获取当前jar包的绝对路径
	protected static String getAgentJarPath() throws UnsupportedEncodingException {
		String path = URLDecoder
				.decode(AgentBootStrap.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
		if (System.getProperty("os.name").contains("dows")) {
			path = path.substring(1, path.length());
		}
		return path.replace("target/classes/", "");
		//return "E:\\maven-repository\\com\\justbon\\monitor\\0.0.1-SNAPSHOT\\monitor-0.0.1-SNAPSHOT.jar";
	}

	@Override
	public boolean init() {
		// TODO Auto-generated method stub
		agentAppMainName = System.getProperty(PropertyKeys.TARGET_MAIN_PATH);
		configFilePath = System.getProperty(PropertyKeys.PRO_FILE_PATH);
		if (null == agentAppMainName || agentAppMainName.isEmpty()) {
			LogFactory.error("need full kalass path args!");
			return false;
		}
		if (null == configFilePath || configFilePath.isEmpty()) {
			LogFactory.error("need config file path args!");
			return false;
		}

		if (!PropertiesConfig.getInstance().initProperties(configFilePath)) {
			LogFactory.error(" Properties init FAILURE!!!");
			return false;
		}
		if(!UpgraderMain.initUpgrader(PropertiesConfig.getInstance().getProperties())){
			LogFactory.error(" initUpgrader init FAILURE!!!");
			return false;
		};
		mqttClient = new PahoMqttClient();
		return true;
	}

	public static void main(String[] args) {
		AgentBootStrap bt = new AgentBootStrap();
		if (bt.init()) {
			List<VirtualMachineDescriptor> list = VirtualMachine.list();
			// 找到匹配的jvm 注入agent
			for (VirtualMachineDescriptor vmd : list) {
				System.out.println("vim displayName:  "+vmd.displayName());
				if (vmd.displayName().endsWith(agentAppMainName)) {
					VirtualMachine virtualMachine = null;
					try {
						virtualMachine = VirtualMachine.attach(vmd.id());
						LogFactory.info(" find JVM : " + agentAppMainName);
						LogFactory.info(" agent jar path: "+AgentBootStrap.getAgentJarPath());
						LogFactory.info(" configer file path: "+configFilePath);
						LogFactory.info(" start agent....");
						virtualMachine.loadAgent(AgentBootStrap.getAgentJarPath(), configFilePath);
					} catch (Exception e) {
						LogFactory.error(" Agent error ---->" + e.getMessage());
						e.printStackTrace();
						return ;
					} finally {
						try {
							virtualMachine.detach();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			startScheduler();
		} 
		while(true){
			  try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void startScheduler(){
		SchedulerFactory.getInstance().enableSingleScheduleAtFixedRate("dataSendThread", () -> {
			List<Map<String, Object>> rs = SqliteDataSource
					.executeQuerySQL("select id,startTime,endTime,description from "
							+ PropertyKeys.MONITOR_SQLITE_RECORDSTABLE + " limit 20");
			StringBuilder sb = new StringBuilder();
			try {
				for (Map<String, Object> m : rs) {
					if(!m.isEmpty()){
						Message msg = new Message();
						msg.setDescription(m.get("description").toString());
						msg.setStartTime(new Long(m.get("startTime").toString()));
						msg.setEndTime(new Long(m.get("endTime").toString()));
						msg.setUploadTime(System.currentTimeMillis());
						msg.setDeviceid(PropertiesConfig.getInstance().getStr(PropertyKeys.PI_ID));
						SystemInfo[] values = PI.SystemInfo.values();
						for (SystemInfo in : values) {
							in.getInfo(msg);
						}
						LogFactory.info(" sending mqtt data: " +msg.toString() );
						// 发送成功的id放入删除列表
						if (mqttClient.sendData(msg.getJsonSchema().getBytes())) {
							sb.append("," + m.get("id"));
						}
					}
				}
			} catch (Exception e) {
				LogFactory.error("上传数据失败：" + e.getMessage());
				e.printStackTrace();
			} finally {
				if (sb.length() > 0) {
					SqliteDataSource.executeUpdateSQL("delete from " + PropertyKeys.MONITOR_SQLITE_RECORDSTABLE
							+ " where id in(" + sb.toString().substring(1) + ")");
				}
			}
		});
	}
}
