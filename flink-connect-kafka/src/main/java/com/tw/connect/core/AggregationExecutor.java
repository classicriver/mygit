package com.tw.connect.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tw.connect.aggregation.CentralizedInverterAggregation;
/**
 * 
 * @author xiesc
 * @TODO   聚合类执行的线程池
 * @time 2018年10月15日
 * @version 1.0
 */
public class AggregationExecutor {
	
	private final ExecutorService pool;
	private final AbstractAggregation[] aggregations;
	
	public AggregationExecutor(){
		aggregations = registerAggregations();
		pool = Executors.newFixedThreadPool(aggregations.length);
	}
	
	/**
	 * 注册
	 * @return
	 */
	private AbstractAggregation[] registerAggregations(){
		Class<?>[] cls = {CentralizedInverterAggregation.class};
		AbstractAggregation[] aggs = new AbstractAggregation[cls.length];
		for(int i = 0 ; i< cls.length; i++){
			try {
				AbstractAggregation agg = (AbstractAggregation) cls[i].newInstance();
				aggs[i] = agg;
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return aggs;
	}
	/**
	 * 执行
	 */
	public void start(){
		for(AbstractAggregation agg : aggregations){
			pool.execute(agg);
		}
	}
	
	public static void main(String[] args) {
		new AggregationExecutor().start();
	}
}
