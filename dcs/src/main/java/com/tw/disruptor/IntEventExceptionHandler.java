package com.tw.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.tw.log.LogFactory;
import com.tw.model.Protocol;
/**
 * 
 * @author xiesc
 * @TODO 自定义异常
 * @time 2018年5月14日
 * @version 1.0
 */
public class IntEventExceptionHandler implements ExceptionHandler<Protocol> {

	public void handleEventException(Throwable ex, long sequence, Protocol event) {
		LogFactory.getLogger(this.getClass()).error("handleEventException", ex);
	}

	public void handleOnStartException(Throwable ex) {
		LogFactory.getLogger(this.getClass()).error("handleOnStartException", ex);
	}

	public void handleOnShutdownException(Throwable ex) {
		LogFactory.getLogger(this.getClass()).error("handleOnShutdownException", ex);
	}


}
