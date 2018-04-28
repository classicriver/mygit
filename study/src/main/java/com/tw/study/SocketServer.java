/**   
*/ 
package com.tw.study;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月18日 
* @version V1.0   
 */
public class SocketServer {
	
 public static void main(String[] args) {
	try {
		Selector selector = Selector.open();
		ServerSocketChannel server =  ServerSocketChannel.open();
		ServerSocket socket = server.socket();
		socket.bind(new InetSocketAddress("10.19.0.24", 9998));
		server.configureBlocking(false);
		server.register(selector,SelectionKey.OP_ACCEPT);
		Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
		while(iterator.hasNext()) {
			SelectionKey next = iterator.next();
			SelectableChannel channel = next.channel();
			channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
		while(true) {
			SocketChannel accept = server.accept();
			if( null != accept) {
				SocketAddress remoteSocketAddress = accept.socket().getRemoteSocketAddress();
				ByteBuffer b = ByteBuffer.allocate(2);
				accept.read(b);
				System.out.println(b.toString());
				System.out.println(remoteSocketAddress.toString());
			}
			
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
