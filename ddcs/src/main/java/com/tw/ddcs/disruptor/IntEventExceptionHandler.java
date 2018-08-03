package com.tw.ddcs.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.tw.ddcs.log.LogFactory;
import com.tw.ddcs.model.Message;

/**
 * 
 * @author xiesc
 * @TODO 自定义异常
 * @time 2018年5月14日
 * @version 1.0
 */
public class IntEventExceptionHandler implements ExceptionHandler<Message> {

	public void handleEventException(Throwable ex, long sequence, Message event) {
		LogFactory.getLogger().error("handleEventException", ex);
	}

	public void handleOnStartException(Throwable ex) {
		LogFactory.getLogger().error("handleOnStartException", ex);
	}

	public void handleOnShutdownException(Throwable ex) {
		LogFactory.getLogger().error("handleOnShutdownException", ex);
	}


}
