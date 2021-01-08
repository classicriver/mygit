/**   
*/ 
package com.tw.nio;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月27日 
* @version V1.0   
 */
public class ThreadPool {
	
	private static ThreadPoolExecutor pool;
	
	public static ThreadPoolExecutor getPool() {
		//pool = new ThreadPoolExecutor(min, max, time, unit, queue);
		pool = new ThreadPoolExecutor(0, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		return pool;
	}
}
