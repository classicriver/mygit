package com.tw.connect.async.source;

import java.util.Collections;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import com.tw.connect.cache.LRUCache;
import com.tw.connect.jdbc.SourceJDBC;
import com.tw.connect.model.CascadeModel;
/**
 * 
 * @author xiesc
 * @TODO flink jdbc 异步数据源
 * @time 2019年1月17日
 * @version 1.0
 */
public class AsyncJDBCReq extends RichAsyncFunction<CascadeModel, CascadeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient SourceJDBC source;
	private transient LRUCache cache;
	
	
	@Override
	public void asyncInvoke(CascadeModel input, ResultFuture<CascadeModel> resultFuture)
			throws Exception {
		// TODO Auto-generated method stub
		String esn = input.getEsn();
		String path = cache.getFromCache(esn);
		if(null == path){
			source.initCache(cache);
			input.setPath(cache.getFromCache(esn));
		}else{
			input.setPath(path);
		}
		resultFuture.complete(Collections.singleton(input));
	}

	@Override
	public void open(Configuration parameters) throws Exception {
		// TODO Auto-generated method stub
		super.open(parameters);
		//初始化缓存
		cache = new LRUCache();
		source = new SourceJDBC();
		source.initCache(cache);
		
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		super.close();
		//关闭jdbc
		source.close();
	}
	
}
