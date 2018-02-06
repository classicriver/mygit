/**   
*/
package com.ptae.fdfs.cilent;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO fastdfs客户端
 * @author xiesc
 * @date 2018年1月27日
 * @version V1.0  
 */
@Component
public class FDFSCilent {

	private static StorageClient storageClient;
	
	static {
		try {
			ClientGlobal.initByProperties("fastdfs-client.properties");
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 *@param localFileName  本地文件路径
	 *@param fileExtName
	 *@param metaList
	 *@return
	 *@Description: TODO 本地文件上传
	 */
	public String[] uploadFile(String localFileName, String fileExtName, NameValuePair[] metaList) {
		try {
			return storageClient.upload_file(localFileName, fileExtName, metaList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 *@param file_buff
	 *@param file_ext_name
	 *@param meta_list
	 *@return
	 *@Description: TODO 文件流
	 */
	public String[] uploadFile(byte[] file_buff, String file_ext_name,
            NameValuePair[] meta_list) {
		try {
			return storageClient.upload_file(file_buff, file_ext_name, meta_list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 *@param file_buff 
	 *@param offset
	 *@param length
	 *@param file_ext_name
	 *@param meta_list
	 *@return  服务器存储成功后的路径
	 *@Description: TODO 断点续传
	 */
	public String[] uploadFile(byte[] file_buff, int offset, int length, String file_ext_name,
            NameValuePair[] meta_list) {
		try {
			return storageClient.upload_file(file_buff, offset, length, file_ext_name, meta_list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int appendFile(String group_name, String appender_filename,
            byte[] file_buff, int offset, int length) {
		
		try {
			return storageClient.append_file(group_name, appender_filename, file_buff, offset, length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return length;
	}
	
}
