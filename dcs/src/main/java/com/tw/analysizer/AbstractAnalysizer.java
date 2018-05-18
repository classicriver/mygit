package com.tw.analysizer;

import org.apache.commons.logging.LogFactory;

import com.tw.model.Protocol;

public abstract class AbstractAnalysizer implements Analysizer{

	@Override
	public void analysize(Protocol protocol) {
		// TODO Auto-generated method stub
		analysize0(protocol);
		//LogFactory.getLog(AbstractAnalysizer.class).info(protocol.getMessage());
	}
	
	public abstract void analysize0(Protocol protocol);
	
}
