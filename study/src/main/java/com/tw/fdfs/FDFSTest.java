/**   
*//* 
package com.tw.fdfs;

import java.io.File;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

*//**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月26日 
* @version V1.0   
 *//*
public class FDFSTest {
	static {
		try {
			ClientGlobal.initByProperties("fastdfs-client.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		 TrackerClient trackerClient = new TrackerClient();
         // 4、创建一个TrackerServer对象。
         TrackerServer trackerServer = null;
		try {
			trackerServer = trackerClient.getConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         // 5、声明一个StorageServer对象，null。
         StorageServer storageServer = null;
         // 6、获得StorageClient对象。
         StorageClient storageClient = new StorageClient(trackerServer,  storageServer);
         // 7、直接调用StorageClient对象方法上传文件即可。
         String[] strings = null;
		try {
			strings = storageClient.upload_file("F:\\MVI_2818.mp4", "mp4", null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         for (String string : strings) {
             System.out.println(string);
         }

	}
}
*/