package com.justbon.lps.entity.origin;

/**
 * 
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description 设备信息
 */
public class AppOriginLogDeviceInfo{

    private String id;
    private String os;
    private String osVersion;
    private String brand;
    private String model;
    private String longitude;
    private String latitude;
    private String other;
    //"1920*1080", #�豸�ֱ���
  	private String resolution;
  	//1527368769000, #Long ��־�ϱ�ʱ��,���볤����
  	private Long timestamp;
  	// #Chrome/IE/FireFox/360/猎豹，浏览器类型
    private String browserType;
    //#浏览器版本
    private String browserVersion;
    
    public String getBrowserType() {
		return browserType;
	}

	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	public String getBrowserVersion() {
		return browserVersion;
	}

	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}