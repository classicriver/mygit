package com.tw.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date format = ft.parse("2019-05-08 00:00:00");
		System.out.println(format.getTime());
	}
	
}
