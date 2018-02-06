/**   
*/ 
package com.ptae.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ptae.auth.api.FileControllerRemoteApi;
import com.ptae.auth.api.model.FileMeta;
import com.ptae.auth.common.CommonUtils;
import com.ptae.auth.common.JsonUtil;
import com.ptae.base.controller.BaseController;
import com.ptae.fdfs.cilent.FDFSClient;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
@RestController
public class FileController extends BaseController implements FileControllerRemoteApi{
	
	@Autowired
	private FDFSClient client;

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.FileControllerRemoteApi#upload(com.ptae.auth.api.model.FileMeta, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public FileMeta upload(@RequestParam("fileMeta") String fileMeta,@RequestParam("file") MultipartFile file) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
		FileMeta meta = null;
		try {
			meta = JsonUtil.json2Object(fileMeta, FileMeta.class);
			
			if(CommonUtils.isNullOrEmpty(meta.getFileName())) {
				String[] result = client.uploadFile(file.getBytes(), "mp4");
				if(result.length > 0) {
					meta.setGroupId(result[0]);
					meta.setPath(result[1]);
					meta.setFileName(result[1]);
				}
			}else {
				int appendFile = client.appendFile(meta.getGroupId(), meta.getFileName(), file.getBytes());
				System.out.println(appendFile);
				
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("fileMeta", meta);
			list.add(map);
			//System.out.println(meta.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return meta;
	}
	
}
