package com.justbon.lps.entity.rfis;

import java.util.ArrayList;
import java.util.List;
/**
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description: 员工用户
 */
public class StaffEntity extends RfisEntity{

    private String birthday;
    private String nickName;
    private String avatar;

    private List<String> userOrgIdHeadFunctionals = new ArrayList<>();
    private List<String> userOrgIdAreaFunctionals = new ArrayList<>();
    private List<String> userOrgIdAreas = new ArrayList<>();
    private List<String> userOrgIdCompanys = new ArrayList<>();
    private List<String> userOrgIdProjects = new ArrayList<>();
    List<BindingInfo> bindingInfo;
   

	public List<String> getUserOrgIdHeadFunctionals() {
		return userOrgIdHeadFunctionals;
	}

	public void setUserOrgIdHeadFunctionals(List<String> userOrgIdHeadFunctionals) {
		this.userOrgIdHeadFunctionals = userOrgIdHeadFunctionals;
	}

	public List<String> getUserOrgIdAreaFunctionals() {
		return userOrgIdAreaFunctionals;
	}

	public void setUserOrgIdAreaFunctionals(List<String> userOrgIdAreaFunctionals) {
		this.userOrgIdAreaFunctionals = userOrgIdAreaFunctionals;
	}

	public List<String> getUserOrgIdAreas() {
		return userOrgIdAreas;
	}

	public void setUserOrgIdAreas(List<String> userOrgIdAreas) {
		this.userOrgIdAreas = userOrgIdAreas;
	}

	public List<String> getUserOrgIdCompanys() {
		return userOrgIdCompanys;
	}

	public void setUserOrgIdCompanys(List<String> userOrgIdCompanys) {
		this.userOrgIdCompanys = userOrgIdCompanys;
	}

	public List<String> getUserOrgIdProjects() {
		return userOrgIdProjects;
	}

	public void setUserOrgIdProjects(List<String> userOrgIdProjects) {
		this.userOrgIdProjects = userOrgIdProjects;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<BindingInfo> getBindingInfo() {
		return bindingInfo;
	}

	public void setBindingInfo(List<BindingInfo> bindingInfo) {
		this.bindingInfo = bindingInfo;
	}
	
}