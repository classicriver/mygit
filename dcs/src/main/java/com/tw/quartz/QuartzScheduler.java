package com.tw.quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.tw.resources.PropertyResources;

/**
 * 
 * @author xiesc
 * @TODO Quartz定时调度
 * @time 2018年5月22日
 * @version 1.0
 */
public class QuartzScheduler extends PropertyResources {

	private Scheduler scheduler;
	private JobDataMap map = new JobDataMap();

	public void setJobData(String key, Object value) {
		map.put(key, value);
	}

	public QuartzScheduler(){
		try {
			SchedulerFactory schedulerfactory = new StdSchedulerFactory(pro);
			scheduler = schedulerfactory.getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startSimpleScheduler(String jobName, String triggerName,
			int second, Class<? extends Job> jobClass) {
		startScheduler(jobName, triggerName,
				SimpleScheduleBuilder.repeatSecondlyForever(second), jobClass);
	}

	public void startScheduler(String jobName, String triggerName,
			ScheduleBuilder<?> builder, Class<? extends Job> jobClass) {
		try {
			// 创建jobDetail实例，绑定Job实现类
			JobDetail job = JobBuilder.newJob(jobClass)
					.withIdentity(jobName, Scheduler.DEFAULT_GROUP).build();

			// 定义调度触发规则
			TriggerBuilder<?> tb = TriggerBuilder.newTrigger()
					.withIdentity(triggerName, Scheduler.DEFAULT_GROUP)
					.withSchedule(builder).startNow();
			// 在trigger上绑定数据
			if (!map.isEmpty()) {
				tb.usingJobData(map);
			}

			scheduler.scheduleJob(job, tb.build());
			// 把作业和触发器注册到任务调度中
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getProFileName() {
		// TODO Auto-generated method stub
		return "quartz.properties";
	}
}
