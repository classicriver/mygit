/**   
*/
package com.ptae.study;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月18日
 * @version V1.0  
 */
public class SocketClient {
	public static void main(String[] args) {
		SocketChannel sc;
		try {
			Selector selector = Selector.open();
			sc = SocketChannel.open();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
			sc.connect(new InetSocketAddress("10.19.0.24", 9998));
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			//连接建立未成功的逻辑
			while (!sc.finishConnect()) {

			}
			// wait, or do something else...
			ByteBuffer b = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
			b.put(0, (byte) 'H').put(1, (byte) 'H');
			b.flip();
			sc.write(b);
			sc.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
