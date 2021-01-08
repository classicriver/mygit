package com.tw.consumer.main;

import com.tw.consumer.server.Server;

public class BootStrap {
	
	public static void main(String[] args) {
		new Server().start();
	}
}
