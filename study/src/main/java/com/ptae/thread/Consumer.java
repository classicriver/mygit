/**   
*/ 
package com.ptae.thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月22日 
* @version V1.0   
 */
public class Consumer implements Runnable{
	
	//private Thread t ;
	private BlockingQueue<Integer> quee;
	
	public Consumer(BlockingQueue<Integer> quee) {
		this.quee = quee;
		//t= new Thread(this);
		//t.start();
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while(true) {
				int reulst = quee.take().intValue();
				System.out.println("Consumer : "+reulst);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
