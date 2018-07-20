/**   
 */
package com.tw.thread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

/**
 * @Description: TODO
 * @author xiesc
 * @date 2017年12月20日
 * @version V1.0  
 */
public class ThreadMain {

	/*
	 * 其实讲到这里该问题的解决办法已然清晰明了了——就是在删除索引文件的同时还取消对应的内存映射，删除mapped对象。
	 * 不过令人遗憾的是，Java并没有特别好的解决方案——令人有些惊讶的是，Java没有为MappedByteBuffer提供unmap的方法，
	 * 该方法甚至要等到Java 10才会被引入 ,DirectByteBufferR类是不是一个公有类 class DirectByteBufferR
	 * extends DirectByteBuffer implements DirectBuffer 使用默认访问修饰符
	 * 不过Java倒是提供了内部的“临时”解决方案——DirectByteBufferR.cleaner().clean() 切记这只是临时方法，
	 * 毕竟该类在Java9中就正式被隐藏了，而且也不是所有JVM厂商都有这个类。
	 * 还有一个解决办法就是显式调用System.gc()，让gc赶在cache失效前就进行回收。
	 * 不过坦率地说，这个方法弊端更多：首先显式调用GC是强烈不被推荐使用的，
	 * 其次很多生产环境甚至禁用了显式GC调用，所以这个办法最终没有被当做这个bug的解决方案。
	 */
	/*
	 * public static void clean(final MappedByteBuffer buffer) throws Exception
	 * { if (buffer == null) { return; } buffer.force();
	 * AccessController.doPrivileged(new PrivilegedAction<Object>()
	 * {//Privileged特权
	 * 
	 * @Override public Object run() { try { //
	 * System.out.println(buffer.getClass().getName()); Method getCleanerMethod
	 * = buffer.getClass().getMethod("cleaner", new Class[0]);
	 * getCleanerMethod.setAccessible(true); sun.misc.Cleaner cleaner =
	 * (sun.misc.Cleaner) getCleanerMethod.invoke(buffer, new Object[0]);
	 * cleaner.clean(); } catch (Exception e) { e.printStackTrace(); } return
	 * null; } });
	 */
	/*
	 * 
	 * 在MyEclipse中编写Java代码时，用到了Cleaner，import sun.misc.Cleaner;可是Eclipse提示：
	 * Access restriction: The type Cleaner is not accessible due to restriction
	 * on required library *\rt.jar Access restriction : The constructor
	 * Cleaner() is not accessible due to restriction on required library
	 * *\rt.jar
	 * 
	 * 解决方案1（推荐）： 只需要在project build path中先移除JRE System Library，再添加库JRE System
	 * Library，重新编译后就一切正常了。 解决方案2： Windows -> Preferences -> Java -> Compiler ->
	 * Errors/Warnings -> Deprecated and trstricted API -> Forbidden reference
	 * (access rules): -> change to warning
	 */

	public static void mappedIO(){
		long start = System.currentTimeMillis();
		try {
			/*Path path = Paths.get("E:\\noted\\pdfs\\HADOOP3.zip");
			FileChannel file = FileChannel.open(path);*/
			FileInputStream fis = new FileInputStream("E:\\noted\\pdfs\\HADOOP3.zip");
			FileChannel file = fis.getChannel();
			int size = (int) file.size();
			ByteBuffer byteBuf = ByteBuffer.allocate(size);
			/*MappedByteBuffer map = file.map(FileChannel.MapMode.READ_ONLY, 0
			, size);
			byte[] bytes = new byte[size];
			map.get(bytes, 0, size);
			map.array();
			System.out.println(new String(bytes,"GB2312"));
			unmap(map);*/
			file.read(byteBuf);
			byteBuf.array();
			file.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("------>time : " + (end -start));
	}
	
	
	public static void file(){
		long start = System.currentTimeMillis();
		File file = new File("E:\\noted\\pdfs\\HADOOP3.zip");
		BufferedInputStream bis;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			long size = bis.available();
			
			int len = 0;
			int allocate = 1024;
			byte[] eachBytes = new byte[allocate];
			while((len = bis.read(eachBytes)) != -1) {
				//System.out.print(new String(eachBytes, 0, len));
			}
			
			bis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long end = System.currentTimeMillis();
		//System.out.println(String.format("\n===>文件大小：%s 字节", size));
		System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
	}
	
	public static void main(String[] args) {
		ThreadMain.mappedIO();
	}
	
	
	
	/**
	 * 清理内存，解决映射有时无法被回收的问题
	 * @param paramMappedByteBuffer
	 */
	private static void unmap(MappedByteBuffer paramMappedByteBuffer) {
		Cleaner localCleaner = ((DirectBuffer) paramMappedByteBuffer).cleaner();
		if (localCleaner != null) {
			localCleaner.clean();
		}
	}
}
