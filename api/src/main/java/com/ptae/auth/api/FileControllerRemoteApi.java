/**   
*/ 
package com.ptae.auth.api;

import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ptae.auth.api.model.FileMeta;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
@RequestMapping("/provider-auth")
public interface FileControllerRemoteApi {
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public FileMeta upload(@RequestParam("fileMeta") String fileMeta,@RequestParam("file") MultipartFile file);
}
