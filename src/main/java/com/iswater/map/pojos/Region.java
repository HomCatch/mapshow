package com.iswater.map.pojos;

import java.math.BigDecimal;
import java.util.Date;

import com.iswater.common.utils.CommonUtil;

public class Region {
    private Integer regionId;

    private String regionName;

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

    private String regionDesc;

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
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
    	Float newColor = CommonUtil.formatFloat4(color, 4);
        return newColor;
    }

    public void setColor(Float color) {
    	Float newColor = CommonUtil.formatFloat4(color, 4);
        this.color = newColor;
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

    public String getRegionDesc() {
        return regionDesc;
    }

    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc == null ? null : regionDesc.trim();
    }
}