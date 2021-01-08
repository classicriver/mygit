package com.tw.hbasehelper.condition;

import org.apache.hadoop.hbase.client.Get;

public class GetCondition extends Condition<Get>{
	
	private GetBuilder builder;
	
	protected GetCondition(GetBuilder builder){
		this.builder = builder;
	}
	
	public static class GetBuilder extends Condition.Builder<Get>{
		public GetCondition build(){
			return new GetCondition(this);
		}
	}

	@Override
	public Get getCondition() {
		// TODO Auto-generated method stub
		Get get = new Get(builder.getSn().getBytes());
		byte[] family = builder.getFamily().getBytes();
		get.addFamily(family);
		if(null != builder.getColumn() && builder.getColumn().length > 0){
			for(String c : builder.getColumn()){
				get.addColumn(family, c.getBytes());
			}
		}
		return get;
	}
}
