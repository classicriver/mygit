package com.justbon.oms.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author xiesc
 * @date 2019年11月8日
 * @version 1.0.0
 * @Description: TODO
 */
public class Shop {
	
	private String shopName;
	private int sales;
	private long time;
	

	public String getShopName() {
		return shopName;
	}


	public void setShopName(String shopName) {
		this.shopName = shopName;
	}


	public int getSales() {
		return sales;
	}


	public void setSales(int sales) {
		this.sales = sales;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	@Override
	public String toString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = format.format(new Date(time));
		return "商店: "+shopName+" 销售额："+sales+" 最后销售时间："+date;
	}
	
}
