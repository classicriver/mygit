package com.tw.consumer.utils;

import org.apache.hadoop.hbase.util.Bytes;

/**
 * @author xiesc
 * @TODO hbase  rowkey生成类
 * @time 2018年6月8日
 * @version 1.0
 */
public class RowKeyHelper implements RowKeyGenerator {

	/**
	 * 生成的rowkey示例：3577-TH-N2-0901001-1541552700000
	 * <br>esn - ，
	 * <br>1528685037028 -  时间戳
	 * @return
	 */
	public byte[] getRowKey(String timeMillis,String sn) {
		StringBuffer s = new StringBuffer();
		//esn的hashcode取模 ，保证rowkey的随机
		s.append(lpad(4,String.valueOf(Math.abs(sn.hashCode()) % 9999))+"-");
		s.append(sn+"-");
		s.append(timeMillis);
		return  Bytes.toBytes(s.toString());
	}
	
	/**
     * 补齐不足长度
     * @param length 长度
     * @param value  模值 
     * @return
     */
    public String lpad(int length, String code) {
    	if(length-code.length() == 0){
    		return code;
    	}
        return code+String.format("%1$0"+(length-code.length())+"d",0);
    }
}
