package com.tw.consumer.zookeeper.watcher;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.hadoop.hbase.executor.EventType;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.proto.WatcherEvent;

public class ZookeeperTest implements Watcher{
	static CountDownLatch l = new CountDownLatch(1);
	static ZooKeeper zk ;

	@Override
	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
		System.out.println(event.getType().getIntValue());
		try {
			System.out.println("------------->"+new String(zk.getData("/zk-book/test", true,new Stat())));
			WatcherEvent wrapper = event.getWrapper();
		} catch (KeeperException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			zk = new ZooKeeper("172.20.90.55:2181", 10000, new ZookeeperTest());
			zk.create("/zk-book/test", "testdata".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			Stat s = new Stat();
			zk.getData("/zk-book/test", true, s);
			zk.getChildren("/zk-book/test", new ZookeeperTest());
			zk.getChildren("/zk-book/test", new ZookeeperTest());
			zk.delete("/zk-book/test", -1);
			//zk.setData("/zk-book/test", "123".getBytes(), -1);
			System.out.println(zk.getState());
		} catch (IOException | KeeperException | InterruptedException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			l.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
