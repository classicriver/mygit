package com.tw.study;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
	private static final Map<String, Object> map = new HashMap<>();

	App() {
		map.put("test", "test");
	}

	public static void main(String[] args) throws InterruptedException {
		// LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		/*
		 * LBQueue<String> queue = new LBQueue<String>(10); queue.put(new
		 * String("test2")); queue.put(new String("test1")); System.out.println();
		 */
		
		// map.put("test", "123");
		// System.out.println(map.get("test"));
		/*Integer i = new Integer(4);
		// 调用 i.toString() 方法，保证 string pool 中没有字符
		// 串
		String s1 = new String(i.toString());
		// 由于此时 string pool 没有 "4" 这个字符串，所以进行
		// “注册”，并将其地址赋值给 s2；其实该地址就是 s1 的
		// 值
		String s2 = s1.intern();
		String s3 = "4";
		System.out.println("s1 == s1.intern() ? " + (s1 == s1.intern()));// true
		System.out.println(s1 == s3);// true
		 */
		char[] cha  = new char[]{1,2,3};
		String s = new String(cha);
		String intern = s.intern();
		System.out.println(s==intern);
	}

	public static String appendStr(String s) {
		s = s + "bbb";
		return s;
	}
}
