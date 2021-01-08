package com.justbon.lps.entity;

import com.justbon.lps.annotation.JdbcColumnMapping;

/**
 * 
 * @author xiesc
 * @date 2020年8月19日
 * @version 1.0.0
 * @Description: 埋点元数据信息
 */
public class MetaEntity {
	/**
	 * 业务code_页面code_事件code
	 */
	@JdbcColumnMapping("bp_code")
	private String bpCode;
	/**
	 * 埋点名称
	 */
	@JdbcColumnMapping("bp_name")
	private String bpName;
	/**
	 * 级别
	 */
	@JdbcColumnMapping("bp_level")
	private String bpLevel;
	/**
	 * 自定义编码（如00001）
	 */
	@JdbcColumnMapping("biz_code")
	private String bizCode;
	/**
	 * 应用（如嘉饰家）
	 */
	@JdbcColumnMapping("biz_name")
	private String bizName;
	/**
	 * 位置编码（如00001）
	 */
	@JdbcColumnMapping("site_code")
	private String siteCode;
	/**
	 * 位置名称
	 */
	@JdbcColumnMapping("site_name")
	private String siteName;
	/**
	 * 全路径编码（如00001/00002）
	 */
	@JdbcColumnMapping("full_code")
	private String fullCode;
	/**
	 * 全路径名称
	 */
	@JdbcColumnMapping("full_name")
	private String fullName;
	/**
	 * 事件编码（如00001）
	 */
	@JdbcColumnMapping("event_code")
	private String eventCode;
	/**
	 * 事件名称
	 */
	@JdbcColumnMapping("event_name")
	private String eventName;
	
	public String getBpCode() {
		return bpCode;
	}
	public void setBpCode(String bpCode) {
		this.bpCode = bpCode;
	}
	public String getBpName() {
		return bpName;
	}
	public void setBpName(String bpName) {
		this.bpName = bpName;
	}
	public String getBpLevel() {
		return bpLevel;
	}
	public void setBpLevel(String bpLevel) {
		this.bpLevel = bpLevel;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	public String getBizName() {
		return bizName;
	}
	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getFullCode() {
		return fullCode;
	}
	public void setFullCode(String fullCode) {
		this.fullCode = fullCode;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
