package com.justbon.lps.utils;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Lists;
import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.entity.MetaEntity;
import com.justbon.lps.entity.origin.AppOriginLogAppInfo;
import com.justbon.lps.entity.origin.AppOriginLogDeviceInfo;
import com.justbon.lps.entity.origin.AppOriginLogEntity;
import com.justbon.lps.entity.origin.AppOriginLogUserEvent;
import com.justbon.lps.entity.origin.AppOriginLogUserEventExtendData;

/**
 * @author xiesc
 * @date 2020年8月5日
 * @version 1.0.0
 * @Description: 对象转换
 */
public class EntityTransverterUtil {

	public BehalogHbaseEntity appTransform(AppOriginLogEntity originLog) {
		BehalogHbaseEntity hbaseEntity = new BehalogHbaseEntity();
		AppOriginLogUserEvent userEvent = originLog.getEventList();
		AppOriginLogAppInfo appInfo = originLog.getApp();
		AppOriginLogDeviceInfo deviceInfo = originLog.getDeviceInfo();
		MetaEntity metaData = originLog.getMetaData();
		if (userEvent == null)
			return hbaseEntity;
		if (appInfo == null)
			return hbaseEntity;
		if (deviceInfo == null)
			return hbaseEntity;
		hbaseEntity.setSysName(originLog.getSysName());
		hbaseEntity.setLogType(originLog.getLogType());
		hbaseEntity.setTimestamps(originLog.getTimestamps());
		hbaseEntity.setMetaCode(originLog.getMetaCode());
		hbaseEntity.setDeviceId(deviceInfo.getId());
		hbaseEntity.setDeviceOS(deviceInfo.getOs());
		hbaseEntity.setDeviceVersion(deviceInfo.getOsVersion());
		hbaseEntity.setDeviceBrand(deviceInfo.getBrand());
		hbaseEntity.setDeviceModel(deviceInfo.getModel());
		hbaseEntity.setDeviceLongitude(deviceInfo.getLongitude());
		hbaseEntity.setDeviceLatitude(deviceInfo.getLatitude());
		hbaseEntity.setDeviceNetworkType(userEvent.getNetworkType());
		hbaseEntity.setAppType(appInfo.getType());
		hbaseEntity.setAppVersion(appInfo.getVersion());
		hbaseEntity.setUserId(userEvent.getUserId());
		hbaseEntity.setUserName("");
		hbaseEntity.setUserMobile("");
		hbaseEntity.setUserSex("");
		hbaseEntity.setUserAge(0);
		hbaseEntity.setUserProjectId(userEvent.getUserProjectId());
		hbaseEntity.setUserProjectName(userEvent.getUserProjectName());
		if(metaData != null){
			hbaseEntity.setAction(metaData.getEventCode());
			hbaseEntity.setToFunctionCode(metaData.getBpCode());
			hbaseEntity.setToFunctionCodeFullPath(metaData.getFullName());
			hbaseEntity.setToFunctionCodeLevel(Integer.parseInt(metaData.getBpLevel() == null ? "0" : metaData.getBpLevel()));
		}else{
			hbaseEntity.setAction(userEvent.getAction());
			hbaseEntity.setToFunctionCode(userEvent.getToFunctionCode());
			hbaseEntity.setToFunctionCodeFullPath(userEvent.getToFunctionCodeFullPath());
			hbaseEntity.setToFunctionCodeLevel(userEvent.getToFunctionCodeLevel());
		}
		hbaseEntity.setFromFunctionCode(userEvent.getFromFunctionCode());
		hbaseEntity.setStartTime(userEvent.getStartTime());
		hbaseEntity.setEndTime(userEvent.getEndTime());
		List<AppOriginLogUserEventExtendData> extendData = userEvent.getData();
		if (CollectionUtils.isNotEmpty(extendData)) {
			hbaseEntity.setData(extendData);
		} else {
			hbaseEntity.setData(Lists.newArrayList());
		}
		hbaseEntity.setUserRegisterTime(0L);
		hbaseEntity.setLengthOfTime(0L);
		Long endtime = hbaseEntity.getEndTime();
		Long starttime = hbaseEntity.getStartTime();
		if (endtime != null && starttime != null) {
			long lenght = endtime - starttime;
			if (lenght > 0) {
				if (lenght > 86400000) {
					hbaseEntity.setLengthOfTime(30000l);
				} else {
					hbaseEntity.setLengthOfTime(lenght);
				}
			}
		}
		hbaseEntity.setUntilNowlengthOfTime(0l);
		Long stamp = deviceInfo.getTimestamp();
		if (endtime == null && starttime != null && stamp != null) {
			if (starttime - stamp < 0) {
				hbaseEntity.setUntilNowlengthOfTime(0l);
			} else {
				hbaseEntity.setUntilNowlengthOfTime(starttime - stamp);
			}
		} else if (endtime != null && stamp != null) {
			if (endtime - stamp < 0) {
				hbaseEntity.setUntilNowlengthOfTime(0l);
			} else {
				hbaseEntity.setUntilNowlengthOfTime(endtime - stamp);
			}
		}
		
		hbaseEntity.setResolution(deviceInfo.getResolution());
		hbaseEntity.setTimestamp(deviceInfo.getTimestamp());
		hbaseEntity.setUserIsInternal(0);
		if (hbaseEntity.getUserRegisterTime() == null || hbaseEntity.getUserRegisterTime() == 0) {
			hbaseEntity.setUserRegisterTime(946656000000l);
		}
		if (hbaseEntity.getUserBindingTime() == null || hbaseEntity.getUserBindingTime() == 0) {
			hbaseEntity.setUserBindingTime(946656000000l);
		}
		hbaseEntity.setRowkey(hbaseEntity.getStartTime() + "_" + hbaseEntity.getAppType() + "_"
				+ hbaseEntity.getToFunctionCode() + "_" + hbaseEntity.getAction() + "_" + hbaseEntity.getUserId());
		hbaseEntity.setReceivingTime(new Date().getTime());
		hbaseEntity.setProperties();
		return hbaseEntity;
	}
}
