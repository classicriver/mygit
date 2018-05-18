package com.tw.analysizer;


import com.tw.model.Protocol;
import com.tw.mq.client.MQClient;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年5月17日
 * @version 1.0
 */
public class DefaultAnalysizerImpl extends AbstractAnalysizer{

	private final MQClient client;
	
	@Override
	public void analysize0(Protocol protocol) {
		// TODO Auto-generated method stub
		client.send(protocol.getMessage());
	}
	
	public DefaultAnalysizerImpl(MQClient client){
		this.client = client;
	}

}
