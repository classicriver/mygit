package com.tw.connect.flink.deprecated;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String TGSN;
	private double IPV2;
	private long TIME;
	
	public String getTGSN() {
		return TGSN;
	}
	public void setTGSN(String tGSN) {
		TGSN = tGSN;
	}
	public double getIPV2() {
		return IPV2;
	}
	public void setIPV2(double iPV2) {
		IPV2 = iPV2;
	}
	public long getTIME() {
		return TIME;
	}
	public void setTIME(long tIME) {
		TIME = tIME;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return TGSN.hashCode();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");
		return "TGSN :" + TGSN+" IPV2 : "+IPV2+" TIME : "+format.format(TIME);
	}
	public static void main(String[] args) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String date = f.format(new Date());
		System.out.println(date);
	}
}
