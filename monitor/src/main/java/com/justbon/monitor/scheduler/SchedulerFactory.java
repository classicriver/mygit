package com.justbon.monitor.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.justbon.monitor.config.PropertiesConfig;
import com.justbon.monitor.constants.PropertyKeys;
import com.justbon.monitor.log.LogFactory;
import com.justbon.monitor.utils.TimeUtils;
/**
 * 
 * @author xiesc
 * @date 2020年4月29日
 * @version 1.0.0
 * @Description: TODO 调度工厂
 */
public class SchedulerFactory {
	
	private static class SingletonSchedulerFactory {
		private static final SchedulerFactory config = new SchedulerFactory();
	}

	public static SchedulerFactory getInstance() {
		return SingletonSchedulerFactory.config;
	}

	private SchedulerFactory() {
		// empty
	}
	//调度延时时间
	int interval = PropertiesConfig.getInstance().getInt(PropertyKeys.MONITOR_AGENT_RECORDERINTERVAL, 1);
	/**
	 * 
	 * @Title: enableSingleScheduleAtFixedRate   
	 * @Description: TODO  开启一个单线程定时调度 
	 * @param: @param threadName
	 * @param: @param runnable
	 * @return:void
	 */
	public void enableSingleScheduleAtFixedRate(String threadName,Runnable runnable){
		singleScheduleExecutorService(threadName).scheduleWithFixedDelay(runnable, 20, interval * 60, TimeUnit.SECONDS);
	}
	
	public ScheduledExecutorService singleScheduleExecutorService(String threadName){
		ScheduledExecutorService singleScheduledThreadPool = Executors.newScheduledThreadPool(1, (r) ->{
			Thread t = new Thread(r);
			t.setName(threadName);
			t.setUncaughtExceptionHandler((th,e) ->{
				e.printStackTrace();
				LogFactory.error(TimeUtils.getTimeNow()+" threadName:"+th.getName()+" exception:", e);
				
			});
			return t;
		});
		return singleScheduledThreadPool;
	}
}
