package com.tw.consumer.analysizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.tw.consumer.model.OriginMessage;
/**
 * 
 * @author xiesc
 * @TODO 解析器代理类
 * @time 2018年9月3日
 * @version 1.0
 */
public class AnalyzerProxy implements Analyzer {

	private final static Analyzer analyzerYanHua = new AnalyzerYanHua();
	private Analyzer analyzerProxy;

	public AnalyzerProxy() {
		this.analyzerProxy = (Analyzer) Proxy
				.newProxyInstance(Analyzer.class.getClassLoader(),
						new Class[] { Analyzer.class },
						new AnalyzerInvocationHandler());
	}

	@Override
	public void analysize(OriginMessage message) {
		// TODO Auto-generated method stub
		analyzerProxy.analysize(message);
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
