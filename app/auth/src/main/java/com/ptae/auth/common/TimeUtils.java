package com.ptae.auth.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: TODO()
 * @author  xiesc
 * @date 2017年10月16日 
 * @version V1.0  
 */
public class TimeUtils {
	public static Date  getTodayZeroTime() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String format = sdf.format(new Date());
		return sdf.parse(format);
	}
}
