package com.tw.kylinhelper.condition;
/**
 * 
 * @author xiesc
 * @TODO 条件基类，带分页
 * @time 2019年4月2日
 * @version 1.0
 */
public class BaseCondition {
	private int limit;
	private int offset;
	
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
}
