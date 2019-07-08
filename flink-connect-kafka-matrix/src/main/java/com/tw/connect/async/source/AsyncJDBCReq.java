package com.tw.connect.async.source;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import com.tw.connect.cache.LRUCache;
import com.tw.connect.jdbc.SourceJDBC;
import com.tw.connect.model.Inverter;
/**
 * 
 * @author xiesc
 * @TODO flink jdbc 异步数据源
 * @time 2019年1月17日
 * @version 1.0
 */
public class AsyncJDBCReq extends RichAsyncFunction<Inverter, Inverter> {

	private static final long serialVersionUID = 1L;
	
	private transient SourceJDBC source;
	private transient LRUCache cache;
	//查询子阵idsql
	private transient String matrixSql;
	//查询装机容量sql
	private transient String capacitySql;
	
	@Override
	public void asyncInvoke(Inverter input, ResultFuture<Inverter> resultFuture)
			throws Exception {
		// TODO Auto-generated method stub
		String esn = input.getEsn().toUpperCase();
		input.setEsn(esn);
		Map<String, Object> data = cache.getFromCache(esn);
		if(null == data){
			initCacheData();
			data = cache.getFromCache(esn);
		}
		input.setMatrix_id(data.get("matrix_id").toString());
		input.setRegion_id(data.get("region_id").toString());
		input.setStation_id(data.get("station_id").toString());
		//把装机容量加到每条数据的后面，有效小时数用sum(output_power)/max(installed_capacity)的方式求，避免二次查询数据库
		input.setInstalled_capacity(new BigDecimal(data.get("capacity").toString()));
		resultFuture.complete(Collections.singleton(input));
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		// TODO Auto-generated method stub
		super.open(parameters);
		matrixSql ="SELECT a.id,a.esn,b.node_id as station_id,c.id as region_id,d.id as matrix_id "
				+ "FROM sc_device_inverter a "
				+ "LEFT JOIN sc_device_node b ON  LOCATE(b.path,a.path) and b.node_id is not NULL "
				+ "LEFT JOIN sc_device_node c ON  LOCATE(c.path,a.path) and c.type ='area' "
				+ "LEFT JOIN sc_device_node d ON  LOCATE(d.path,a.path) and d.type ='subarray'";
		capacitySql=" SELECT SUM(i.pv_total) pvTotal,n.parent_id id FROM sc_device_inverter i "
				+ " LEFT JOIN sc_device_node n ON n.path = i.path GROUP BY n.parent_id";
		//初始化缓存
		cache = new LRUCache();
		source = new SourceJDBC();
		initCacheData();
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		super.close();
		//关闭jdbc
		source.close();
	}
	//把装机容量和子阵id加入缓存
	private void initCacheData(){
		List<Map<String, Object>> matrixes = source.getData(matrixSql);
		Map<String, Object> capacities = source.getCapacityAsMap(capacitySql);
		for( Map<String, Object> matrix : matrixes){
			String esn = matrix.get("esn").toString();
			String matrix_id = matrix.get("matrix_id").toString();
			matrix.put("capacity", capacities.get(matrix_id));
			cache.putCache(esn, matrix);
		}
	}
}
