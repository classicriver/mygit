package com.justbon.lps.entity;

import java.util.Map;

import com.justbon.lps.annotation.Nested;

public class OperlogEntity {
	
	private Long timestamps;
	private String sysName;
	private String metaCode;
	private String funName;
	private String logType;
	private String fun;
	private String browserName;
	private String browserVersion;
	private String operatingSystemName;
	private String operatingSystemVersion;
	private Long receivingTime;
	private Map<String,String> customdata;
	@Nested
	private UserInfo userInfo;
	@Nested
	private AccessInfo accessInfo;
	private Map<String,String> metaData;
	
	public Long getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	
	public String getMetaCode() {
		return metaCode;
	}
	public void setMetaCode(String metaCode) {
		this.metaCode = metaCode;
	}
	public String getFunName() {
		return funName;
	}
	public void setFunName(String funName) {
		this.funName = funName;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getFun() {
		return fun;
	}
	public void setFun(String fun) {
		this.fun = fun;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public AccessInfo getAccessInfo() {
		return accessInfo;
	}
	public void setAccessInfo(AccessInfo accessInfo) {
		this.accessInfo = accessInfo;
	}
	public Map<String, String> getCustomdata() {
		return customdata;
	}
	public void setCustomdata(Map<String, String> customdata) {
		this.customdata = customdata;
	}
	public Map<String,String> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String,String> metaData) {
		this.metaData = metaData;
	}
	public String getBrowserName() {
		return browserName;
	}
	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getOperatingSystemName() {
		return operatingSystemName;
	}
	public void setOperatingSystemName(String operatingSystemName) {
		this.operatingSystemName = operatingSystemName;
	}
	public String getOperatingSystemVersion() {
		return operatingSystemVersion;
	}
	public void setOperatingSystemVersion(String operatingSystemVersion) {
		this.operatingSystemVersion = operatingSystemVersion;
	}
	public Long getReceivingTime() {
		return receivingTime;
	}
	public void setReceivingTime(Long receivingTime) {
		this.receivingTime = receivingTime;
	}
}
