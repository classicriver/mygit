package com.tw.consumer.kylin.scheduler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;

import com.tw.consumer.config.Config;
import com.tw.consumer.log.LogFactory;

public class KylinRestClient {

	private final HttpClient httpClient;
	private final String kylinUri = Config.getInstance().getKylinUri();
	private final Logger logger = LogFactory.getLogger();

	public KylinRestClient() {
		httpClient = new DefaultHttpClient();
		authentication();
	}

	/**
	 * 登录
	 */
	private void authentication() {
		StringEntity stringEntity = new StringEntity(
				"{\"userDetails\":{\"password\":\"KYLIN\",\"username\":\"ADMIN\","
						+ "\"authorities\":[{\"authority\":\"ROLE_ANALYST\"},"
						+ "{\"authority\":\"ROLE_MODELER\"}],\"accountNonExpired\":\"true\",\"accountNonLocked\":\"true\","
						+ "\"credentialsNonExpired\":\"true\",\"enabled\":\"true\"}}",
				ContentType.create("application/json", "utf-8"));
		postRequest("/user/authentication", stringEntity);
	}

	/**
	 * put请求
	 * @param path 请求路径
	 * @param entity 请求entity
	 * @return
	 */
	public String putRequest(String path, HttpEntity entity) {
		HttpPut httpPut = new HttpPut(kylinUri + path);
		// 设置数据
		if (null != entity) {
			httpPut.setEntity(entity);
		}
		return baseRequest(httpPut);
	}
	/**
	 * post请求
	 * @param path 请求路径
	 * @param entity 请求entity
	 * @return
	 */
	public String postRequest(String path, HttpEntity entity) {
		HttpPost httpPost = new HttpPost(kylinUri + path);
		// 设置数据
		if (null != entity) {
			httpPost.setEntity(entity);
		}
		return baseRequest(httpPost);
	}
	/**
	 * get请求
	 * @param path 请求路径
	 * @return
	 */
	public String getRequest(String path) {
		HttpGet get = new HttpGet(kylinUri + path);
		return baseRequest(get);
	}

	private String baseRequest(HttpRequestBase request) {
		String entity = "";
		request.setHeader("Authorization", "Basic QURNSU46S1lMSU4=");
		try {
			entity = handleResponse(httpClient.execute(request));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		return entity;

	}

	private String handleResponse(HttpResponse httpResponse)
			throws ParseException, IOException {
		String entity = "";
		if (httpResponse != null) {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				entity = EntityUtils.toString(httpResponse.getEntity());
			} else {
				logger.error("Error Response: "
						+ httpResponse.getStatusLine().toString());
				return null;
			}
		}
		return entity;
	}

	/*public static void main(String[] args) {
		KylinRestClient client = new KylinRestClient();
		String request = client.getRequest("/cubes");
		Gson gson = new Gson();
		Object fromJson = gson.fromJson(request, new TypeToken<List<Cube>>() {}.getType());
		System.out.println(fromJson);
	}*/
}
