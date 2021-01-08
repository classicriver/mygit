package com.justbon.lps.entity.rfis;

import java.util.List;

import com.google.gson.annotations.JsonAdapter;
import com.justbon.lps.gsonadapter.DateAdapter;

public class RfisEntity {
	
	@JsonAdapter(DateAdapter.class)
    private Long bindTime;
    private String id;
    private String userName;
    private Long registerTime;
    private String userMobile;
    private String userSex;
    private Integer userAge;
    private String userIsInternal; 
    private String projectId;
    
    private List<BindingInfo> contactRelatinosViews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

	public String getUserIsInternal() {
		return userIsInternal;
	}

	public void setUserIsInternal(String userIsInternal) {
		this.userIsInternal = userIsInternal;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Long getBindTime() {
		return bindTime;
	}

	public void setBindTime(Long bindTime) {
		this.bindTime = bindTime;
	}

	public List<BindingInfo> getContactRelatinosViews() {
		return contactRelatinosViews;
	}

	public void setContactRelatinosViews(List<BindingInfo> contactRelatinosViews) {
		this.contactRelatinosViews = contactRelatinosViews;
	}
}
