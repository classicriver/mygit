package com.tw.consumer.device.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import com.tw.consumer.config.Config;
import com.tw.consumer.core.AutoShutdown;
import com.tw.consumer.core.SingleBeanFactory;
import com.tw.consumer.core.ThreadFactoryBean;
import com.tw.consumer.device.handler.DeveiceHandler;
import com.tw.consumer.kfk.producer.KfkAvroProducer;
import com.tw.consumer.kfk.producer.KfkJsonProducer;
import com.tw.consumer.model.GenericDeviceModel;
import com.tw.consumer.phoenix.client.PhoenixDaoFactory;

public class DeveiceExecutor implements AutoShutdown{
	private int producerThreadCount = Config.getInstance().getDeveiceExecutorNum();
	private final ExecutorService executor = ThreadFactoryBean.getFixedThreadPool("kfkproducerThread",producerThreadCount );
	private final LinkedBlockingQueue<GenericDeviceModel> queue;
	private final KfkAvroProducer avroProducer = SingleBeanFactory.getBean(KfkAvroProducer.class);
	private final KfkJsonProducer jsonProducer = SingleBeanFactory.getBean(KfkJsonProducer.class);
	//private final PhoenixDaoFactory phoenixFactory = SingleBeanFactory.getBean(PhoenixDaoFactory.class);
	
	public DeveiceExecutor(LinkedBlockingQueue<GenericDeviceModel> queue){
		this.queue = queue;
	}
	public void start(){
		for(int i=0;i < producerThreadCount;i++){
			executor.submit(new DeveiceHandler(queue,jsonProducer,avroProducer));
		}
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		executor.shutdown();
	}
}
