package com.justbon.lps.entity.origin;

import com.justbon.lps.entity.MetaEntity;

/**
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description
 *              <p>
 * 				app上传的原始log实体, json规范见统一日志设计文档
 *              </p>
 *              <br>
 * 				deviceInfo 设备信息</br>
 *              <br>
 * 				app App客户端信息</br>
 *              <br>
 * 				eventList 批量埋点的具体事件对象</br>
 *              <br>
 * 				sysName 当前系统名称</br>
 *              <br>
 * 				logType 日志类型</br>
 *              <br>
 * 				timestamps 日志产生时间</br>
 *              <br>
 * 				metacode 元数据</br>
 */
public class AppOriginLogEntity {
	// 乙方系统的名称
	private String systemName;
	private String type;
	private String systemeVersion;
	private String clientAddress;
	private AppOriginLogAppInfo app;
	private AppOriginLogDeviceInfo deviceInfo;
	private AppOriginLogUserEvent eventList;
	private MetaEntity metaData;
	private String sysName;
	private String logType;
	private long timestamps;
	private String metaCode;
	
	
	public String getMetaCode() {
		return metaCode;
	}

	public void setMetaCode(String metaCode) {
		this.metaCode = metaCode;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public long getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(long timestamps) {
		this.timestamps = timestamps;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getSystemeVersion() {
		return systemeVersion;
	}

	public void setSystemeVersion(String systemeVersion) {
		this.systemeVersion = systemeVersion;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AppOriginLogDeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(AppOriginLogDeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public AppOriginLogAppInfo getApp() {
		return app;
	}

	public void setApp(AppOriginLogAppInfo app) {
		this.app = app;
	}

	public AppOriginLogUserEvent getEventList() {
		return eventList;
	}

	public void setEventList(AppOriginLogUserEvent eventList) {
		this.eventList = eventList;
	}

	public MetaEntity getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaEntity metaData) {
		this.metaData = metaData;
	}
	
}
