package com.tw.phoenixhelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tw.phoenixhelper.executor.Executor;

/**
 * 
 * @author xiesc
 * @TODO
 * @time 2019年1月16日
 * @version 1.0
 */
public class Phoenixhelper {

	private final Executor executor = new Executor();

	public Map<String, List<Map<String, Object>>> query(List<String> esns,
			long startTime, long endTime, String... columns) throws Exception {
		return executor.rangeQuery(esns, startTime, endTime, columns);
	}

	public static void main(String[] args) {

		Phoenixhelper helper = new Phoenixhelper();
		try {
			Map<String, List<Map<String, Object>>> query = helper.query(Arrays
					.asList("RD-N1-0206016"), new Long(
					"1547521800000"), new Long("1547740800000"));
			// System.out.println(query.get("RD-N1-0206016").size());
			long start = System.currentTimeMillis();
			Map<String, List<Map<String, Object>>> q = helper.query(Arrays
					.asList("TH-H1-1101001", "TH-H1-1101002", "TH-H1-1101003",
							"TH-H1-1101004", "TH-H1-1101005", "TH-H1-1101006",
							"TH-H1-1101007", "TH-H1-1101008", "TH-H1-1101009",
							"TH-H1-1101010"), new Long("1547654400000"),
					new Long("1547827200000"),"i1", "i2", "i3", "i4","i5", "i6", "i7", "i8","i9", "i10");
			System.out.println(q.get("TH-H1-1101001").size());
			long end = System.currentTimeMillis();
			System.out.println(end - start);
			System.exit(0);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
