package com.tw.consumer.hbase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.hbase.client.Put;
import org.apache.ibatis.reflection.ExceptionUtil;

/**
 * 
 * @author xiesc
 * @TODO htable instance is not thread safe. 使用代理保证htable线程安全
 * @time 2018年8月16日
 * @version 1.0
 */
public class HbaseClientManager implements HbaseClientInterface {

	private ThreadLocal<HbaseClient> localHbaseClient = new ThreadLocal<HbaseClient>();
	private final HbaseClientInterface clientProxy;
	private Map<Integer, HbaseClient> pools = new ConcurrentHashMap<>();

	private HbaseClientManager() {
		this.clientProxy = (HbaseClientInterface) Proxy.newProxyInstance(
				HbaseClientInterface.class.getClassLoader(),
				new Class[] { HbaseClientInterface.class },
				new HbaseClientInterceptor());
	}

	public static HbaseClientManager getInstance() {
		return SingletonManager.MANAGER;
	}

	private static class SingletonManager {
		private static final HbaseClientManager MANAGER = new HbaseClientManager();
	}

	@Override
	public void save(byte[] data) {
		// TODO Auto-generated method stub
		clientProxy.save(data);
	}

	@Override
	public void save(List<Put> data) {
		// TODO Auto-generated method stub
		clientProxy.save(data);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		/*final HbaseClient hbaseClient = localHbaseClient.get();
	    if (hbaseClient == null) {
	      throw new SqlSessionException("Error:  Cannot close.  No managed session is started.");
	    }
	    try {
	    	hbaseClient.close();
	    } finally {
	    	localHbaseClient.set(null);
	    }*/
		for (int key : pools.keySet()) {
			pools.get(key).close();;
		}
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
}
