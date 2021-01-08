package com.justbon.lps.entity.rfis;

import com.google.gson.annotations.JsonAdapter;
import com.justbon.lps.gsonadapter.DateAdapter;
/**
 * 
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description 用户绑定信息
 */
public class BindingInfo{
	
	private String projectId;
    private String resourceId;
    @JsonAdapter(DateAdapter.class)
    private Long bindTime;
    
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Long getBindTime() {
		return bindTime;
	}
	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	}
}
