package com.tw.hbasehelper.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryPrefixComparator;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FamilyFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.QualifierFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;

import com.tw.hbasehelper.utils.Constants;
import com.tw.hbasehelper.utils.FamilyType;
import com.tw.hbasehelper.utils.RowKeyHelper;

public class ConditionBuilder {

	private final String region;
	private final String sn;
	private final String startTime;
	private final String endTime;
	private final FamilyType family;
	private final String[] column;
	private final Scan scan = new Scan();
	private final RowKeyHelper rowKey = new RowKeyHelper();
	/**
	 * @param region hbase region分区号
	 * @param sn   
	 * @param startTime   开始时间
	 * @param endTime	结束时间
	 * @param family 列簇
	 * @param column 列
	 */
	public ConditionBuilder(String region, String sn, String startTime,
			String endTime, FamilyType family, String[] column) {
		this.region = region;
		this.sn = sn;
		this.startTime = startTime;
		this.endTime = endTime;
		this.family = family;
		this.column = column;
	}

	public Scan build() {
		List<Filter> list = new ArrayList<Filter>();
		//时间过滤
		Filter startTimeFilter = new RowFilter(
				CompareFilter.CompareOp.GREATER_OR_EQUAL,
				new BinaryPrefixComparator(rowKey.getRowKey(region, startTime,
						sn)));
		list.add(startTimeFilter);
		if (null != endTime) {
			Filter endTimeFilter = new RowFilter(
					CompareFilter.CompareOp.LESS_OR_EQUAL,
					new BinaryPrefixComparator(rowKey.getRowKey(region,
							endTime, sn)));
			list.add(endTimeFilter);
		}
		//sn过滤
		Filter snFilter = new RowFilter(CompareOp.EQUAL,
				new RegexStringComparator(".*" + sn));
		list.add(snFilter);
		//列簇过滤
		if (null != family) {
			String familyString = null;
			FamilyFilter ff = null;
			switch (family) {
			case YC:
				familyString = Constants.YC;
				ff = new FamilyFilter(CompareOp.EQUAL, new BinaryComparator(
						familyString.getBytes()));
				break;
			case YX:
				familyString = Constants.YX;
				ff = new FamilyFilter(CompareOp.EQUAL, new BinaryComparator(
						familyString.getBytes()));
				break;
			default:
				break;
			}
			list.add(ff);
			//列过滤
			if (null != column && column.length > 0) {
				for (String c : column) {
					QualifierFilter qf = new QualifierFilter(CompareOp.EQUAL,
							new BinaryComparator(c.getBytes()));
					list.add(qf);
				}
			}
		}
		// Operator.MUST_PASS_ONE,表示filter中的查询条件为"或"的关系 类似sql的or
		// Operator.MUST_PASS_ALL,即list中各filter为"并"的关系 类似sql的and
		scan.setFilter(new FilterList(FilterList.Operator.MUST_PASS_ALL, list));
		return scan;
	}
}
