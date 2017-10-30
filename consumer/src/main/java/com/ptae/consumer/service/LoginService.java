/**   
*/ 
package com.ptae.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.ptae.auth.api.LoginControllerRemoteApi;
import com.ptae.consumer.fallback.FeignServerFactoryImpl;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0   
 */
@FeignClient(value = "provider-auth",fallbackFactory = FeignServerFactoryImpl.class)
public interface LoginService extends LoginControllerRemoteApi{

}
