package com.justbon.lps.entity;

import java.util.List;

import com.justbon.lps.annotation.Nested;
import com.justbon.lps.entity.origin.AppOriginLogUserEventExtendData;

/**
 * @author xiesc
 * @date 2020年8月5日
 * @version 1.0.0
 * @Description: 行为日志Elasticsearch存储实体
 */
public class BehalogElasticsearchEntity{

	private String rowkey;
	private String deviceId;
	private String deviceOS;
	private String deviceVersion;
	private String deviceBrand;
	private String deviceModel;
	private String deviceLongitude;
	private String deviceLatitude;
	private String deviceNetworkType;
	private String appType;
	private String appVersion;
	private String userId;
	private String userName;
	private String userMobile;
	private String userSex;
	private Integer userAge;
	private String userProjectId;
	private String userProjectName;
	private Long userRegisterTime;
	private Long userBindingTime;
	private String action;
	private String fromFunctionCode;
	private String toFunctionCode;
	private Long startTime;
	private Long endTime;
	private Long lengthOfTime;
	@Nested
	private List<AppOriginLogUserEventExtendData> data;
	// "1920*1080", 
	private String resolution;
	// 1527368769000, #Long 
	private Long timestamp;
	// "/00/00009/00041", # String 
	private String toFunctionCodeFullPath;
	//endTime-timestamp
	private Long untilNowlengthOfTime;
	private Integer toFunctionCodeLevel;
	// #用户总部职能OrgId,字符串0表示无OrgId,嘉宝员工有效
	private List<String> userOrgIdHeadFunctional;
	// ##用户区域职能OrgId,嘉宝员工有效,字符串0表示无OrgId
	private List<String> userOrgIdAreaFunctional;
	// ##用户片区OrgId,嘉宝员工有效,字符串0表示无OrgId
	private List<String> userOrgIdArea;
	// ##用户公司OrgId,嘉宝员工有效,字符串0表示无OrgId
	private List<String> userOrgIdCompany;
	// ##用户项目OrgId,嘉宝员工有效,字符串0表示无OrgId
	private List<String> userOrgIdProject;
	// 是否内部员工 1:是 0：不是
	private Integer userIsInternal;
	// 用户信息接收时间--当前时间为准
	private Long receivingTime;
	private String sysName;
	private String logType;
	private Long timestamps;
	private String metaCode;

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceOS() {
		return deviceOS;
	}

	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceLongitude() {
		return deviceLongitude;
	}

	public void setDeviceLongitude(String deviceLongitude) {
		this.deviceLongitude = deviceLongitude;
	}

	public String getDeviceLatitude() {
		return deviceLatitude;
	}

	public void setDeviceLatitude(String deviceLatitude) {
		this.deviceLatitude = deviceLatitude;
	}

	public String getDeviceNetworkType() {
		return deviceNetworkType;
	}

	public void setDeviceNetworkType(String deviceNetworkType) {
		this.deviceNetworkType = deviceNetworkType;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserSex() {
		return userSex;
	}

	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public String getUserProjectId() {
		return userProjectId;
	}

	public void setUserProjectId(String userProjectId) {
		this.userProjectId = userProjectId;
	}

	public String getUserProjectName() {
		return userProjectName;
	}

	public void setUserProjectName(String userProjectName) {
		this.userProjectName = userProjectName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFromFunctionCode() {
		return fromFunctionCode;
	}

	public void setFromFunctionCode(String fromFunctionCode) {
		this.fromFunctionCode = fromFunctionCode;
	}

	public String getToFunctionCode() {
		return toFunctionCode;
	}

	public void setToFunctionCode(String toFunctionCode) {
		this.toFunctionCode = toFunctionCode;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public List<AppOriginLogUserEventExtendData> getData() {
		return data;
	}

	public void setData(List<AppOriginLogUserEventExtendData> data) {
		this.data = data;
	}

	public Long getUserRegisterTime() {
		return userRegisterTime;
	}

	public void setUserRegisterTime(Long userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}

	public Long getLengthOfTime() {
		return lengthOfTime;
	}

	public void setLengthOfTime(Long lengthOfTime) {
		this.lengthOfTime = lengthOfTime;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getToFunctionCodeFullPath() {
		return toFunctionCodeFullPath;
	}

	public void setToFunctionCodeFullPath(String toFunctionCodeFullPath) {
		this.toFunctionCodeFullPath = toFunctionCodeFullPath;
	}

	public Long getUntilNowlengthOfTime() {
		return untilNowlengthOfTime;
	}

	public void setUntilNowlengthOfTime(Long untilNowlengthOfTime) {
		this.untilNowlengthOfTime = untilNowlengthOfTime;
	}

	public Integer getToFunctionCodeLevel() {
		return toFunctionCodeLevel;
	}

	public void setToFunctionCodeLevel(Integer toFunctionCodeLevel) {
		this.toFunctionCodeLevel = toFunctionCodeLevel;
	}

	public List<String> getUserOrgIdHeadFunctional() {
		return userOrgIdHeadFunctional;
	}

	public void setUserOrgIdHeadFunctional(List<String> userOrgIdHeadFunctional) {
		this.userOrgIdHeadFunctional = userOrgIdHeadFunctional;
	}

	public List<String> getUserOrgIdAreaFunctional() {
		return userOrgIdAreaFunctional;
	}

	public void setUserOrgIdAreaFunctional(List<String> userOrgIdAreaFunctional) {
		this.userOrgIdAreaFunctional = userOrgIdAreaFunctional;
	}

	public List<String> getUserOrgIdArea() {
		return userOrgIdArea;
	}

	public void setUserOrgIdArea(List<String> userOrgIdArea) {
		this.userOrgIdArea = userOrgIdArea;
	}

	public List<String> getUserOrgIdCompany() {
		return userOrgIdCompany;
	}

	public void setUserOrgIdCompany(List<String> userOrgIdCompany) {
		this.userOrgIdCompany = userOrgIdCompany;
	}

	public List<String> getUserOrgIdProject() {
		return userOrgIdProject;
	}

	public void setUserOrgIdProject(List<String> userOrgIdProject) {
		this.userOrgIdProject = userOrgIdProject;
	}

	public Integer getUserIsInternal() {
		return userIsInternal;
	}

	public void setUserIsInternal(Integer userIsInternal) {
		this.userIsInternal = userIsInternal;
	}

	public Long getUserBindingTime() {
		return userBindingTime;
	}

	public void setUserBindingTime(Long userBindingTime) {
		this.userBindingTime = userBindingTime;
	}

	public Long getReceivingTime() {
		return receivingTime;
	}

	public void setReceivingTime(Long receivingTime) {
		this.receivingTime = receivingTime;
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

	public Long getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(Long timestamps) {
		this.timestamps = timestamps;
	}

	public String getMetaCode() {
		return metaCode;
	}

	public void setMetaCode(String metaCode) {
		this.metaCode = metaCode;
	}
}
