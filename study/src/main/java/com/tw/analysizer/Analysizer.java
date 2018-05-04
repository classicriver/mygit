package com.tw.analysizer;

import java.util.concurrent.BlockingQueue;

public class Analysizer implements Runnable {

	private BlockingQueue<String> queue;
	private Boolean running;

	public Analysizer(BlockingQueue<String> queue, Boolean running) {
		this.queue = queue;
		this.running = running;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (running) {
			try {
				long nextLong = (long) (Math.random() * 1000);
				Thread.sleep(nextLong);
				System.out.println("Analysizer: " + queue.take()
						+ "  ThreadName : " + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
