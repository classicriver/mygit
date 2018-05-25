package com.tw.quartz;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.avro.file.DataFileWriter;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.tw.avro.AvroWriter;
import com.tw.model.Protocol;

@SuppressWarnings("unchecked")
//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
public class AvroWriterJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap jobData = context.getTrigger().getJobDataMap();
		ConcurrentLinkedQueue<String> serializeQueue = (ConcurrentLinkedQueue<String>) jobData
				.get("queue");
		Boolean shutdown = (Boolean) jobData.get("shutdown");
		int size = serializeQueue.size();
		if (size > 0) {
			final AvroWriter writer = new AvroWriter();
			DataFileWriter<Protocol> dataFileWriter = writer.getWriter();
			for (int i = 0; i < size; i++) {
				Protocol.Builder builder = Protocol.newBuilder();
				try {
					builder.setMessage(serializeQueue.poll());
					Protocol user = builder.build();
					dataFileWriter.append(user);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			writer.syncAndClose(dataFileWriter);
		}

		if (shutdown != null) {
			try {
				context.getScheduler().shutdown();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
