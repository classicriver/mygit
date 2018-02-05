/**   
*/ 
package com.ptae.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.ptae.auth.api.FileControllerRemoteApi;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月27日 
* @version V1.0   
 */
@FeignClient(value = "provider-auth")
public interface FileService extends FileControllerRemoteApi{
	
}
