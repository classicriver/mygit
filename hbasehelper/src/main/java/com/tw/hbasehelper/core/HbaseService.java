package com.tw.hbasehelper.core;

import java.io.IOException;
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

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import com.tw.hbasehelper.client.HbaseClient;
import com.tw.hbasehelper.condition.ScanCondition;
import com.tw.hbasehelper.config.HbaseConfig;
import com.tw.hbasehelper.utils.FamilyType;

class HbaseService implements HbaseDao {

	private final HbaseConfig config = new HbaseConfig();
	private final static ThreadFactory threadFactory = new ThreadFactory() {
		private AtomicInteger atomic = new AtomicInteger();

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "hbasehelper:" + this.atomic.getAndIncrement());
		}
	};
	private final static ExecutorService threadPool = Executors
			.newCachedThreadPool(threadFactory);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tw.hbasehelper.core.Query#rangeQuery(java.lang.String,
	 * java.lang.String, java.lang.String, com.tw.hbasehelper.utils.FamilyType,
	 * java.lang.String)
	 */
	@Override
	public Map<String, List<Map<String, Object>>> rangeQuery(String tableName,
			List<String> sns, long startTime, long endTime, FamilyType family,
			String... column) throws Exception {
		return this.execut(tableName, sns, startTime, endTime, family, column);
	}

	private Map<String, List<Map<String, Object>>> execut(String tableName,
			List<String> snList, long startTime, long endTime,
			FamilyType family, String[] column) throws Exception {
		Map<String, List<Map<String, Object>>> result = new HashMap<>();
		CompletionService<Map<String, List<Map<String, Object>>>> future = new ExecutorCompletionService<>(
				threadPool);
		CountDownLatch latch = new CountDownLatch(snList.size());
		for (int i = 0; i < snList.size(); i++) {
			HbaseClient client = new HbaseClient(config.getConnection(),tableName);
			String esn = snList.get(i);
			Scan scanner = new ScanCondition.ScanBuilder().startTime(startTime)
					.endTime(endTime).sn(esn).column(column).family(family)
					.build().getCondition();
			future.submit(new ScanTask(client, esn, scanner, latch));
		}
		latch.await(10, TimeUnit.SECONDS);
		for (int i = 0; i < snList.size(); i++) {
			result.putAll(future.take().get());
		}
		//config.closeConnection();
		//threadPool.shutdown();
		return result;
	}

	/*
	 * public Map<String,List<Map<String, Object>>> get() throws
	 * InterruptedException, ExecutionException{ List<String> esns =
	 * Arrays.asList("TH-H1-1101001", "TH-H1-1101002", "TH-H1-1101003",
	 * "TH-H1-1101004", "TH-H1-1101005", "TH-H1-1101006", "TH-H1-1101007",
	 * "TH-H1-1101008", "TH-H1-1101009", "TH-H1-1101010"); Map<String,
	 * List<RowKeyEntity>> rowkeySearch = dao.rowkeySearch(threadPool, esns,
	 * 1545753600000L , 1545926400000L); client.close(); Map<String,
	 * List<Map<String, Object>>> result = new HashMap<>();
	 * CompletionService<Map<String, List<Map<String, Object>>>> future = new
	 * ExecutorCompletionService<>( threadPool); CountDownLatch latch = new
	 * CountDownLatch(rowkeySearch.size()); Iterator<String> iterator =
	 * rowkeySearch.keySet().iterator(); //long startTime =
	 * System.currentTimeMillis(); while(iterator.hasNext()){ String sn =
	 * iterator.next(); List<RowKeyEntity> list = rowkeySearch.get(sn);
	 * List<Get> getList = new ArrayList<>(); HbaseClient hbaseClient = new
	 * HbaseClient(config.getConnection(), "kktest"); for(RowKeyEntity en :
	 * list){ Condition<Get> build = new
	 * GetCondition.GetBuilder().family(FamilyType
	 * .YC).sn(en.getRowkey()).column(
	 * "uab","ubc","uca","ua","ub","uc","ia","ib",
	 * "ic","i1","i2","i3","i4","u1","u2"
	 * ,"u3","u4","temperature","power_factor",
	 * "line_frequency","active_power","reactive_power"
	 * ,"output_power_day","output_power_month"
	 * ,"total_output_power","rated_reactive_power"
	 * ,"run_minute_day","dc_input_total_power"
	 * ,"rated_total_power","total_run_time"
	 * ,"total_apparent_power","realpower_limited_feedback"
	 * ,"eactive_power_feedback","power_factor_feedback").build(); Get condition
	 * = build.getCondition(); getList.add(condition); } future.submit(new
	 * GetTask(hbaseClient,sn,getList,latch)); }
	 * 
	 * latch.await(10, TimeUnit.SECONDS); for (int i = 0; i <
	 * rowkeySearch.size(); i++) { result.putAll(future.take().get()); }
	 * threadPool.shutdown(); long endTime = System.currentTimeMillis();
	 * System.out.println("es time: " + (endTime - startTime)); return result;
	 * 
	 * }
	 */

	@Override
	public void saveList(String tableName, List<Map<String, Object>> data,
			byte[] family) throws IOException {
		List<Put> list = new ArrayList<Put>();
		for (Map<String, Object> map : data) {
			Put put = new Put(map.get("esn").toString().getBytes());
			mapTransverter(map, put, family);
			list.add(put);
		}
		HbaseClient client = new HbaseClient(config.getConnection(), tableName);
		client.save(list);
		client.close();
	}

	private void mapTransverter(Map<String, Object> map, Put put, byte[] family) {
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = String.valueOf(map.get(key));
			if (null != value && !"".equals(value)) {
				put.addColumn(family, Bytes.toBytes(key), Bytes.toBytes(value));
			}
		}
	}

	@Override
	public void deleteList(String tableName, List<String> keys)
			throws IOException {
		// TODO Auto-generated method stub
		List<Delete> deletes = new ArrayList<Delete>();
		for (String key : keys) {
			deletes.add(new Delete(key.getBytes()));
		}
		HbaseClient client = new HbaseClient(config.getConnection(), tableName);
		client.delete(deletes);
		client.close();
	}

}
