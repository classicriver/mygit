package com.tw.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
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
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(format.parse(time));
			return getRowKey(calendar.getTimeInMillis());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param time  格式:yyyy-MM-dd hh:mm:ss
	 * @param stationId 电站id
	 * @return
	 */
	/*public static byte[] getRowKey(String time, String stationId) {
		return null;
	}*/
	/**
	 * 生成的rowkey示例：100-1528685037028-11-1551416
	 * <br>100 - 电站id(001反转)，
	 * <br>1528685037028 时间戳
	 * <br>11 - 设备类型，
	 * <br>1551416 - 设备ID，
	 * 
	 * @return
	 */
	private byte[] getRowKey(Long timeMillis) {
		byte[][] by = new byte[4][];
		Random r= new Random();
		/*by[0] = Bytes.toBytes(MD5Hash.getMD5AsHex(
				Bytes.toBytes(Long.hashCode(timeMillis) % 8)).substring(
				0, 5)+"-");*/
		//by[0] = Bytes.toBytes((byte)Long.hashCode(timeMillis) % 20 +"-");
		
		//by[1] = Bytes.toBytes(String.valueOf(timeMillis)+"-");
		
		by[0] = Bytes.toBytes(String.valueOf(timeMillis % 99)+"-");
		by[1] =  Bytes.toBytes(String.valueOf(timeMillis +"-"));
		by[2] = Bytes.toBytes(String.valueOf(r.nextInt(999))+"-");
		by[3] = Bytes.toBytes(String.valueOf(r.nextInt(9999999)));
		return  Bytes.add(by);
	}
	

	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		System.out.println(currentTimeMillis % 99);
		//String test = "9223370508177987453";
		//System.out.println(new String(new RowKeyHelper().getRowKey(currentTimeMillis)));
	}
}
