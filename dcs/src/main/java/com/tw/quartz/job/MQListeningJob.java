package com.tw.quartz.job;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.tw.avro.AvroReader;
import com.tw.common.utils.Utils;
import com.tw.log.LogFactory;
import com.tw.mq.producer.MQProducer;
/**
 * 
 * @author xiesc
 * @TODO	MQ服务器上线监听类
 * @time 2018年5月29日
 * @version 1.0
 */
//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
@SuppressWarnings("unchecked")
public class MQListeningJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap jobDataMap = context.getTrigger().getJobDataMap();
		ConcurrentLinkedQueue<String> serializeQueue = (ConcurrentLinkedQueue<String>) jobDataMap
				.get("queue");
		MQProducer producer = (MQProducer) jobDataMap.get("MQProducer");
		//检查MQ服务器状态
		if (producer.mqServerIsUp()) {
			LogFactory.getLogger().warn("----> "+Utils.getDateString()+" MQ is online,starting deserialization local data.");
			// 设置MQ状态为上线状态
			producer.setIsDown(false);
			/*（手动触发可能会在当前调度执行完之后执行，会导致queue的数据在AvroReader已经反序列化完成之后再被写入到磁盘上）
			try {
				context.getScheduler().triggerJob(
						new JobKey(Constants.AVROSCHEDULERJOBNAME), jobDataMap);
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			//先发送queue中缓存的消息
			int size = serializeQueue.size();
			for(int i = 0; i< size;i++){
				producer.send(serializeQueue.poll());
			}
			//读取本地已序列化的avro数据批量发送到mq
			final AvroReader reader = new AvroReader();
			producer.send(reader.readSerializeFileAsList());
			//关闭调度
			try {
				context.getScheduler().shutdown();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
			LogFactory.getLogger().warn("----> "+Utils.getDateString()+" deserialization local data success,shutdown scheduler.");
		}
	}

}
