package com.tw.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.tw.log.LogFactory;
import com.tw.model.Protocol;

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
