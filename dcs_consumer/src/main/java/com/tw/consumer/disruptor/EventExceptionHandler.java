package com.tw.consumer.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.tw.consumer.log.LogFactory;
import com.tw.consumer.model.OriginMessage;
import com.tw.consumer.utils.CommonUtils;
/**
 * 
 * @author xiesc
 * @TODO 自定义异常
 * @time 2018年5月14日
 * @version 1.0
 */
public class EventExceptionHandler implements ExceptionHandler<OriginMessage> {

	public void handleEventException(Throwable ex, long sequence, OriginMessage event) {
		LogFactory.getLogger().error(CommonUtils.getTimeNow()+" event: ====>"+event.toString()+"<==== \n "+" exception:", ex);
		ex.printStackTrace();
	}

	public void handleOnStartException(Throwable ex) {
		LogFactory.getLogger().error("handleOnStartException", ex);
	}

	public void handleOnShutdownException(Throwable ex) {
		LogFactory.getLogger().error("handleOnShutdownException", ex);
	}

}
