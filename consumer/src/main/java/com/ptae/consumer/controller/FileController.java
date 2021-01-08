/**   
*/ 
package com.ptae.consumer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ptae.auth.api.model.FileMeta;
import com.ptae.base.controller.BaseController;
import com.ptae.consumer.service.FileService;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
@RestController
public class FileController extends BaseController{
	
	@Autowired
	private FileService service;
	
	@RequestMapping(value = "/file/upload", method = RequestMethod.POST)
	@ResponseBody
	public FileMeta upload(@RequestParam("fileMeta") String meta, @RequestParam("file") MultipartFile file){
		return service.upload(meta, file);
	}
}
