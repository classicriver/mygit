package com.justbon.oms.flink.function;

import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

import com.justbon.oms.flink.state.ShopStateCalculate;
import com.justbon.oms.model.Shop;

public class ShopCountFunction extends RichFlatMapFunction<Shop, Shop> {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1511131491000648152L;

	private transient ValueState<ShopStateCalculate> countState;

	@Override
	public void flatMap(Shop in, Collector<Shop> out) throws Exception {
		// TODO Auto-generated method stub
		// access the state in
		ShopStateCalculate lastState = countState.value();
		// 第一次进入计算,更新state,输入原样返回
		if (lastState == null) {
			// 初始化state
			lastState = new ShopStateCalculate(in.getShopName(), in.getSales());
			// 进来的数据原样返回
			out.collect(in);
			// 更新state
			countState.update(lastState);
		} else {
			// count 累加
			lastState.setSalesCount(lastState.getSalesCount() + in.getSales());
			out.collect(in);
			// 更新state
			countState.update(lastState);
			System.out.println("shopName: "+ lastState.getShopName()+" shopCount: "+lastState.getSalesCount());
		}
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		// TODO Auto-generated method stub
		ValueStateDescriptor<ShopStateCalculate> descriptor = new ValueStateDescriptor<>(
				// the state name
				"countState",
				// type information
				TypeInformation.of(new TypeHint<ShopStateCalculate>() {
				}));
		countState = getRuntimeContext().getState(descriptor);

	}

}
