/**   
*/ 
package com.ptae.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.ptae.auth.api.AddressControllerRemoteApi;

/**
 * @Description: TODO
* @author  xiesc
* @date 2017年10月24日 
* @version V1.0   
 */
@FeignClient(value = "provider-auth")
public interface AddressService extends AddressControllerRemoteApi{

}
