package com.tw.consumer.zookeeper.watcher;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.ZoneOffset;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.tw.consumer.config.Config;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.kylin.scheduler.KylinSchedulerTask;
import com.tw.consumer.utils.CommonUtils;
import com.tw.consumer.utils.Constants;

/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年12月13日
 * @version 1.0
 */
public class KylinMonitor implements Watcher {

	private ScheduledExecutorService kylinScheduler;
	private ZooKeeper zk;
	private KylinSchedulerTask scheduler;

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		createOrWatchNode();
	}

	/**
	 * 创建会话级节点，如果节点已被创建就监听节点。
	 */
	public void createOrWatchNode() {
		try {
			Stat exists = zk.exists(Constants.KYLINSCHEDULERPATH, false);
			if (null == exists) {
				// 写入本机ip
				createZKNode();
				kylinScheduler = ThreadFactoryBean.getScheduledThreadPool(
						"KylinSchedulerThread: ", 1);
				scheduler = new KylinSchedulerTask(this);
				long initialDelay = CommonUtils.getZeroTime(1,ZoneOffset
						.ofHours(8)) - System.currentTimeMillis();
				// 开启kylin定时任务，每日零点创建、刷新cube
				kylinScheduler.scheduleWithFixedDelay(scheduler, initialDelay,
						86400 * 1000, TimeUnit.MILLISECONDS);
			} else {
				registerNodeWatcher();
			}
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取node数据
	 * 
	 * @return
	 */
	public String getNodeData() {
		String data = "";
		try {
			data = new String(zk.getData(Constants.KYLINSCHEDULERPATH, false,
					null), "utf-8");
		} catch (UnsupportedEncodingException | KeeperException
				| InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * 关闭定时调度
	 */
	public void shutdownScheduler() {
		kylinScheduler.shutdown();
	}

	/**
	 * 创建zk节点
	 */
	public void createZKNode() {
		try {
			zk.create(Constants.KYLINSCHEDULERPATH, InetAddress.getLocalHost()
					.getHostAddress().toString().getBytes(),
					Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		} catch (UnknownHostException | KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 重新注册监听
	 */
	public void registerNodeWatcher() {
		try {
			zk.getChildren(Constants.KYLINSCHEDULERPARENTPATH, true);
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 初始化zk
	 */
	public void init() {
		try {
			zk = new ZooKeeper(Config.getInstance().getZKServers(), 10000, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	  /*public static void main(String[] args) { 
		  //KylinMonitor watcher = new KylinMonitor();
		  //watcher.init(); System.out.println(watcher);
	  	  //watcher.createOrWatchNode(); 
		  System.out.println(CommonUtils.getZeroTime(1,ZoneOffset
					.ofHours(8)));
	  }*/
	 
}
