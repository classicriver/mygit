package com.tw.consumer.exception;

import com.tw.consumer.log.LogFactory;
import com.tw.consumer.utils.CommonUtils;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		LogFactory.getLogger().error(CommonUtils.getTimeNow()+" threadName:"+t.getName()+" exception:", e);
		e.printStackTrace();
	}

}
