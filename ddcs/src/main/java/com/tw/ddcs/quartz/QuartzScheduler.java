package com.tw.ddcs.quartz;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.tw.ddcs.resources.PropertyResources;


/**
 * 
 * @author xiesc
 * @TODO Quartz定时调度
 * @time 2018年5月22日
 * @version 1.0
 */
public class QuartzScheduler extends PropertyResources {

	private Scheduler scheduler;

	private QuartzScheduler() {
		try {
			scheduler = new StdSchedulerFactory(pro).getScheduler();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static QuartzScheduler getInstance() {
		return SingletonScheduler.quartz;
	}

	private static class SingletonScheduler {
		private static final QuartzScheduler quartz = new QuartzScheduler();
	}
	/**
	 * 创建不带参数的秒级重复job
	 * @param jobName
	 * @param triggerName
	 * @param second
	 * @param jobClass
	 */
	public void createRepeatSecondScheduler(String jobName, String triggerName,
			int second, Class<? extends Job> jobClass) {
		createRepeatSecondScheduler(jobName, triggerName, second, null, jobClass);
	}
	/**
	 * 创建带参数的秒级重复job
	 * @param jobName
	 * @param triggerName
	 * @param second
	 * @param map
	 * @param jobClass
	 */
	public void createRepeatSecondScheduler(String jobName, String triggerName,
			int second, JobDataMap map, Class<? extends Job> jobClass) {
		createScheduler(jobName, triggerName,
				SimpleScheduleBuilder.repeatSecondlyForever(second), map,
				jobClass);
	}
	/**
	 * 自定义job
	 * @param jobName
	 * @param triggerName
	 * @param builder
	 * @param map
	 * @param jobClass
	 */
	public void createScheduler(String jobName, String triggerName,
			ScheduleBuilder<?> builder, JobDataMap map,
			Class<? extends Job> jobClass) {
		try {
			// 创建jobDetail实例，绑定Job实现类
			JobDetail job = JobBuilder.newJob(jobClass)
					.withIdentity(jobName, Scheduler.DEFAULT_GROUP).build();

			// 定义调度触发规则
			TriggerBuilder<?> tb = TriggerBuilder.newTrigger()
					.withIdentity(triggerName, Scheduler.DEFAULT_GROUP)
					.withSchedule(builder).startNow();
			// 在trigger上绑定数据
			if (null != map) {
				tb.usingJobData(map);
			}

			scheduler.scheduleJob(job, tb.build());
			// 把作业和触发器注册到任务调度中
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 关闭调度器
	 */
	public void shutdown(){
		try {
			this.scheduler.shutdown();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 删除job
	 * @param jobName
	 * @param triggerName
	 */
	public void removeJob(String jobName, String triggerName) {  
        try {  
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,Scheduler.DEFAULT_GROUP);
            scheduler.pauseTrigger(triggerKey);// 停止触发器  
            scheduler.unscheduleJob(triggerKey);// 移除触发器  
            scheduler.deleteJob(JobKey.jobKey(jobName, Scheduler.DEFAULT_GROUP));// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 

	@Override
	public String getProFileName() {
		// TODO Auto-generated method stub
		return "quartz.properties";
	}
}
