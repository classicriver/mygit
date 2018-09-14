package com.tw.hbasehelper.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.tw.hbasehelper.client.HbaseClientManager;
import com.tw.hbasehelper.utils.CommonUtils;
import com.tw.hbasehelper.utils.Constants;
import com.tw.hbasehelper.utils.FamilyType;

public class QueryExecutor implements Query{

	private final static ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "hbasehelper:" + this.atomic.getAndIncrement());
		}
	};
	private final static ExecutorService threadPool = Executors
			.newCachedThreadPool(threadFactory);
	
	private final int threadCount = 10;  //和hbase的region保持一致
	private final CountDownLatch latch = new CountDownLatch(threadCount);

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#query(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> query(String sn, String time)
			throws Exception {
		return this.query(sn, time, null);
	}

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#query(java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType)
	 */
	@Override
	public Map<String, Object> query(String sn, String time, FamilyType family)
			throws Exception {
		return this.query(sn, time, family, new String[] {});
	}

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#query(java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType, java.lang.String)
	 */
	@Override
	public Map<String, Object> query(String sn, String time, FamilyType family,
			String... column) throws Exception{
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

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#query(java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType, java.lang.Class)
	 */
	@Override
	public <T> T query(String sn, String time, FamilyType family, Class<T> clazz)
			throws Exception {
		return CommonUtils.mapToObject(query(sn, time, family), clazz);
	}

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#rangeQuery(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<Map<String, Map<String, Object>>> rangeQuery(String sn,
			String startTime, String endTime) throws Exception{
		return this.execut(sn, startTime, endTime, null, new String[] {});
	}

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#rangeQuery(java.lang.String, java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType)
	 */
	@Override
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family) throws Exception
			 {
		return this.rangeQuery(sn, startTime, endTime, family, new String[] {});
	}

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#rangeQuery(java.lang.String, java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType, java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> rangeQuery(String sn, String startTime,
			String endTime, FamilyType family, String... column)
			throws Exception {
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

	/* (non-Javadoc)
	 * @see com.tw.hbasehelper.core.Query#rangeQuery(java.lang.String, java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType, java.lang.Class)
	 */
	@Override
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
			throws Exception {
		List<Map<String, Map<String, Object>>> result = new ArrayList<Map<String, Map<String, Object>>>();
		final HbaseClientManager client = new HbaseClientManager();
		try{
			CompletionService<List<Map<String, Map<String, Object>>>> future = 
					new ExecutorCompletionService<List<Map<String, Map<String, Object>>>>(threadPool);
			for (int i = 0; i < threadCount; i++) {
				future.submit(new QueryTask(client, new ConditionBuilder(String.valueOf(i),sn, startTime,
						endTime, family, column).build(), latch));
			}
			latch.await(10, TimeUnit.SECONDS);
			for (int i = 0; i < threadCount; i++) {
				result.addAll(future.take().get());
			}
		}finally{
			client.close();
		}
		return result;
	}
	
	public static void main(String[] args) {
			List<Map<String, Object>> rangeQuery = null;
			try {
				rangeQuery = new QueryExecutor().rangeQuery("HG_D28", "2018-9-13 16:00:00", "2018-9-13 16:15:00", FamilyType.YC);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(rangeQuery);
	}
	
}
