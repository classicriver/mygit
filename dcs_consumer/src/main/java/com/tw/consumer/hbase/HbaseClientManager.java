package com.tw.consumer.hbase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.hbase.client.Put;
import org.apache.ibatis.reflection.ExceptionUtil;

import com.tw.consumer.core.AutoShutdown;

/**
 * 
 * @author xiesc
 * @TODO htable instance is not thread safe. 使用代理保证htable线程安全
 * @time 2018年8月16日
 * @version 1.0
 */
public class HbaseClientManager implements HbaseClientInterface,AutoShutdown {

	private ThreadLocal<HbaseClient> localHbaseClient = new ThreadLocal<HbaseClient>();
	private final HbaseClientInterface clientProxy;
	private Map<Integer, HbaseClient> pools = new ConcurrentHashMap<>();

	public HbaseClientManager() {
		this.clientProxy = (HbaseClientInterface) Proxy.newProxyInstance(
				HbaseClientInterface.class.getClassLoader(),
				new Class[] { HbaseClientInterface.class },
				new HbaseClientInterceptor());
	}

	@Override
	public void save(Put put) {
		// TODO Auto-generated method stub
		clientProxy.save(put);
	}

	@Override
	public void save(List<Put> data) {
		// TODO Auto-generated method stub
		clientProxy.save(data);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		for (int key : pools.keySet()) {
			pools.get(key).close();
		}
		HbaseClient.closeConnection();
	}

	private class HbaseClientInterceptor implements InvocationHandler {
		
		public HbaseClientInterceptor() {
			// Prevent Synthetic Access
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			HbaseClient client = HbaseClientManager.this.localHbaseClient.get();
			if (client != null) {
				try {
					return method.invoke(client, args);
				} catch (Throwable t) {
					throw ExceptionUtil.unwrapThrowable(t);
				}
			} else {
				client = new HbaseClient();
				try {
					final Object result = method.invoke(client, args);
					HbaseClientManager.this.localHbaseClient.set(client);
					pools.put(client.hashCode(), client);
					return result;
				} catch (Throwable t) {
					throw ExceptionUtil.unwrapThrowable(t);
				}
			}
		}
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		clientProxy.flush();
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		close();
	}
}
