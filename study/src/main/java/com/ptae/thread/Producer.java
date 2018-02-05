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
public class Producer implements Runnable{
	
	//private Thread t ;
	private BlockingQueue<Integer> quee;
	
	public Producer(BlockingQueue<Integer> quee){
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
		for(int x = 0 ; ;x++) {
			try {
				//Thread.sleep(1000);
				quee.put(new Integer(x));
				System.out.println("Produced : "+x);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
