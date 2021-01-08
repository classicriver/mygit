package com.justbon.lps.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.justbon.lps.annotation.Nested;
import com.justbon.lps.entity.origin.AppOriginLogUserEventExtendData;

/**
 * @author xiesc
 * @date 2020年8月5日
 * @version 1.0.0
 * @Description: 行为日志hbase存储实体
 */
public class BehalogHbaseEntity{

	@SerializedName("rowkey")
	private String rowkey;
	@SerializedName("device_id")
	private String deviceId;
	@SerializedName("device_OS")
	private String deviceOS;
	@SerializedName("device_version")
	private String deviceVersion;
	@SerializedName("device_brand")
	private String deviceBrand;
	@SerializedName("device_model")
	private String deviceModel;
	@SerializedName("device_longitude")
	private String deviceLongitude;
	@SerializedName("device_latitude")
	private String deviceLatitude;
	@SerializedName("device_network_type")
	private String deviceNetworkType;
	@SerializedName("app_type")
	private String appType;
	@SerializedName("app_version")
	private String appVersion;
	@SerializedName("user_id")
	private String userId;
	@SerializedName("user_name")
	private String userName;
	@SerializedName("user_mobile")
	private String userMobile;
	@SerializedName("user_sex")
	private String userSex;
	@SerializedName("user_age")
	private Integer userAge;
	@SerializedName("user_project_id")
	private String userProjectId;
	@SerializedName("user_project_name")
	private String userProjectName;
	@SerializedName("user_register_time")
	private Long userRegisterTime;
	@SerializedName("user_binding_time")
	private Long userBindingTime;
	@SerializedName("action")
	private String action;
	@SerializedName("from_function_code")
	private String fromFunctionCode;
	@SerializedName("to_function_code")
	private String toFunctionCode;
	@SerializedName("start_time")
	private Long startTime;
	@SerializedName("end_time")
	private Long endTime;
	@SerializedName("length_of_time")
	private Long lengthOfTime;
	@Nested
	@SerializedName("data")
	private List<AppOriginLogUserEventExtendData> data;
	// "1920*1080", #Éè±¸·Ö±æÂÊ
	@SerializedName("resolution")
	private String resolution;
	// 1527368769000, #Long ÈÕÖ¾ÉÏ±¨Ê±¼ä,ºÁÃë³¤ÕûÐÍ
	@SerializedName("timestamp")
	private Long timestamp;
	// "/00/00009/00041", # String Âñµã¹¦ÄÜ±àÂëÈ«Â·¾¶
	@SerializedName("to_function_code_full_path")
	private String toFunctionCodeFullPath;
	// ÊÂ¼þ´ÓAPPÆô¶¯µ½ÏÖÔÚµÄÊ±³¤£¬ºÁÃë³¤ÕûÐÍ£¬endTime-timestamp
	@SerializedName("until_now_length_of_time")
	private Long untilNowlengthOfTime;
	// toFunctionCodeLevel: # ��㹦�ܼ���
	@SerializedName("to_function_code_level")
	private Integer toFunctionCodeLevel;
	// #用户总部职能OrgId,字符串0表示无OrgId,嘉宝员工有效
	@SerializedName("user_orgid_head_functional")
	private List<String> userOrgIdHeadFunctional;
	// ##用户区域职能OrgId,嘉宝员工有效,字符串0表示无OrgId
	@SerializedName("user_orgid_area_functional")
	private List<String> userOrgIdAreaFunctional;
	// ##用户片区OrgId,嘉宝员工有效,字符串0表示无OrgId
	@SerializedName("user_orgid_area")
	private List<String> userOrgIdArea;
	// ##用户公司OrgId,嘉宝员工有效,字符串0表示无OrgId
	@SerializedName("user_orgid_company")
	private List<String> userOrgIdCompany;
	// ##用户项目OrgId,嘉宝员工有效,字符串0表示无OrgId
	@SerializedName("user_orgid_project")
	private List<String> userOrgIdProject;
	// 是否内部员工 1:是 0：不是
	@SerializedName("user_is_internal")
	private Integer userIsInternal;
	// 用户信息接收时间--当前时间为准
	@SerializedName("receiving_time")
	private Long receivingTime;
	@SerializedName("sysName")
	private String sysName;
	@SerializedName("logType")
	private String logType;
	@SerializedName("timestamps")
	private Long timestamps;
	@SerializedName("metaCode")
	private String metaCode;
	// 冗余字段 非字符串转字符串
	@SerializedName("user_age_str")
	private String userAgeStr;
	@SerializedName("user_register_time_str")
	private String userRegisterTimeStr;
	@SerializedName("user_binding_time_str")
	private String userBindingTimeStr;
	@SerializedName("start_time_str")
	private String startTimeStr;
	@SerializedName("end_time_str")
	private String endTimeStr;
	@SerializedName("length_of_time_str")
	private String lengthOfTimeStr;
	@SerializedName("timestamp_str")
	private String timestampStr;
	@SerializedName("until_now_length_of_time_str")
	private String untilNowlengthOfTimeStr;
	@SerializedName("to_function_code_level_str")
	private String toFunctionCodeLevelStr;
	// 是否内部员工 1:是 0：不是
	@SerializedName("user_is_internal_str")
	private String userIsInternalStr;
	// 用户信息接收时间--当前时间为准
	@SerializedName("receiving_time_str")
	private String receivingTimeStr;
	

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
	
	public String getUserAgeStr() {
		if (userAgeStr == null && userAge != null) {
			userAgeStr = userAge.toString();
		}
		return userAgeStr;
	}

	public String getUserRegisterTimeStr() {
		if (userRegisterTimeStr == null && userRegisterTime != null) {
			userRegisterTimeStr = userRegisterTime.toString();
		}
		return userRegisterTimeStr;
	}

	public String getUserBindingTimeStr() {
		if (userBindingTimeStr == null && userBindingTime != null) {
			userBindingTimeStr = userBindingTime.toString();
		}
		return userBindingTimeStr;
	}

	public String getStartTimeStr() {
		if (startTimeStr == null && startTime != null) {
			startTimeStr = startTime.toString();
		}
		return startTimeStr;
	}

	public String getEndTimeStr() {
		if (endTimeStr == null && endTime != null) {
			endTimeStr = endTime.toString();
		}
		return endTimeStr;
	}

	public String getLengthOfTimeStr() {
		if (lengthOfTimeStr == null && lengthOfTime != null) {
			lengthOfTimeStr = lengthOfTime.toString();
		}
		return lengthOfTimeStr;
	}

	public String getTimestampStr() {
		if (timestampStr == null && timestamp != null) {
			timestampStr = timestamp.toString();
		}
		return timestampStr;
	}

	public String getUntilNowlengthOfTimeStr() {
		if (untilNowlengthOfTimeStr == null && untilNowlengthOfTime != null) {
			untilNowlengthOfTimeStr = untilNowlengthOfTime.toString();
		}
		return untilNowlengthOfTimeStr;
	}

	public String getToFunctionCodeLevelStr() {
		if (toFunctionCodeLevelStr == null && toFunctionCodeLevel != null) {
			toFunctionCodeLevelStr = toFunctionCodeLevel.toString();
		}
		return toFunctionCodeLevelStr;
	}

	public String getUserIsInternalStr() {
		if (userIsInternalStr == null && userIsInternal != null) {
			userIsInternalStr = userIsInternal.toString();
		}
		return userIsInternalStr;
	}

	public String getReceivingTimeStr() {
		if (receivingTimeStr == null && receivingTime != null) {
			receivingTimeStr = receivingTime.toString();
		}
		return receivingTimeStr;
	}

	public void setUserAgeStr(String userAgeStr) {
		this.userAgeStr = userAgeStr;
	}

	public void setUserRegisterTimeStr(String userRegisterTimeStr) {
		this.userRegisterTimeStr = userRegisterTimeStr;
	}

	public void setUserBindingTimeStr(String userBindingTimeStr) {
		this.userBindingTimeStr = userBindingTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public void setLengthOfTimeStr(String lengthOfTimeStr) {
		this.lengthOfTimeStr = lengthOfTimeStr;
	}

	public void setTimestampStr(String timestampStr) {
		this.timestampStr = timestampStr;
	}

	public void setUntilNowlengthOfTimeStr(String untilNowlengthOfTimeStr) {
		this.untilNowlengthOfTimeStr = untilNowlengthOfTimeStr;
	}

	public void setToFunctionCodeLevelStr(String toFunctionCodeLevelStr) {
		this.toFunctionCodeLevelStr = toFunctionCodeLevelStr;
	}

	public void setUserIsInternalStr(String userIsInternalStr) {
		this.userIsInternalStr = userIsInternalStr;
	}

	public void setReceivingTimeStr(String receivingTimeStr) {
		this.receivingTimeStr = receivingTimeStr;
	}

	public void setProperties() {
		if (userAgeStr == null && userAge != null) {
			userAgeStr = userAge.toString();
		}
		if (userRegisterTimeStr == null && userRegisterTime != null) {
			userRegisterTimeStr = userRegisterTime.toString();
		}
		if (userBindingTimeStr == null && userBindingTime != null) {
			userBindingTimeStr = userBindingTime.toString();
		}
		if (startTimeStr == null && startTime != null) {
			startTimeStr = startTime.toString();
		}
		if (endTimeStr == null && endTime != null) {
			endTimeStr = endTime.toString();
		}
		if (lengthOfTimeStr == null && lengthOfTime != null) {
			lengthOfTimeStr = lengthOfTime.toString();
		}
		if (timestampStr == null && timestamp != null) {
			timestampStr = timestamp.toString();
		}
		if (untilNowlengthOfTimeStr == null && untilNowlengthOfTime != null) {
			untilNowlengthOfTimeStr = untilNowlengthOfTime.toString();
		}
		if (toFunctionCodeLevelStr == null && toFunctionCodeLevel != null) {
			toFunctionCodeLevelStr = toFunctionCodeLevel.toString();
		}
		if (userIsInternalStr == null && userIsInternal != null) {
			userIsInternalStr = userIsInternal.toString();
		}
		if (receivingTimeStr == null && receivingTime != null) {
			receivingTimeStr = receivingTime.toString();
		}
	}
}
