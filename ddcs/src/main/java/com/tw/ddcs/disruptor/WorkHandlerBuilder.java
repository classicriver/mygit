package com.tw.ddcs.disruptor;

import com.tw.ddcs.db.core.DaoFactory;
/**
 * 
 * @author xiesc
 * @TODO
 * @time 2018年7月24日
 * @version 1.0
 */
public class WorkHandlerBuilder {
	
	private static final DaoFactory daoFactory = new DaoFactory();
	
	public static MessageWorkHandler[] build(int count) {
		MessageWorkHandler[] handlers = new MessageWorkHandler[count];
		for (int i = 0; i < count; i++) {
			handlers[i] = new MessageWorkHandler(daoFactory);
		}
		return handlers;
	}
	
}
