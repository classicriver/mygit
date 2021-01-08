package com.tw.hbasehelper.condition;

import com.tw.hbasehelper.utils.FamilyType;

public abstract class Condition<T> {
	
	public abstract T getCondition();
	
	public abstract static class Builder<T>{
		/**
		 * sn
		 */
		private String sn;
		/**
		 * 遥测、遥信
		 */
		private String family;
		/**
		 * 字段
		 */
		private String[] column;
		/**
		 * 开始时间
		 */
		private long startTime;
		/**
		 * 结束时间
		 */
		private long endTime;
		
		public String getSn() {
			return sn;
		}

		public String getFamily() {
			return family;
		}

		public String[] getColumn() {
			return column;
		}

		public Builder<T> sn(String sn){
			this.sn = sn;
			return this;
		}
		
		public Builder<T> family(FamilyType family){
			switch (family){
			case YX :
				this.family = "yx";
				break;
			default:
				this.family = "yc";
				break;
			}
			return this;
		}
		
		public Builder<T> column(String... column){
			this.column = column;
			return this;
		}
		
		public long getStartTime() {
			return startTime;
		}

		public Builder<T> startTime(long startTime) {
			this.startTime = startTime;
			return this;
		}

		public long getEndTime() {
			return endTime;
		}

		public Builder<T> endTime(long endTime) {
			this.endTime = endTime;
			return this;
		}
		
		public abstract Condition<T> build();
	}
}
