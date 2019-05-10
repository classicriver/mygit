package com.tw.coprocessor.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tw.es.client.EsJestClient;
import com.tw.es.dao.JestDao;
import com.tw.es.dao.JestDaoImpl;
import com.tw.es.entity.RowKeyEntity;

public class Test implements Runnable{

	private JestDao esDao;
	private java.util.Random r = new java.util.Random();
	
	public Test(JestDao esDao){
		this.esDao = esDao;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 0;
		while(i < 100000){
			long timestamps = System.currentTimeMillis();
			RowKeyEntity entity = new RowKeyEntity(); 
			int nextInt = r.nextInt(9);
			entity.setEsn("TH-N2-"+nextInt+"90100"+nextInt);
			entity.setTimestamps(timestamps);
			entity.setRowkey(nextInt+"577-TH-N2-090100"+nextInt+"-"+timestamps);
			esDao.insert(entity);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		EsJestClient esClient = new EsJestClient(true);
		JestDao  esDao = new JestDaoImpl(esClient);
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		for(int i = 0 ;i <= 99;i++){
			newCachedThreadPool.submit(new Test(esDao));
		}
		
	}
}
