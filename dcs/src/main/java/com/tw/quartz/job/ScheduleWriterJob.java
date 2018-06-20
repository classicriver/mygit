package com.tw.quartz.job;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.tw.avro.AvroWriter;

/**
 * 
 * @author xiesc
 * @TODO MQ断线后采用avro序列化到本地保存
 * @time 2018年5月31日
 * @version 1.0
 */
@SuppressWarnings("unchecked")
//设置@DisallowConcurrentExecution以后程序会等上个任务执行完毕以后再开始执行，可以保证任务不会并发执行
@DisallowConcurrentExecution
public class ScheduleWriterJob implements Job {

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		JobDataMap jobData = context.getTrigger().getJobDataMap();
		ConcurrentLinkedQueue<String> serializeQueue = (ConcurrentLinkedQueue<String>) jobData
				.get("queue");
		int size = serializeQueue.size();
		if (size > 0) {
			final AvroWriter writer = new AvroWriter();
			DataFileWriter<GenericRecord> dataFileWriter = writer.getWriter();
			for (int i = 0; i < size; i++) {
				/*Message message = serializeQueue.poll();
				GenericRecord genericMessage = new GenericData.Record(writer.getSchema()); 
				genericMessage.put("topic", message.getTopic());
				genericMessage.put("flag", message.getFlag());
				genericMessage.put("properties", message.getProperties());
				//avro只支持bytebuffer的数组
				genericMessage.put("body", ByteBuffer.wrap(message.getBody()));*/
				GenericRecord genericMessage = new GenericData.Record(writer.getSchema()); 
				genericMessage.put("message", serializeQueue.poll());
				try {
					dataFileWriter.append(genericMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			writer.syncAndClose(dataFileWriter);
		}
	}
}
