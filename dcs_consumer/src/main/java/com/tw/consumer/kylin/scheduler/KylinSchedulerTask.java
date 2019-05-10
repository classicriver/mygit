package com.tw.consumer.kylin.scheduler;

import java.net.InetAddress;
import java.time.ZoneOffset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tw.consumer.model.Cube;
import com.tw.consumer.utils.CommonUtils;
import com.tw.consumer.zookeeper.watcher.KylinMonitor;

/**
 * 
 * @author xiesc
 * @TODO
 * @time 2019年4月8日
 * @version 1.0
 */
public class KylinSchedulerTask implements Runnable {
	/**
	 * 
	 * @author xiesc
	 * @TODO kylin cube 构建类型
	 * @time 2019年4月9日
	 * @version 1.0
	 */
	public enum BuildType {
		/**
		 * 刷新
		 */
		REFRESH,
		/**
		 * 合并
		 */
		MERGE,
		/**
		 * 构建
		 */
		BUILD
	}

	private KylinMonitor monitor;
	private final KylinRestClient kylinRestClient;
	private final Gson gson = new Gson();
	// kylin server采用的是GMT时区,设置时区为GMT
	private static final ZoneOffset zone = ZoneOffset.ofHours(0);

	public KylinSchedulerTask(KylinMonitor watcher) {
		this.monitor = watcher;
		kylinRestClient = new KylinRestClient();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String remoteIp = monitor.getNodeData();
			String localIp = InetAddress.getLocalHost().getHostAddress()
					.toString();
			if (null == remoteIp) {
				// 极端情况，所有应用和zk的连接都断过，后来又连上了。
				// monitor.createZKNode();
			} else if (localIp.endsWith(remoteIp)) {
				buildCube();
			} else {
				// 如果不是自己的IP，证明自己掉过线，节点已经被别的主机占了，关闭定时调度,注册节点监听
				monitor.registerNodeWatcher();
				monitor.shutdownScheduler();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HttpEntity buildEntity(long startTime, long endTime,
			BuildType buildType) {
		return new StringEntity("{\"startTime\":\"" + startTime
				+ "\",\"endTime\":\"" + endTime + "\",\"buildType\":\""
				+ buildType + "\"}", ContentType.create("application/json",
				"utf-8"));
	}

	private void buildCube() {
		@SuppressWarnings("unchecked")
		List<Cube> cubes = (List<Cube>) gson.fromJson(
				kylinRestClient.getRequest("/cubes"),
				new TypeToken<List<Cube>>() {
				}.getType());
		for (Cube cube : cubes) {
			HttpEntity buildEntity = buildEntity(
					CommonUtils.getZeroTime(-1, zone),
					CommonUtils.getTodayZeroTime(zone), BuildType.BUILD);
			kylinRestClient.putRequest("/cubes/" + cube.getName() + "/build",
					buildEntity);
		}
	}
}
