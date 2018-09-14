package com.tw.hbasehelper.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.Scan;

/**
 * @author xiesc
 * @TODO htable instance is not thread safe. 使用代理保证htable线程安全
 * @time 2018年8月16日
 * @version 1.0
 */
public class HbaseClientManager implements HbaseClientInterface {

	private ThreadLocal<HbaseClient> localHbaseClient = new ThreadLocal<HbaseClient>();
	private final HbaseClientInterface clientProxy;

	public HbaseClientManager() {
		this.clientProxy = (HbaseClientInterface) Proxy.newProxyInstance(
				HbaseClientInterface.class.getClassLoader(),
				new Class[] { HbaseClientInterface.class },
				new HbaseClientInterceptor());
	}

	@Override
	public List<Map<String,Map<String,Object>>> query(Scan scan) {
		// TODO Auto-generated method stub
		return clientProxy.query(scan);
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		/*HbaseClient hbaseClient = localHbaseClient.get();
		if (hbaseClient == null) {
			throw new Exception(
					"Error:  Cannot close.  No managed session is started.");
		}
		try {
			hbaseClient.close();
		} finally {
			localHbaseClient.set(null);
		}*/
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
				return method.invoke(client, args);
			} else {
				try {
					client = new HbaseClient();
					final Object result = method.invoke(client, args);
					return result;
				} finally {
					client.close();
				}
			}
		}
	}

}
