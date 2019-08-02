package com.iswater.map.pojos;

import java.util.Date;

public class FilterPlan {
	
    private Integer id;

    private String deviceId;

    private String previousRemark;
    
    private Date previousTime;
    
    private String nextRemark;
    
    private Date nextTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getPreviousRemark() {
		return previousRemark;
	}

	public void setPreviousRemark(String previousRemark) {
		this.previousRemark = previousRemark;
	}

	public Date getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(Date previousTime) {
		this.previousTime = previousTime;
	}

	public String getNextRemark() {
		return nextRemark;
	}

	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
	
	
    
    
}