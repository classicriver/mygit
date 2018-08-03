package com.tw.ddcs.disruptor;

import com.lmax.disruptor.EventFactory;
import com.tw.ddcs.model.Message;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年7月24日
 * @version 1.0
 */
public class MessageEventFactory implements EventFactory<Message>{

	@Override
	public Message newInstance() {
		// TODO Auto-generated method stub
		return new Message();
	}

}
