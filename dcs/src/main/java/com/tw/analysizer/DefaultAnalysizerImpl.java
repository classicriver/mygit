package com.tw.analysizer;


import com.tw.model.Protocol;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年5月17日
 * @version 1.0
 */
public class DefaultAnalysizerImpl extends AbstractAnalysizer{

	@Override
	public String analysize0(Protocol protocol) {
		// TODO Auto-generated method stub
		return protocol.getMessage().toString();
	}


}
