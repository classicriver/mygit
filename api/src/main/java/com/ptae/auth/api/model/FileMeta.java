package com.ptae.auth.api.model;

import com.ptae.base.model.Entity;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
public class FileMeta extends Entity{
	/**
	 * 文件的唯一标示码
	 */
	 private String fileId;
	 /**
	  * 用户上传文件的文件名
	  */
	 private String fileName;
	 
	 /**
	  * 用户上传文件的类型
	  */
	 private String fileType;
	 
	 /**
	  * 用户待上传文件的总大小
	  */
	 private int fileSize;
	 
	 /**
	  * 用户已上传文件大小
	  */
	 private int currentFileSize;
	 
	 /**
	  * 用户唯一识别码
	  */
	 private int userId;
	 
	 /**
	  * 总文件数据的MD5值 
	  */
	 private String allMD5;
	 
	 /**
	  * 当前文件数据的MD5值 
	  */
	 private String currentMD5;
	 /**
	  * 文件保存路径 
	  */
	 private String path;
	 /**
	  * 文件保存的分组id
	  */
	 private String groupId;
	 /**
	  * 文件上传时间(yyyy-MM-dd HH:mm:ss)
	  */
	 private String uploadTime;
	 
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileSize
	 */
	public int getFileSize() {
		return fileSize;
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * @return the currentFileSize
	 */
	public int getCurrentFileSize() {
		return currentFileSize;
	}

	/**
	 * @param currentFileSize the currentFileSize to set
	 */
	public void setCurrentFileSize(int currentFileSize) {
		this.currentFileSize = currentFileSize;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the allMD5
	 */
	public String getAllMD5() {
		return allMD5;
	}

	/**
	 * @param allMD5 the allMD5 to set
	 */
	public void setAllMD5(String allMD5) {
		this.allMD5 = allMD5;
	}

	/**
	 * @return the currentMD5
	 */
	public String getCurrentMD5() {
		return currentMD5;
	}

	/**
	 * @param currentMD5 the currentMD5 to set
	 */
	public void setCurrentMD5(String currentMD5) {
		this.currentMD5 = currentMD5;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the uploadTime
	 */
	public String getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

}
