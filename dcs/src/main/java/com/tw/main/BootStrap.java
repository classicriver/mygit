package com.tw.main;

import com.tw.server.DisruptorServerImpl;

/**
 * 
 * @author xiesc
 * @TODO 入口函数
 * @time 2018年5月14日
 * @version 1.0
 */
public class BootStrap {
	
	public static void main(String[] args) {
		new DisruptorServerImpl().start();
	}
}
