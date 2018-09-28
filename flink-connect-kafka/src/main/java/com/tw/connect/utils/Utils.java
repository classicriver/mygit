package com.tw.connect.utils;

import java.util.Calendar;

public class Utils {
	public static String getCurrentMonth(){
		int i = Calendar.getInstance().get(Calendar.MONTH);
		return String.valueOf(i+1);
	}

}
