package com.tw.consumer.analysizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.model.OriginMessage;
/**
 * 
 * @author xiesc
 * @TODO 解析器代理类
 * @time 2018年9月3日
 * @version 1.0
 */
public class AnalyzerProxy implements Analyzer {

	private Analyzer analyzerProxy;
	private Semaphore semaphore;
	private final Analyzer analyzerYanHua;

	public AnalyzerProxy(Semaphore semaphore,final LinkedBlockingQueue<GenericDeviceModel> queue) {
		analyzerYanHua = new AnalyzerYanHua(queue);
		this.semaphore = semaphore;
		this.analyzerProxy = (Analyzer) Proxy
				.newProxyInstance(Analyzer.class.getClassLoader(),
						new Class[] { Analyzer.class },
						new AnalyzerInvocationHandler());
	}

	@Override
	public void analysize(OriginMessage message) {
		// TODO Auto-generated method stub
		analyzerProxy.analysize(message);
		semaphore.release();
	}

	private class AnalyzerInvocationHandler implements InvocationHandler {

		public AnalyzerInvocationHandler() {
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			return method.invoke(analyzerYanHua, args);
			
		}
	}

}
