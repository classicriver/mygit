/**   
*/ 
package com.tw.thread;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月20日 
* @version V1.0   
 */
public class ThreadTest implements Runnable{
	private AtomicInteger i = new AtomicInteger();
	//private volatile Integer i = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition newCondition = lock.newCondition();
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public  int  getI() throws InterruptedException {
		
		try{
			//newCondition.await();
			//newCondition.signal();
			//lock.lock();
			//ReadWriteLock
			//ScheduledThreadPoolExecutor
			//Semaphore a = new Semaphore(0);
			//newCondition.awaitNanos(1000000000);
			return i.getAndIncrement();
			//return i++;
		}
		finally {
			//lock.unlock();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			System.out.println(getI());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
