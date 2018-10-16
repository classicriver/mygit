package com.tw.connect.utils;

import java.util.Calendar;

public class Utils {
	public static String getCurrentYearAndMonth(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		return String.valueOf(year)+String.valueOf(month+1);
	}

}
