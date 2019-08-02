package com.iswater.common.utils;

import java.math.BigDecimal;
import java.util.Date;

import com.iswater.common.utils.CommonUtil;

public class Device {
    private Integer id;

    private String deviceId;

    private String deviceName;

    private Date updateTime;

    private Integer tds;
    

    private Float ph;

    private Float color;

    private Float trt;

    private Float tbdt;

    private Integer purifyTds;

    private Float purifyPh;

    private Float purifyColor;

    private Float purifyTrt;

    private Float purifyTbdt;

    private Float amount;
    
    private Float purifyAmount;

    private BigDecimal deviceX;

    private BigDecimal deviceY;

    private String deviceDesc;

    private Integer regionId;
    
    private Integer BindedUserId; // 该设备被哪个代理商管理
    
    private Integer status; //设备状态  1:管理中, 2:正在申请管理 , 0:正在管理
    
   private Integer generalInTds; //一般情况入水tds
    
    private Integer special; //是否为特殊设备
    
    private Float generalInSd;
    
    private Float generalInZd;
    
    private int hasAmountProbe; //是否拥有水质坍塌欧
    
    private Float usageInOnDay; // 日用水量
    private Float usageInWeek; // 周用水量
    private Float usageInMonth; //月用水量

	public Float getUsageInOnDay() {
		return usageInOnDay;
	}

	public void setUsageInOnDay(Float usageInOnDay) {
		this.usageInOnDay = usageInOnDay;
	}

	public Float getUsageInWeek() {
		return usageInWeek;
	}

	public void setUsageInWeek(Float usageInWeek) {
		this.usageInWeek = usageInWeek;
	}

	public Float getUsageInMonth() {
		return usageInMonth;
	}

	public void setUsageInMonth(Float usageInMonth) {
		this.usageInMonth = usageInMonth;
	}

	public int getHasAmountProbe() {
		return hasAmountProbe;
	}

	public void setHasAmountProbe(int hasAmountProbe) {
		this.hasAmountProbe = hasAmountProbe;
	}

	public Integer getGeneralInTds() {
		return generalInTds;
	}

	public void setGeneralInTds(Integer generalInTds) {
		this.generalInTds = generalInTds;
	}

	public Integer getSpecial() {
		return special;
	}

	public void setSpecial(Integer special) {
		this.special = special;
	}

	public Float getGeneralInSd() {
		return generalInSd;
	}

	public void setGeneralInSd(Float generalInSd) {
		this.generalInSd = generalInSd;
	}

	public Float getGeneralInZd() {
		return generalInZd;
	}

	public void setGeneralInZd(Float generalInZd) {
		this.generalInZd = generalInZd;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBindedUserId() {
		return BindedUserId;
	}

	public void setBindedUserId(Integer bindedUserId) {
		BindedUserId = bindedUserId;
	}

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
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTds() {
        return tds;
    }

    public void setTds(Integer tds) {
        this.tds = tds;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Float getColor() {
//    	Float newColor = CommonUtil.formatFloat4(color, 4);
//        return newColor;
    	return color;
    }

    public void setColor(Float color) {
//    	Float newColor = CommonUtil.formatFloat4(color, 4);
//        this.color = newColor;
    	this.color = color;
    }

    public Float getTrt() {
        return trt;
    }

    public void setTrt(Float trt) {
        this.trt = trt;
    }

    public Float getTbdt() {
        return tbdt;
    }

    public void setTbdt(Float tbdt) {
        this.tbdt = tbdt;
    }

    public Integer getPurifyTds() {
        return purifyTds;
    }

    public void setPurifyTds(Integer purifyTds) {
        this.purifyTds = purifyTds;
    }

    public Float getPurifyPh() {
        return purifyPh;
    }

    public void setPurifyPh(Float purifyPh) {
        this.purifyPh = purifyPh;
    }

    public Float getPurifyColor() {
        return purifyColor;
    }

    public void setPurifyColor(Float purifyColor) {
        this.purifyColor = purifyColor;
    }

    public Float getPurifyTrt() {
        return purifyTrt;
    }

    public void setPurifyTrt(Float purifyTrt) {
        this.purifyTrt = purifyTrt;
    }

    public Float getPurifyTbdt() {
        return purifyTbdt;
    }

    public void setPurifyTbdt(Float purifyTbdt) {
        this.purifyTbdt = purifyTbdt;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPurifyAmount() {
		return purifyAmount;
	}

	public void setPurifyAmount(Float purifyAmount) {
		this.purifyAmount = purifyAmount;
	}

	public BigDecimal getDeviceX() {
        return deviceX;
    }

    public void setDeviceX(BigDecimal deviceX) {
        this.deviceX = deviceX;
    }

    public BigDecimal getDeviceY() {
        return deviceY;
    }

    public void setDeviceY(BigDecimal deviceY) {
        this.deviceY = deviceY;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc == null ? null : deviceDesc.trim();
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }
}