package com.tw.consumer.utils;

import java.util.Random;

import org.apache.hadoop.hbase.util.Bytes;
/**
 * 
 * @author xiesc
 * @TODO hbase  rowkey生成类
 * @time 2018年6月8日
 * @version 1.0
 */
public class RowKeyHelper implements RowKeyGenerator {
	/**
	 * @return 根据当前时间获取rowkey
	 */
	public byte[] getRowKey() {
		return getRowKey(System.currentTimeMillis());
	}
	/**
	 * 获取指定时间的rowkey
	 * @param time 格式:yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public byte[] getRowKey(String time) {
		return getRowKey(CommonUtils.timeString2Timestamp(time));
	}
	/**
	 * 
	 * @param time
	 * @param stationId
	 * @param deviceType
	 * @param deviceId
	 * @return
	 */
	/*public byte[] getRowKey(String time,String stationId,DeviceType deviceType,String deviceId) {
		String type = "";
		switch(deviceType){
		case  COMBINER :
			type = Constants.COMBINER;
			break;
		case INVERTER :
			type = Constants.INVERTER;
			break;
		}
		return getRowKey(CommonUtils.timeString2Timestamp(time),stationId,type,deviceId);
	}*/
	
	/**
	 * 生成的rowkey示例：1-1528685037028-001-11-1551416
	 * <br>1 - salt，
	 * <br>1528685037028 时间戳
	 * <br>001 - 电站id，
	 * <br>11 - 设备类型，
	 * <br>1551416 - 设备ID，
	 * 
	 * @return
	 */
	private byte[] getRowKey(Long timeMillis) {
		byte[][] by = new byte[5][];
		Random r= new Random();
		by[0] = Bytes.toBytes(String.valueOf(timeMillis % 9)+"-");
		by[1] = Bytes.toBytes(String.valueOf(timeMillis +"-"));
		by[2] = Bytes.toBytes(String.valueOf(r.nextInt(9))+"-");
		by[3] = Bytes.toBytes(String.valueOf(r.nextInt(9))+"-");
		by[4] = Bytes.toBytes(String.valueOf(r.nextInt(999)));
		return  Bytes.add(by);
	}
}
