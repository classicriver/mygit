package com.tw.hbasehelper.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.hbase.client.Scan;

import com.tw.hbasehelper.client.HbaseClientManager;
import com.tw.hbasehelper.utils.CommonUtils;
import com.tw.hbasehelper.utils.Constants;
import com.tw.hbasehelper.utils.FamilyType;

//此类必须设置成单例
public class HbaseHelper {

	private final ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "hbasehelper:" + this.atomic.getAndIncrement());
		}
	};
	private final int threadCount = 10;  //和hbase的region保持一致
	private final ExecutorService threadPool = Executors
			.newCachedThreadPool(threadFactory);

	private final HbaseClientManager client = new HbaseClientManager();
	private final CountDownLatch latch = new CountDownLatch(threadCount);

	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time)
			throws InterruptedException, ExecutionException {
		return this.query(sn, time, null);
	}

	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            列簇 遥测or遥信
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time, FamilyType family)
			throws InterruptedException, ExecutionException {
		return this.query(sn, time, family, new String[] {});
	}

	/**
	 * @param sn
	 *            设备sn号
	 * @param time
	 *            时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            列簇 遥测or遥信
	 * @param column
	 *            列 （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, Object> query(String sn, String time, FamilyType family,
			String... column) throws InterruptedException, ExecutionException {
		List<Map<String, Map<String, Object>>> result = execut(sn, time, time,
				family, column);
		if (result.size() > 0) {
			switch (family) {
			case YC:
				return result.get(0).get(Constants.YC);
			case YX:
				return result.get(0).get(Constants.YX);
			default:
				break;
			}
		}
		return new HashMap<String, Object>();
	}

	/**
	 * 
	 * @param sn
	 * @param time
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param family
	 *            遥测or遥信
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T> T query(String sn, String time, FamilyType family, Class<T> clazz)
			throws Exception {
		return CommonUtils.mapToObject(query(sn, time, family), clazz);
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @return 嵌套 map 格式{yx : {ipv1 : 1},yc:{e1 : 1}}
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Map<String, Object>>> rangeQuery(String sn,
			String startTime, String endTime) throws InterruptedException,
			ExecutionException {
		return this.execut(sn, startTime, endTime, null, new String[] {});
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family
	 *            遥测or遥信
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family) throws InterruptedException,
			ExecutionException {
		return this.rangeQuery(sn, startTime, endTime, family, new String[] {});
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family 列簇
	 *            遥测or遥信
	 * @param column 
	 *            列   （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family, String... column)
			throws InterruptedException, ExecutionException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Map<String, Object>>> execut = this.execut(sn,
				startTime, endTime, family, column);
		for (Map<String, Map<String, Object>> familyMap : execut) {
			Iterator<String> it = familyMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				result.add(familyMap.get(key));
			}
		}
		return result;
	}

	/**
	 * @param sn
	 * @param startTime
	 *            字符串时间格式 yyyy-MM-dd HH:mm:ss
	 * @param endTime
	 * @param family
	 *            遥测or遥信
	 * @param clazz
	 *            需要转换的类对象
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> rangeQuery(String sn, String startTime, String endTime,
			FamilyType family, Class<T> clazz) throws Exception {
		List<T> list = new ArrayList<T>();
		List<Map<String, Object>> rangeQuery = rangeQuery(sn, startTime,
				endTime, family);
		for (Map<String, Object> map : rangeQuery) {
			list.add(CommonUtils.mapToObject(map, clazz));
		}
		return list;
	}

	private List<Map<String, Map<String, Object>>> execut(String sn,
			String startTime, String endTime, FamilyType family, String[] column)
			throws InterruptedException, ExecutionException {
		List<Map<String, Map<String, Object>>> result = new ArrayList<Map<String, Map<String, Object>>>();
		CompletionService<List<Map<String, Map<String, Object>>>> future = 
				new ExecutorCompletionService<List<Map<String, Map<String, Object>>>>(threadPool);
		for (int i = 0; i < threadCount; i++) {
			future.submit(new QueryTask(client, scanBuilder(String.valueOf(i),sn, startTime,
					endTime, family, column), latch));
		}
		latch.await(10, TimeUnit.SECONDS);
		for (int i = 0; i < threadCount; i++) {
			result.addAll(future.take().get());
		}
		return result;
	}

	private Scan scanBuilder(String region,String sn, String startTime, String endTime,
			FamilyType family, String[] column) {
		return new ConditionBuilder(region,sn,startTime,endTime,family,column).build();
	}
	
	public static void main(String[] args) {
		try {
			List<Map<String, Object>> rangeQuery = new HbaseHelper().rangeQuery("HG_D28", "2018-9-13 16:00:00", "2018-9-13 16:15:00", FamilyType.YC);
			System.out.println(rangeQuery);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
