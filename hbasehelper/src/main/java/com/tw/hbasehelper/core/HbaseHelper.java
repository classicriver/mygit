package com.tw.hbasehelper.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.BasicConfigurator;

import com.tw.hbasehelper.utils.Constants;
import com.tw.hbasehelper.utils.FamilyType;

public class HbaseHelper {

	private final HbaseDao service = new HbaseService();
	/**
	 * 设备表
	 */
	private final String deviceTableName = Constants.DEVICETABLENAME;

	/**
	 * @param sn
	 * @param time
	 *            时间戳
	 * @param endTime
	 * @param family
	 *            列簇 遥测or遥信
	 * @param column
	 *            列 （必须先指定列簇）
	 * @return map
	 * e.g. {esnxxx1:[{i1:1,u1:1},{i1:0,u1:1}],esnxxx2:[{i1:1,u1:1},{i1:0,i2:1}]}
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public Map<String, List<Map<String, Object>>> rangeQuery(List<String> sn,
			long startTime, long endTime, FamilyType family, String... column)
			throws Exception {
		// TODO Auto-generated method stub
		return service.rangeQuery(deviceTableName, sn, startTime, endTime,
				family, column);
	}

	/**
	 * @param sn
	 * @param time
	 *            时间戳
	 * @param endTime
	 * @param family
	 *            列簇 遥测or遥信
	 * @param column
	 *            列 （必须先指定列簇）
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public List<Map<String, Object>> rangeQuery(String sn, long startTime,
			long endTime, FamilyType family, String... column) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, List<Map<String, Object>>> rangeQuery = service.rangeQuery(
				deviceTableName, Arrays.asList(sn), startTime, endTime, family,
				column);
		Iterator<String> iterator = rangeQuery.keySet().iterator();
		while (iterator.hasNext()) {
			list = rangeQuery.get(iterator.next());
		}
		return list;
	}

	/**
	 * 
	 * @param data<p>
	 *            {station:xxx,area:xx,subarray:xx,esn:xx,devtype:xxx} <BR/>
	 *            station电站，area区域，subarray子阵,esn设备编码,devtype设备类型。
	 * @throws IOException
	 */
	public void insertOrUpdateDefualt(List<Map<String, Object>> data)
			throws IOException {
		service.saveList(Constants.RELATIONTABLENAME, data, "a".getBytes());
	}

	/**
	 * 
	 * @param esns
	 * @throws IOException
	 */
	public void deleteDefualt(List<String> esns) throws IOException {
		service.deleteList(Constants.RELATIONTABLENAME, esns);
	}

	public static void main(String[] args) {
		BasicConfigurator.configure();
		Map<String,List<Map<String,Object>>> rangeQuery = null;
		try {
			HbaseHelper queryExecutor = new HbaseHelper();
			long startTime = System.currentTimeMillis();
			rangeQuery = queryExecutor
					.rangeQuery(Arrays.asList("TH-H1-1101001", "TH-H1-1101002", "TH-H1-1101003",
							"TH-H1-1101004", "TH-H1-1101005", "TH-H1-1101006",
							"TH-H1-1101007", "TH-H1-1101008", "TH-H1-1101009",
							"TH-H1-1101010"), new Long("1557244800000"),
							new Long("1557331200000"),FamilyType.YC,"piSum");
			long endTime = System.currentTimeMillis();
			//System.out.println(endTime - startTime);
			//System.out.println(rangeQuery.get("TH-H1-1101001"));
			Map<String, Object> map = rangeQuery.get("TH-H1-1101002").get(400);
			Iterator<String> iterator = map.keySet().iterator();
			while(iterator.hasNext()){
				String key = iterator.next();
				System.out.println(map.get(key));
			}
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated e.printStackTrace(); }
			e.printStackTrace();
			System.exit(0);
		}
	}
}
