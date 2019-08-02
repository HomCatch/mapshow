package com.iswater.map.pojos;

public class DashBoardStartUpPara {
	
	private Integer interval; //页面刷新时间， //毫秒
	private Integer deviceCount;

	private Integer evaluate;
	private Boolean hasAmountProbe; //拥有水质探头
	private Float dayAmount;
	private Float weekAmount;
	private Float monthAmount;
	private String devceNo;
	
	
	public String getDevceNo() {
		return devceNo;
	}

	public void setDevceNo(String devceNo) {
		this.devceNo = devceNo;
	}

	public Float getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(Float dayAmount) {
		this.dayAmount = dayAmount;
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

	public Boolean getHasAmountProbe() {
		return hasAmountProbe;
	}

	public void setHasAmountProbe(Boolean hasAmountProbe) {
		this.hasAmountProbe = hasAmountProbe;
	}

	public Integer getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}

	public Integer getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	
	
}
