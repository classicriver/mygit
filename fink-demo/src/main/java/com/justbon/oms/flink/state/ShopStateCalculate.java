package com.justbon.oms.flink.state;

import java.io.Serializable;

public class ShopStateCalculate implements Serializable{

	/**   
	 * @Fields serialVersionUID : TODO  
	 */ 
	private static final long serialVersionUID = -8484222320410836281L;
	
	private String shopName;
	private int salesCount;
	
	public ShopStateCalculate(String shopName, int sales) {
		// TODO Auto-generated constructor stub
		this.shopName = shopName;
		this.salesCount = sales;
	}
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getSalesCount() {
		return salesCount;
	}
	public void setSalesCount(int salesCount) {
		this.salesCount = salesCount;
	}

}
