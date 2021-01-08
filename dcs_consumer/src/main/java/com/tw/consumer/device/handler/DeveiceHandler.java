package com.tw.consumer.device.handler;

import java.util.concurrent.LinkedBlockingQueue;

import com.tw.consumer.device.dao.Dao;
import com.tw.consumer.model.GenericDeviceModel;

public class DeveiceHandler implements Runnable{

	private final LinkedBlockingQueue<GenericDeviceModel> queue;
	private final Dao<GenericDeviceModel>[] daos;
	
	@SafeVarargs
	public DeveiceHandler(LinkedBlockingQueue<GenericDeviceModel> queue,Dao<GenericDeviceModel>... daos){
		this.queue = queue;
		this.daos = daos;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				GenericDeviceModel data = queue.take();
				for(Dao<GenericDeviceModel> d : daos){
					d.add(data);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}
}
