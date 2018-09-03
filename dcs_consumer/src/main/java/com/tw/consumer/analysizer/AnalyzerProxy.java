package com.tw.consumer.analysizer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.model.MMessage;
/**
 * 
 * @author xiesc
 * @TODO 解析器代理类
 * @time 2018年9月3日
 * @version 1.0
 */
public class AnalyzerProxy implements Analyzer {

	private final static Analyzer analyzerRdbms = SingleBeanFactory.getBean(AnalyzerRdbms.class);
	private final static Analyzer analyzerNosql = SingleBeanFactory.getBean(AnalyzerNosql.class);
	private Analyzer analyzerProxy;

	public AnalyzerProxy() {
		this.analyzerProxy = (Analyzer) Proxy
				.newProxyInstance(Analyzer.class.getClassLoader(),
						new Class[] { Analyzer.class },
						new AnalyzerInvocationHandler());
	}

	@Override
	public String analysizeAndSave(MMessage message) {
		// TODO Auto-generated method stub
		return analyzerProxy.analysizeAndSave(message);
	}

	private class AnalyzerInvocationHandler implements InvocationHandler {

		public AnalyzerInvocationHandler() {
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			// TODO Auto-generated method stub
			MMessage msg = (MMessage) args[0];
			if("".equals("")){
				return method.invoke(analyzerRdbms, args);
			}else{
				return method.invoke(analyzerNosql, args);
			}
		}
	}

}
