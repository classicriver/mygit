package com.tw.consumer.core;
/**
 * 
 * @author xiesc
 * @TODO 自动关闭接口
 * <p>只对被singleBeanFactory加载的Bean有效</p>
 * @time 2018年9月6日
 * @version 1.0
 */
public interface AutoShutdown {
	
	public void shutdown();
	
}
