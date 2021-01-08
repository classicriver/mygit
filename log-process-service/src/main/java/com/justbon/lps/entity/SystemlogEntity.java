package com.justbon.lps.entity;
/**
 * 
 * @author xiesc
 * @date 2020年8月14日
 * @version 1.0.0
 * @Description: 系统日志
 */
public class SystemlogEntity {
	
	private String sysName;
	private String logType;
	private String level;
	private String thread;
	private String klass;
	private String message;
	private String stackTrace;
	private Long timestamps;
	
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
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String klass) {
		this.klass = klass;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	public Long getTimestamps() {
		return timestamps;
	}
	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}
}
