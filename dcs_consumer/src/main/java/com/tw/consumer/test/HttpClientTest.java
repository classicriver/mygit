package com.tw.consumer.test;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientTest implements TestClient{
	private int count = 0;
	private HttpClient httpClient = new DefaultHttpClient();
	private String logstashURL = "http://112test:28800";
	
	
	public HttpClientTest(int count){
		this.count = count;
	}
	
	public HttpClientTest(){
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			long currentTimeMillis = System.currentTimeMillis();
			StringEntity stringEntity = new StringEntity(
					"{\"sysName\":\"bmm\",\"logType\":\"syslog\",\"timestamps\":\""+String.valueOf(currentTimeMillis)+"\""
							+ ",\"level\": \"ERROR\""
							+ ",\"thread\": \"main\""
							+ ",\"klass\": \"com.justbon.a.AgentTest\""
							+ ",\"message\": \"异常数据{....}\""
							+ ",\"stackTrace\": \"org.flink.web.bind.MethodArgumentNotValidException: Validation failed for argument at index 0 in method: public com.justbon.springboot.common.bean.ResultBean<java.lang.Void> com.justbon.logmanager.controller.BuriedPointController.saveBiz(com.justbon.logmanager.bean.dto.BPBusinessDTO) throws java.lang.Exception, with 2 error(s): [Field error in object 'BPBusinessDTO' on field 'bizName': rejected value [null]; codes [NotEmpty.BPBusinessDTO.bizName,NotEmpty.bizName,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [BPBusinessDTO.bizName,bizName]; arguments []; default message [bizName]]; default message [业务名称不可为空]] [Field error in object 'BPBusinessDTO' on field 'bizCode': rejected value [null]; codes [NotEmpty.BPBusinessDTO.bizCode,NotEmpty.bizCode,NotEmpty.java.lang.String,NotEmpty]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [BPBusinessDTO.bizCode,bizCode]; arguments []; default message [bizCode]]; default message [业务编号不可为空]] at org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor.resolveArgument(RequestResponseBodyMethodProcessor.java:138) at org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:124) at org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:161) at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:131) at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:102) at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:877) at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:783) at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:991) at org.springframework.web.servlet.hadoop.doService(DispatcherServlet.java:925)\"}",
					ContentType.create("application/json", "utf-8"));
			postRequest(stringEntity);
			try {
				Thread.sleep(1000/count);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void init(int count) {
		// TODO Auto-generated method stub
		this.count = count;
	}

	/**
	 * post请求
	 * @param path 请求路径
	 * @param entity 请求entity
	 * @return
	 */
	public String postRequest(HttpEntity entity) {
		HttpPost httpPost = new HttpPost(logstashURL);
		// 设置数据
		if (null != entity) {
			httpPost.setEntity(entity);
		}
		return baseRequest(httpPost);
	}
	
	private String baseRequest(HttpRequestBase request) {
		String entity = "";
		//request.setHeader("Authorization", "Basic QURNSU46S1lMSU4=");
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
				System.out.println("Error Response: "
						+ httpResponse.getStatusLine().toString());
				return null;
			}
		}
		return entity;
	}
}
