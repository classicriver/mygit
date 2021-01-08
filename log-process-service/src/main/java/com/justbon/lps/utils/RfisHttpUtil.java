package com.justbon.lps.utils;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.justbon.lps.config.ServiceConfig;
import com.justbon.lps.constants.Constants;
import com.justbon.lps.entity.rfis.BindingInfo;
import com.justbon.lps.entity.rfis.CustomerEntity;
import com.justbon.lps.entity.rfis.RfisEntity;
import com.justbon.lps.entity.rfis.StaffEntity;

/**
 * @author xiesc
 * @date 2020年8月3日
 * @version 1.0.0
 * @Description: rfis http客户端工具类
 */
public class RfisHttpUtil {
	private static final ServiceConfig config = ServiceConfig.getInstance();
	private final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	private static final String BASEURI = config.getAsString(Constants.RFIS_URL);
	private static final String STAFFURI = BASEURI + "staff/id/";
	private static final String CONTACTURI = BASEURI + "contact/id/";
	private static final String HEADER_TOKEN = "token";
	private Logger logger = LogManager.getLogger(RfisHttpUtil.class);
	private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	/**
	 * put请求
	 * 
	 * @param path
	 *            请求路径
	 * @param entity
	 *            请求entity
	 * @return
	 */
	public String putRequest(String path, HttpEntity entity) {
		HttpPut httpPut = new HttpPut(path);
		// 设置数据
		if (null != entity) {
			httpPut.setEntity(entity);
		}
		return baseRequest(httpPut);
	}

	/**
	 * post请求
	 * 
	 * @param path
	 *            请求路径
	 * @param entity
	 *            请求entity
	 * @return
	 */
	public String postRequest(String path, HttpEntity entity) {
		HttpPost httpPost = new HttpPost(path);
		// 设置数据
		if (null != entity) {
			httpPost.setEntity(entity);
		}
		return baseRequest(httpPost);
	}

	/**
	 * get请求
	 * 
	 * @param path
	 *            请求路径
	 * @return
	 */
	public String getRequest(String path) {
		HttpGet get = new HttpGet(path);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000) // 服务器响应超时时间
				.setConnectTimeout(5000) // 连接服务器超时时间
				.build();
		get.setConfig(requestConfig);
		return baseRequest(get);
	}

	private String baseRequest(HttpRequestBase request) {
		String entity = "";
		request.setHeader(HEADER_TOKEN, config.getAsString(Constants.RFIS_TOKEN));
		request.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
				+ " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		try {
			entity = handleResponse(httpClient.execute(request));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		return entity;

	}

	private String handleResponse(HttpResponse httpResponse) throws ParseException, IOException {
		String entity = "";
		if (httpResponse != null) {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				entity = EntityUtils.toString(httpResponse.getEntity());
			} else {
				logger.error("Error Response: " + httpResponse.getStatusLine().toString());
				return null;
			}
		}
		return entity;
	}

	/**
	 * @Title: getAppStaffDataByStaffId
	 * @Description: 获取员工信息
	 * @param: @param
	 *             staffId
	 * @param: @param
	 *             projectId
	 * @param: @return
	 * @return:StaffData
	 */
	public StaffEntity getAppStaffDataByStaffId(String staffId, String projectId) {
		if (StringUtils.isEmpty(staffId)) {
			return null;
		}
		String path = STAFFURI + staffId;
		String result = getRequest(path);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		StaffEntity data = parseRfisJsonString2Object(StaffEntity.class, result, path);
		// 返回的结果包含该用户所有的项目，只要当前项目
		if (null != data) {
			List<BindingInfo> views = data.getContactRelatinosViews();
			setCurrentProject(data, views, projectId);
		}
		return data;
	}

	/**
	 * {"status":"0","msg":"ok","data":{"id":"12097701583","userName":"13585083402","registerTime":1579656054000,"userMobile":"13585083402","userSex":"","userAge":null,"birthday":null,"nickName":"13585083402","avatar":"","userIsInternal":"0","contactRelatinosViews":[{"projectId":"9983200191","resourceId":"10031135369","bindTime":"2020-01-22
	 * 09:30:49"}],"userOrgViews":null}}
	 * 
	 * @Title: getAppCustomerDataByContactId
	 * @Description: 获取APP用户信息
	 * @param: @param
	 *             contactId
	 * @param: @param
	 *             projectId
	 * @param: @return
	 * @return:CustomerData
	 */
	public CustomerEntity getAppCustomerDataByContactId(String contactId, String projectId) {
		if (StringUtils.isEmpty(contactId)) {
			return null;
		}
		String path = CONTACTURI + contactId;
		String result = getRequest(path);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		CustomerEntity data = parseRfisJsonString2Object(CustomerEntity.class, result, path);
		// 返回的结果包含该用户所有的项目，只要当前项目
		if (null != data) {
			List<BindingInfo> views = data.getContactRelatinosViews();
			setCurrentProject(data, views, projectId);
		}
		return data;
	}
	/**
	 * 
	 * @Title: parseRfisJsonString2Object   
	 * @Description: json 转object  
	 * @param: @param klass
	 * @param: @param json
	 * @param: @param path
	 * @param: @return
	 * @return:T
	 */
	private <T> T parseRfisJsonString2Object(Class<T> klass, String json, String path) {
		JsonObject jsonObject = (JsonObject) JsonParser.parseString(json);
		if (jsonObject.get("status") == null) {
			return null;
		}
		if (!"0".equals(jsonObject.get("status").getAsString())) {
			logger.error("rfis url:{} 获取customer 信息出错：{}", path, jsonObject.get("msg").toString());
			return null;
		}
		return gson.fromJson(jsonObject.get("data").toString(), klass);
	}
	/**
	 * @Title: setCurrentProject   
	 * @Description: 设置当前项目
	 * @param: @param entity
	 * @param: @param views
	 * @param: @param projectId
	 * @return:void
	 */
	private void setCurrentProject(RfisEntity entity, List<BindingInfo> views, String projectId) {
		if (null != views && views.size() > 0) {
			long time = 0;
			for (BindingInfo v : views) {
				if (projectId.equals(v.getProjectId())) {
					Long staffTime = v.getBindTime();
					if ((staffTime != null && time < staffTime) || staffTime == null) {
						entity.setBindTime(time);
						entity.setProjectId(projectId);
					}
				}
			}
		}
	}
	
	public void close(){
		try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*	public static void main(String[] args) {
		CustomerEntity appCustomerDataByContactId = new RfisHttpUtil().getAppCustomerDataByContactId("1004096042", "44717857");
		System.out.println(appCustomerDataByContactId.getId());
	}*/
}
