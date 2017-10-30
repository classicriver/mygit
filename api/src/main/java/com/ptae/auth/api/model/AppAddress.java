package com.ptae.auth.api.model;

import java.util.Date;

import com.ptae.base.model.Entity;

public class AppAddress extends Entity{
    private Long id;

    private String userAccount;

    private String homeAddress;

    private String compnayAddress;

    private Date addTime;

    private Date updateTime;

    private String homeLongitude;

    private String homeLatitude;

    private String companyLongitude;

    private String companyLatitude;

    private String attribute5;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress == null ? null : homeAddress.trim();
    }

    public String getCompnayAddress() {
        return compnayAddress;
    }

    public void setCompnayAddress(String compnayAddress) {
        this.compnayAddress = compnayAddress == null ? null : compnayAddress.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getHomeLongitude() {
        return homeLongitude;
    }

    public void setHomeLongitude(String homeLongitude) {
        this.homeLongitude = homeLongitude == null ? null : homeLongitude.trim();
    }

    public String getHomeLatitude() {
        return homeLatitude;
    }

    public void setHomeLatitude(String homeLatitude) {
        this.homeLatitude = homeLatitude == null ? null : homeLatitude.trim();
    }

    public String getCompanyLongitude() {
        return companyLongitude;
    }

    public void setCompanyLongitude(String companyLongitude) {
        this.companyLongitude = companyLongitude == null ? null : companyLongitude.trim();
    }

    public String getCompanyLatitude() {
        return companyLatitude;
    }

    public void setCompanyLatitude(String companyLatitude) {
        this.companyLatitude = companyLatitude == null ? null : companyLatitude.trim();
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5 == null ? null : attribute5.trim();
    }
}