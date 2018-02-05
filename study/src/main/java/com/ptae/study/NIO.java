/**   
*/ 
package com.ptae.study;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;


/**
 * @Description: TODO
* @author  xiesc
* @date 2017年12月14日 
* @version V1.0   
 */
public class NIO {
	public static void test() {
		ByteBuffer b = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN);
		//ByteBuffer b = ByteBuffer.allocateDirect(2);//使用本地内存的直接缓冲区
		b.put(0,(byte) 'H').put(1,(byte) 'H');
		//ByteBuffer asReadOnlyBuffer = b.asReadOnlyBuffer();//创建只读对象
		//CharBuffer asCharBuffer = b.asCharBuffer();
		//ByteOrder nativeOrder = ByteOrder.nativeOrder();
		//System.out.println(nativeOrder.toString());
		//Channel
		//String encoding = System.getProperty("file.encoding");  
		FileInputStream fin;
		try {
			fin = new FileInputStream("");
			FileChannel channel = fin.getChannel();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		CharBuffer asCharBuffer=Charset.forName("UTF-8").decode(b);//获取char视图缓冲区，处理字符转换
		//byte[] c = new byte[1000];
		//翻转缓冲池，把position指向开头
		//b.flip();
		//b.get(c,0,b.remaining());
		System.out.println(asCharBuffer.toString());
		System.out.println(b.getChar());
	}
	public static void main(String[] args) {
		NIO.test();
	}
	
}
