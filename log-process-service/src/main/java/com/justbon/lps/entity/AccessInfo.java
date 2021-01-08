package com.justbon.lps.entity;
/**
 * 
 * @author xiesc
 * @date 2020年8月14日
 * @version 1.0.0
 * @Description: TODO
 */
public class AccessInfo {
	
	private Integer lengthOfresponse;
	private String responseResult;
	private String requestArgs;
	private Long responseTime;
	private String remoteAddr;
	private String userAgent;
	private String upstreamAddr;
	private String accessUrl;
	private String requestMethod;
	
	public Integer getLengthOfresponse() {
		return lengthOfresponse;
	}
	public void setLengthOfresponse(Integer lengthOfresponse) {
		this.lengthOfresponse = lengthOfresponse;
	}
	public String getResponseResult() {
		return responseResult;
	}
	public void setResponseResult(String responseResult) {
		this.responseResult = responseResult;
	}
	public String getRequestArgs() {
		return requestArgs;
	}
	public void setRequestArgs(String requestArgs) {
		this.requestArgs = requestArgs;
	}
	public Long getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Long responseTime) {
		this.responseTime = responseTime;
	}
	public String getRemoteAddr() {
		return remoteAddr;
	}
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getUpstreamAddr() {
		return upstreamAddr;
	}
	public void setUpstreamAddr(String upstreamAddr) {
		this.upstreamAddr = upstreamAddr;
	}
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	
	
}
