package com.tw.hbasehelper.utils;

import org.apache.hadoop.hbase.util.Bytes;
/**
 * @author xiesc
 * @TODO hbase  rowkey生成类
 * @time 2018年6月8日
 * @version 1.0
 */
public class RowKeyHelper {
	/**
	 * 获取指定时间的rowkey
	 * @param time 格式:yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public byte[] getRowKey(String region,String time,String sn) {
		return getRowKey(region,CommonUtils.timeString2Timestamp(time),sn);
	}
	/**
	 * 生成的rowkey示例：1-1528685037028-sn
	 * <br>1 - salt，
	 * <br>1528685037028 -  时间戳
	 * <br>1551416 - sn序列号，
	 * @return
	 */
	public byte[] getRowKey(String region,long timeMillis,String sn) {
		byte[][] by = new byte[3][];
		by[0] = Bytes.toBytes(region +"-");
		by[1] = Bytes.toBytes(String.valueOf(timeMillis +"-"));
		by[2] = Bytes.toBytes(String.valueOf(sn));
		return  Bytes.add(by);
	}
	
	
}
