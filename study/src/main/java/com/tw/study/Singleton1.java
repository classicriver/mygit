/**   
*/ 
package com.tw.study;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年11月30日 
* @version V1.0   
 */

public class Singleton1 {
	
	private Singleton1(){
		
	}
	
	private static class Singleton1I {
		private static final Singleton1 INSTANCE = new Singleton1();
	}
	
	public static Singleton1 getInstance() {
		return Singleton1I.INSTANCE;
	}
	
	static class Run implements Runnable{
		private Semaphore s ;
		private CountDownLatch c ;
		Run(CountDownLatch count , Semaphore s){
			this.c = count;
			this.s = s;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				s.acquire(1);
				c.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Singleton1.getInstance());
			System.out.println(s.hasQueuedThreads());
			s.release(1);
		}
		
	}
	
  public static void main(String[] args) {
	CountDownLatch c = new CountDownLatch(1);
	Semaphore s = new Semaphore(3);
	ExecutorService pool = Executors.newCachedThreadPool();
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	pool.execute(new Singleton1.Run(c,s));
	c.countDown();
	pool.shutdown();
}
}