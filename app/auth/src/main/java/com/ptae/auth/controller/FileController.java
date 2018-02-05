/**   
*/ 
package com.ptae.auth.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ptae.auth.api.FileControllerRemoteApi;
import com.ptae.auth.api.model.FileMeta;
import com.ptae.auth.common.JsonUtil;
import com.ptae.base.controller.BaseController;
import com.ptae.fdfs.cilent.FDFSCilent;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
@RestController
public class FileController extends BaseController implements FileControllerRemoteApi{
	
	@Autowired
	private FDFSCilent client;

	/* (non-Javadoc)
	 * @see com.ptae.auth.api.FileControllerRemoteApi#upload(com.ptae.auth.api.model.FileMeta, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public Map<String, Object> upload(@RequestParam("fileMeta") String fileMeta,@RequestParam("file") MultipartFile file) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			FileMeta meta = JsonUtil.json2Object(fileMeta, FileMeta.class);
			String[] result = client.uploadFile(file.getBytes(), "", null);
			if(result.length > 0) {
				meta.setGroupId(result[0]);
				meta.setPath(result[1]);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("fileMeta", meta);
			
			list.add(map);
			System.out.println(meta.getPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.successJson("上传成功", list);
	}
	
}
