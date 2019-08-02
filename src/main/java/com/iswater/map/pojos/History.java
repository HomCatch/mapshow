package com.iswater.map.pojos;

import java.math.BigDecimal;
import java.util.Date;

import com.iswater.common.utils.CommonUtil;

public class History {
    private Integer historyId;

    private String deviceId;

    private String deviceName;

    private Date recordTime;
    
    private Long unixTime;
    private String strTime;
    private String lastTime = null;
    
    private Integer binded_user_id;
    
    
	private Integer tds = 10;

    private Float ph = 0.0f;

    private Float color = 0.0f; //色度

    private Float trt = 0.0f; //入水温度
  
    private Float tbdt = 0.0f; //入水浊度
    
    private Float amount = 0.0f ; //入水睡莲

    private Integer purifyTds = 0; //出水tds

    private Float purifyPh = 0.0f; //出水ph

    private Float purifyColor = 0.0f; //出水色度

    private Float purifyTrt = 0.0f; //出水温度

    private Float purifyTbdt = 0.0f; //出水浊度

    private Float purifyAmount = 0.0f; //出水水量上传值

	private Float purifyAmountBase = 0.0f; //出水水量基础值
    
    private Float purifyAmountTotal = 0.0f; //出水水量累计值
    
    private BigDecimal deviceX;

    private BigDecimal deviceY;

    private String deviceDesc;

    private Integer regionId;
    
   
    private Float todayAmount;
    private Float weekAmount;
    private Float monthAmount;
    


	public Integer getBinded_user_id() {
		return binded_user_id;
	}

	public void setBinded_user_id(Integer binded_user_id) {
		this.binded_user_id = binded_user_id;
	}

	public String getStrTime() {
		return strTime;
	}

	public void setStrTime(String strTime) {
		this.strTime = strTime;
	}

	public Float getPurifyAmountBase() {
		return purifyAmountBase;
	}

	public void setPurifyAmountBase(Float purifyAmountBase) {
		this.purifyAmountBase = purifyAmountBase;
	}

	public Float getPurifyAmountTotal() {
		return purifyAmountTotal;
	}

	public void setPurifyAmountTotal(Float purifyAmountTotal) {
		this.purifyAmountTotal = purifyAmountTotal;
	}
  
    
    public Float getTodayAmount() {
		return todayAmount;
	}

	public void setTodayAmount(Float todayAmount) {
		this.todayAmount = todayAmount;
	}

	public Float getWeekAmount() {
		return weekAmount;
	}

	public void setWeekAmount(Float weekAmount) {
		this.weekAmount = weekAmount;
	}

	public Float getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(Float monthAmount) {
		this.monthAmount = monthAmount;
	}

	public Long getUnixTime() {
		return unixTime;
	}

	public void setUnixTime(Long unixTime) {
		this.unixTime = unixTime;
	}

	 public String getlastDate() {
		 return lastTime;
	 }

	 public void setlastDate(String lastTime) {
		 this.lastTime = lastTime;
	 }
	    
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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

	@Override
	public String toString() {
		return "History [historyId=" + historyId + ", deviceId=" + deviceId + ", deviceName=" + deviceName
				+ ", recordTime=" + recordTime + ", unixTime=" + unixTime + ", strTime=" + strTime + ", binded_user_id="
				+ binded_user_id + ", tds=" + tds + ", ph=" + ph + ", color=" + color + ", trt=" + trt + ", tbdt="
				+ tbdt + ", amount=" + amount + ", purifyTds=" + purifyTds + ", purifyPh=" + purifyPh + ", purifyColor="
				+ purifyColor + ", purifyTrt=" + purifyTrt + ", purifyTbdt=" + purifyTbdt + ", purifyAmount="
				+ purifyAmount + ", purifyAmountBase=" + purifyAmountBase + ", purifyAmountTotal=" + purifyAmountTotal
				+ ", deviceX=" + deviceX + ", deviceY=" + deviceY + ", deviceDesc=" + deviceDesc + ", regionId="
				+ regionId + ", todayAmount=" + todayAmount + ", weekAmount=" + weekAmount + ", monthAmount="
				+ monthAmount + ", lastTime="+ lastTime + "]";
	}
    
    
    
    
    
}




