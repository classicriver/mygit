/**   
*/ 
package com.tw.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月22日 
* @version V1.0   
 */
public class CPTest implements Runnable {
	//private static ArrayBlockingQueue<Integer> quee = new ArrayBlockingQueue<>(50);
	private static List<String> l = new LinkedList<>();
	private static List<String> synchronizedList = Collections.synchronizedList(l);
	private static final AtomicLong c = new AtomicLong();
	private CountDownLatch co;
	
	public CPTest(CountDownLatch co){
		this.co = co ;
	}
	
	public static void main(String[] args) {
		CountDownLatch co = new CountDownLatch(10);
		CPTest cpTest = new CPTest(co);
		long start = System.currentTimeMillis();
		//ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 50000l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>() );
		//ExecutorService pool = Executors.newCachedThreadPool();
		ExecutorService pool = Executors.newFixedThreadPool(10);
		//Future<?> submit = pool.submit(new Producer(quee));
		//Future<?> submit2 = pool.submit(new Consumer(quee));
		//HashSet
		//pool.execute(new Consumer(quee));
		//pool.execute(new Producer(quee));
		//pool.execute(new Consumer(quee));
		for(int i = 0 ;i< 10;i++){
			pool.submit(cpTest);
			//System.out.println(c.incrementAndGet());
			
		}
		try {
			co.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end-start);
		pool.shutdown();
		//pool.shutdown();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 0 ;i< 100000;i++){
			synchronizedList.add(String.valueOf(i));
			/*synchronized (synchronizedList) {
				System.out.println(synchronizedList.size());
			}*/
			System.out.println(c.incrementAndGet());
		}
		co.countDown();
		synchronizedList.clear();
	}
}
