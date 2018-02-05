/**   
*/ 
package com.ptae.zuul.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @Description: TODO
* @author  xiesc
* @date 2018年1月10日 
* @version V1.0   
 */
@Component
public class ServerFallback implements ZuulFallbackProvider{

	/* (non-Javadoc)
	 * @see org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider#fallbackResponse()
	 */
	@Override
	public ClientHttpResponse fallbackResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider#getRoute()
	 */
	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return null;
	}

}
