package com.tw.quartz;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Job;
import org.quartz.ScheduleBuilder;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.tw.config.Config;

/**
 * 
 * @author xiesc
 * @TODO 初始化Quartz
 * @time 2018年5月24日
 * @version 1.0
 */
public abstract class AbstractQuartzScheduler {

	protected static SchedulerFactory schedulerfactory;

	static {
		final Properties pro = new Properties();
		try {
			pro.load(Config.class.getClassLoader().getResourceAsStream(
					"quartz.properties"));
			schedulerfactory = new StdSchedulerFactory(pro);
		} catch (IOException | SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public abstract void startScheduler(String jobName, String triggerName,
			ScheduleBuilder<?> builder, Class<? extends Job> jobClass);
	
}
