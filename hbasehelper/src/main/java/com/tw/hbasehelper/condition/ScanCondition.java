package com.tw.hbasehelper.condition;

import org.apache.hadoop.hbase.client.Scan;

import com.tw.hbasehelper.utils.RowKeyHelper;

/**
 * 
 * @author xiesc
 * @TODO hbase查询条件构造类
 * @time 2018年10月18日
 * @version 1.0
 */
public class ScanCondition extends Condition<Scan> {

	private final Scan scan = new Scan();
	private final RowKeyHelper rowKey = new RowKeyHelper();
	private ScanBuilder builder;

	protected ScanCondition(ScanBuilder builder) {
		this.builder = builder;
	}

	public static class ScanBuilder extends Condition.Builder<Scan> {
		public ScanCondition build() {
			return new ScanCondition(this);
		}
	}

	@Override
	public Scan getCondition() {
		// TODO Auto-generated method stub
		// List<Filter> list = new ArrayList<Filter>();
		scan.setStartRow(rowKey.getRowKey(
				String.valueOf(builder.getStartTime()), builder.getSn()));
		scan.setStopRow(rowKey.getRowKey(String.valueOf(builder.getEndTime()),
				builder.getSn()));
		scan.setCaching(1000);
		scan.setBatch(100);
		scan.setCacheBlocks(false);
		// sn过滤
		/*
		 * Filter snFilter = new RowFilter(CompareOp.EQUAL, new
		 * RegexStringComparator(new
		 * StringBuffer(sn).reverse().toString()+".*")); list.add(snFilter);
		 */
		// 时间过滤
		/*
		 * Filter startTimeFilter = new RowFilter(CompareOp.GREATER_OR_EQUAL,
		 * new BinaryPrefixComparator(rowKey.getRowKey(startTime, sn)));
		 * list.add(startTimeFilter); if (0 != endTime) { Filter endTimeFilter =
		 * new RowFilter(CompareOp.LESS_OR_EQUAL, new
		 * BinaryPrefixComparator(rowKey.getRowKey(endTime, sn)));
		 * list.add(endTimeFilter); }
		 */
		// 列簇过滤
		if (null != builder.getFamily()) {
			scan.addFamily(builder.getFamily().getBytes());
			/*
			 * list.add(new FamilyFilter(CompareOp.EQUAL, new BinaryComparator(
			 * familyString.getBytes())));
			 */
			// 列过滤
			if (null != builder.getColumn() && builder.getColumn().length > 0) {
				for (String c : builder.getColumn()) {
					scan.addColumn(builder.getFamily().getBytes(), c.getBytes());
					/*
					 * QualifierFilter qf = new QualifierFilter(CompareOp.EQUAL,
					 * new BinaryComparator(c.getBytes()));
					 */
					// list.add(qf);
				}
			}
		}
		// Operator.MUST_PASS_ONE,表示filter中的查询条件为"或"的关系 类似sql的or
		// Operator.MUST_PASS_ALL,即list中各filter为"并"的关系 类似sql的and
		// scan.setFilter(new FilterList(FilterList.Operator.MUST_PASS_ALL,
		// list));
		return scan;
	}
}
