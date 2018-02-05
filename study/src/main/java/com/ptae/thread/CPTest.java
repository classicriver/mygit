/**   
*/ 
package com.ptae.thread;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月22日 
* @version V1.0   
 */
public class CPTest {
	private static ArrayBlockingQueue<Integer> quee = new ArrayBlockingQueue<>(50);
	public static void main(String[] args) {
		//ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 10, 50000l, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>() );
		//ExecutorService pool = Executors.newCachedThreadPool();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		//Future<?> submit = pool.submit(new Producer(quee));
		//Future<?> submit2 = pool.submit(new Consumer(quee));
		//HashSet
		pool.execute(new Consumer(quee));
		pool.execute(new Producer(quee));
		//pool.execute(new Consumer(quee));
		
		pool.shutdown();
	}
}
