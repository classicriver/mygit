package com.tw.ddcs.main;

import java.io.IOException;

import com.tw.ddcs.server.Server;
import com.tw.ddcs.server.impl.DefaultServerImpl;
/**
 * 
 * @author xiesc
 * @TODO 启动类
 * @time 2018年8月2日
 * @version 1.0
 */
public class BootStrap {
	
	public static void main(String[] args) {
		
		Server server = null;
		try{
			server = new DefaultServerImpl();
			server.start();
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}catch(Exception e){
			e.printStackTrace();
			server.close();
		}
	}
}	
