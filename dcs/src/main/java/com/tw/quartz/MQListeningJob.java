package com.tw.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import com.tw.avro.AvroReader;
import com.tw.common.utils.Constants;
import com.tw.mq.client.RocketMQClientImpl;
//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
public class MQListeningJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		RocketMQClientImpl client = (RocketMQClientImpl) jobDataMap
				.get("MQClientImpl");
		//检查MQ服务器状态
		if (client.mqServerIsUp()) {
			// 设置MQ状态为上线状态
			client.setIsDown(false);
			// 设置定时任务关闭标识
			jobDataMap.put("shutdown", true);
			// 手动触发一次序列化，把queue中缓存的消息保存到本地。
			try {
				context.getScheduler().triggerJob(
						new JobKey(Constants.AVROSCHEDULERJOBNAME), jobDataMap);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 读取本地已序列化的avro数据批量发送到mq
			final AvroReader reader = new AvroReader();
			client.bathSend(reader.readSerializeFileAsList());
		}
	}

}
