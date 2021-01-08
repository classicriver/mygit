/**   
*/
package com.ptae.fdfs.cilent;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import org.csource.common.MyException;
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
public class FDFSClient {

	private ReentrantLock lock = new ReentrantLock();
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
	 * @param localFileName
	 *            本地文件路径
	 * @param fileExtName
	 * @param metaList
	 * @return
	 * @Description: TODO 本地文件上传
	 */
	/*
	 * public String[] uploadFile(String localFileName, String fileExtName) { try {
	 * //ClientConfig.initClient(); return
	 * storageClient.upload_appender_file(localFileName, fileExtName, null); } catch
	 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * catch (MyException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return null; }
	 */
	/**
	 * 
	 * @param fileBuff
	 *            文件流
	 * @param fileExtName
	 *            后缀名
	 * @return
	 *         <ul>
	 *         <li>results[0]: the group name to store the file</li>
	 *         </ul>
	 *         <ul>
	 *         <li>results[1]: the new created filename</li>
	 *         </ul>
	 * @Description: TODO
	 */
	public String[] uploadFile(byte[] fileBuff, String fileExtName) {
		// ClientConfig.initClient();
		lock.lock();
		try {
			return storageClient.upload_appender_file(fileBuff, fileExtName, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return null;
	}
	/*
	 * public String[] uploadFile(byte[] file_buff, int offset, int length, String
	 * file_ext_name) { //ClientConfig.initClient(); try { return
	 * storageClient.upload_appender_file(file_buff, offset, length, file_ext_name,
	 * null); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (MyException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return null; }
	 */

	/*
	 * public int appendFile(String group_name, String appender_filename, byte[]
	 * file_buff, int offset, int length) { //ClientConfig.initClient(); try {
	 * return storageClient.append_file(group_name, appender_filename, file_buff,
	 * offset, length); } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (MyException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return -1; }
	 */
	/*
	 * public int appendFile(String groupName, String appenderFileName, String
	 * localFileName) { //ClientConfig.initClient(); try { return
	 * storageClient.append_file(groupName, appenderFileName, localFileName); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (MyException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return -1; }
	 */
	/**
	 * 
	 * @param groupName
	 *            文件groupid
	 * @param appenderFilePath
	 *            服务器文件路径
	 * @param fileBuff
	 *            追加的文件流
	 * @return 0 for success, != 0 for error (error no)
	 * @Description: TODO
	 */
	public int appendFile(String groupName, String appenderFilePath, byte[] fileBuff) {
		// ClientConfig.initClient();
		lock.lock();
		try {
			return storageClient.append_file(groupName, appenderFilePath, fileBuff);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return -1;
	}
}
