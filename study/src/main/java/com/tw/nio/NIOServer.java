/**   
*/ 
package com.tw.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.tw.nio.Server.Reader;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年2月23日 
* @version V1.0   
 */
public class NIOServer {
	//线程池
	private static Executor pools = Executors.newCachedThreadPool();
	
	volatile boolean running = true;
	
	
	//写线程
	private int writeThread = 1;
	
	private class Listener extends Thread{
		//监听selector
		Selector listenerSelector; 
		//读线程
		int readThread = 10;
		
		Reader[] readers;
		
		int robin;
		
		Listener() throws IOException{
			
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(10011), 150);
            listenerSelector = Selector.open();
            serverChannel.register(listenerSelector, SelectionKey.OP_ACCEPT);
            readThread = 10;
            readers = new Reader[readThread];
            for(int i = 0; i < readThread; i++) {
                readers[i] = new Reader();
                pools.execute(readers[i]);
            }
            
		}
		
		 public void run() {
			 while(running) {
				 try {
					 listenerSelector.select();
	                    Iterator<SelectionKey> it = listenerSelector.selectedKeys().iterator();
	                    while(it.hasNext()) {
	                        SelectionKey key = it.next();
	                        it.remove();
	                        if(key.isValid()) {
	                            if(key.isAcceptable()) {
	                               
	                            }
	                        }
	                    }
	                } catch (IOException e) {
	                   	e.printStackTrace();
	                } 
			 }
		 }
		 
		 public Reader getReader() {
	            if(robin == Integer.MAX_VALUE) {
	            	robin = 0;
	            }
	            return readers[(robin ++) % readThread];
	        }
	}
	
	//读
	private class Reader implements Runnable{
		//读selector
		private Selector readSelector;
		
		Reader() throws IOException{
			this.readSelector = Selector.open();
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		public SelectionKey registerChannel(SocketChannel channel) throws IOException {
            return channel.register(readSelector, SelectionKey.OP_READ);
        }
	}
	
	//写
	private class Writer implements Runnable{
		//写selector
		private Selector writeSelector;
		
		Writer() throws IOException{
			this.writeSelector = Selector.open();
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
		public SelectionKey registerChannel(SocketChannel channel) throws IOException {
            return channel.register(writeSelector, SelectionKey.OP_WRITE);
        }
	}
	
	public static void main(String[] args) {
		
	}
}
