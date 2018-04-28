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
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月26日
 * @version V1.0  
 */
public class TCPServer implements Runnable {

	private boolean isOpen = true;
	private Selector select;
	private ServerSocketChannel server;
	private static ExecutorService pool;

	private int port;

	public TCPServer(int port) {
		this.port = port;
	}

	public void startServer() throws IOException {
		// 开启selector
		select = Selector.open();
		// 开启nio socket server
		server = ServerSocketChannel.open();
		// 设置为non blocking
		server.configureBlocking(false);
		// 绑定ip和端口
		server.socket().bind(new InetSocketAddress(port));
		// 注册selector事件
		server.register(select, SelectionKey.OP_ACCEPT);
	}

	public void stop() throws IOException {
		isOpen = false;
		server.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		try {
			startServer();
			while (isOpen) {
				//查询就绪的通道数量
                int readyChannels = select.select();
                //没有就绪的则继续进行循环
                if(readyChannels == 0)
                    continue;
              //获得就绪的selectionKey的set集合
                Set<SelectionKey> keys = select.selectedKeys();
                //获得set集合的迭代器
                Iterator<SelectionKey> iterator = keys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()) {
                        //该key有ACCEPT事件
                        //将监听得到的channel强转为ServerSocketChannel
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        //得到接收到的SocketChannel
                        SocketChannel socketChannel = server.accept();
                        if(socketChannel != null) {
                            System.out.println("收到了来自" + ((InetSocketAddress)socketChannel.getRemoteAddress()).getHostString()
                                    + "的请求");
                            //将socketChannel设置为阻塞模式
                            socketChannel.configureBlocking(false);
                            //将socketChannel注册到选择器 
                            socketChannel.register(select, SelectionKey.OP_READ);
                        }
                    } else if (key.isReadable()) {
                        //该key有Read事件
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        String requestHeader = "";
                        //拿出通道中的Http头请求
                        try {
                           // requestHeader = receive(socketChannel);
                        } catch (Exception e) {
                            System.out.println("读取socketChannel出错");
                            return;
                        }
                        //启动线程处理该请求,if条件判断一下，防止心跳包
                        if(requestHeader.length() > 0) {
                            //logger.info("该请求的头格式为\r\n" + requestHeader);
                            //logger.info("启动了子线程..");
                        	pool.execute(new HandlClient());
                        }
                    } else if (key.isWritable()) {
                        //该key有Write事件
                        System.out.println("");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.shutdownInput();
                        socketChannel.close();
                    }
                    //从key集合中删除key，这一步很重要，就是因为没写这句，Selector.select()方法一直返回的是0
                    //原因分析可能是不从集合中删除，就不会回到I/O就绪事件中
                    iterator.remove();
                }
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class HandlClient implements Runnable {
		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}

	public static void main(String[] args) {
		pool = Executors.newCachedThreadPool();
		TCPServer tcpServer = new TCPServer(8889);
		// 线程池开启server线程
		pool.execute(tcpServer);
		System.out.println();
		// 线程池开启客户端处理线程
		//pool.execute(tcpServer.new HandlClient());
	}
}
