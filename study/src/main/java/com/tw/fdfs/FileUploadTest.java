/**   
*//*
package com.tw.fdfs;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.ptae.auth.api.model.FileMeta;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

*//**
 * @Description: TODO
 * @author xiesc
 * @date 2018年1月30日
 * @version V1.0  
 *//*
public class FileUploadTest {

	public static void execute() throws Exception {
		FileMeta meta = new FileMeta();
		meta.setFileId("123456");

		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost("http://10.19.0.24:8762/provider-auth/file/upload");
		File file = new File("F:\\\\MVI_2818.mp4");
		
		CloseableHttpResponse rep = null;
		try (InputStream ins = new FileInputStream(file)) {
			byte[] bytes = new byte[256 * 1024];
			while (ins.read(bytes) != -1) {
				
				 * MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
				 * mEntityBuilder.addBinaryBody("file", bytes);
				 * httpPost.setEntity(mEntityBuilder.build());
				 
				ByteArrayBody in = new ByteArrayBody(bytes, ContentType.MULTIPART_FORM_DATA, "file");
				Gson gson = new Gson();
				StringBody fileMetaJson = new StringBody(gson.toJson(meta),
						ContentType.create("application/json", Consts.UTF_8));

				HttpEntity reqEntity = MultipartEntityBuilder.create()
						.addPart("fileMeta", fileMetaJson)
						.addPart("file", in)
						.build();
				httpPost.setEntity(reqEntity);
				rep = httpclient.execute(httpPost);
				HttpEntity entity = rep.getEntity();
				String string = EntityUtils.toString(entity);
				meta = JsonUtil.json2Object(string, FileMeta.class);
				System.out.println(string);
			}
		}finally {
			rep.close();
		}

	}

	public static void main(String[] args) {
		try {
			FileUploadTest.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
*/