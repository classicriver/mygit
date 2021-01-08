package com.justbon.lps.datasource;

import java.util.Collections;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.justbon.lps.constants.Constants;
import com.justbon.lps.core.LRUCache;
import com.justbon.lps.entity.BehalogHbaseEntity;
import com.justbon.lps.entity.origin.AppOriginLogEntity;
import com.justbon.lps.entity.rfis.RfisEntity;
import com.justbon.lps.entity.rfis.StaffEntity;
import com.justbon.lps.utils.EntityTransverterUtil;
import com.justbon.lps.utils.RfisHttpUtil;

/**
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description: 异步IO关联rfis
 */
public class AsyncRfisSource extends RichAsyncFunction<JsonObject, BehalogHbaseEntity> {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -1033877947194355877L;
	private transient LRUCache<RfisEntity> CACHE;
	private transient RfisHttpUtil rfis;
	private transient Gson gson;
	private transient EntityTransverterUtil het;
	private final static String suffix="_";

	@Override
	public void open(Configuration parameters) throws Exception {
		super.open(parameters);
		CACHE = new LRUCache<>();
		rfis = new RfisHttpUtil();
		gson = new Gson();
		het = new EntityTransverterUtil();
	}

	@Override
	public void asyncInvoke(JsonObject input, ResultFuture<BehalogHbaseEntity> resultFuture) throws Exception {
		AppOriginLogEntity originLog = gson.fromJson(input, AppOriginLogEntity.class);
		
		BehalogHbaseEntity hbaseEntity = het.appTransform(originLog);
		String logType = hbaseEntity.getAppType();
		if (null != hbaseEntity) {
			String userId = hbaseEntity.getUserId();
			String projectId = hbaseEntity.getUserProjectId();
			String cacheKey = userId+suffix+projectId+suffix+logType;
			if (Constants.USER_APP.equals(logType)) {//生活家
				RfisEntity customerData = CACHE.getFromCache(cacheKey);
				if (null == customerData) {
					customerData = rfis.getAppCustomerDataByContactId(userId,projectId);
					CACHE.putCache(cacheKey, customerData);
				}
				if (customerData != null) {
					hbaseEntity.setUserName(customerData.getUserName());
					hbaseEntity.setUserRegisterTime(customerData.getRegisterTime());
					hbaseEntity.setUserMobile(customerData.getUserMobile());
					hbaseEntity.setUserSex(customerData.getUserSex());
					hbaseEntity.setUserAge(customerData.getUserAge());
					hbaseEntity.setUserBindingTime(customerData.getBindTime());
					hbaseEntity.setUserIsInternal(Integer.valueOf(
							customerData.getUserIsInternal() == null ? "0" : customerData.getUserIsInternal()));
				}
			} else if (Constants.USER_STAFF.equals(logType)) {//员工
				StaffEntity staffData = (StaffEntity) CACHE.getFromCache(cacheKey);
				if (null == staffData) {
					staffData = rfis.getAppStaffDataByStaffId(userId,projectId);
					CACHE.putCache(cacheKey, staffData);
				}
				if(staffData!=null){
					hbaseEntity.setUserName(staffData.getUserName());
					hbaseEntity.setUserRegisterTime(staffData.getRegisterTime());
					hbaseEntity.setUserMobile(staffData.getUserMobile());
					hbaseEntity.setUserSex(staffData.getUserSex());
					hbaseEntity.setUserAge(staffData.getUserAge());
					hbaseEntity.setUserOrgIdArea(staffData.getUserOrgIdAreas());
					hbaseEntity.setUserOrgIdAreaFunctional(staffData.getUserOrgIdAreaFunctionals());
					hbaseEntity.setUserOrgIdCompany(staffData.getUserOrgIdCompanys());
					hbaseEntity.setUserOrgIdHeadFunctional(staffData.getUserOrgIdHeadFunctionals());
					hbaseEntity.setUserOrgIdProject(staffData.getUserOrgIdProjects());
					hbaseEntity.setUserIsInternal(Integer.valueOf(staffData.getUserIsInternal() == null ? "1" : staffData.getUserIsInternal()));
		        }
			}
			resultFuture.complete(Collections.singleton(hbaseEntity));
		}
	}

	@Override
	public void close() throws Exception {
		// 关闭rfis连接
		rfis.close();
		super.close();
	}

}
