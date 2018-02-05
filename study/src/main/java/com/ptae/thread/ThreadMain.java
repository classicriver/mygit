/**   
*/ 
package com.ptae.thread;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月20日 
* @version V1.0   
 */
public class ThreadMain {
	public static void main(String[] args) {
		ThreadTest threadTest = new ThreadTest();
		Thread t = new Thread(threadTest);
		Thread t1 = new Thread(threadTest);
		Thread t2 = new Thread(threadTest);
		t.start();
		t1.start();
		t2.start();
	}
}
