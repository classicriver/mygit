package com.justbon.lps.entity.origin;

import java.util.List;

/**
 * 
 * @author xiesc
 * @date 2020年8月4日
 * @version 1.0.0
 * @Description 用户事件
 */
public class AppOriginLogUserEvent{

    private String userId;
    private String userProjectId;
    private String userProjectName;
    private String action;
    private String fromFunctionCode;
    private String toFunctionCode;
    private Long startTime;
    private Long endTime;
    private String networkType;
    //"/00/00009/00041", # String 埋点功能编码全路径
  	private String toFunctionCodeFullPath;
  	//toFunctionCodeLevel: #  埋点功能级别
  	private Integer toFunctionCodeLevel;
    private List<AppOriginLogUserEventExtendData> data;
    
    //操作行为名称
  	private String functionPreName;
  	//切换到一键开门
  	private String functionName;
  	//访问的url
  	private String functionUrl;
  	//操作行为发生时间(毫秒长整型)
  	private long functionTime;
  	//"操作行为全路径", #查询用户列表/添加用户/修改logo
  	private String functionFullPath;
  	//操作行为用时(毫秒长整型)，有网络请求才有意义
  	private long lengthOfTime;
  	//操作行为用时(毫秒长整型)，有网络请求才有意义
  	private String functionResult;
  	private String sessionId;
  	
    public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getFunctionPreName() {
		return functionPreName;
	}

	public void setFunctionPreName(String functionPreName) {
		this.functionPreName = functionPreName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public long getFunctionTime() {
		return functionTime;
	}

	public void setFunctionTime(long functionTime) {
		this.functionTime = functionTime;
	}

	public String getFunctionFullPath() {
		return functionFullPath;
	}

	public void setFunctionFullPath(String functionFullPath) {
		this.functionFullPath = functionFullPath;
	}

	public long getLengthOfTime() {
		return lengthOfTime;
	}

	public void setLengthOfTime(long lengthOfTime) {
		this.lengthOfTime = lengthOfTime;
	}

	public String getFunctionResult() {
		return functionResult;
	}

	public void setFunctionResult(String functionResult) {
		this.functionResult = functionResult;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProjectId() {
        return userProjectId;
    }

    public void setUserProjectId(String userProjectId) {
        this.userProjectId = userProjectId;
    }

    public String getUserProjectName() {
        return userProjectName;
    }

    public void setUserProjectName(String userProjectName) {
        this.userProjectName = userProjectName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFromFunctionCode() {
        return fromFunctionCode;
    }

    public void setFromFunctionCode(String fromFunctionCode) {
        this.fromFunctionCode = fromFunctionCode;
    }

    public String getToFunctionCode() {
        return toFunctionCode;
    }

    public void setToFunctionCode(String toFunctionCode) {
        this.toFunctionCode = toFunctionCode;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public List<AppOriginLogUserEventExtendData> getData() {
        return data;
    }

    public void setData(List<AppOriginLogUserEventExtendData> data) {
        this.data = data;
    }

	public String getToFunctionCodeFullPath() {
		return toFunctionCodeFullPath;
	}

	public void setToFunctionCodeFullPath(String toFunctionCodeFullPath) {
		this.toFunctionCodeFullPath = toFunctionCodeFullPath;
	}

	public Integer getToFunctionCodeLevel() {
		return toFunctionCodeLevel;
	}

	public void setToFunctionCodeLevel(Integer toFunctionCodeLevel) {
		this.toFunctionCodeLevel = toFunctionCodeLevel;
	}
}