package com.xiaohe.mapshow.modules.filterplan.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 *
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */


public class QueryFilterPlan implements Serializable {


	private Integer id;
	private String deviceId;
	/**
	 * 上次滤芯更换时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date previousTime;
	/**
	 * 上次滤芯更换备注
	 */
	private String previousRemark;
	/**
	 * 下次滤芯更换时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date nextTime;
	/**
	 * 下次滤芯更换备注
	 */
	private String nextRemark;
	/**
	 * 记录时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	private Date updateTime;

	private Integer page = 1;

	private Integer pageSize = 10;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
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
		this.deviceId = deviceId;
	}

	public Date getPreviousTime() {
		return previousTime;
	}

	public void setPreviousTime(Date previousTime) {
		this.previousTime = previousTime;
	}

	public String getPreviousRemark() {
		return previousRemark;
	}

	public void setPreviousRemark(String previousRemark) {
		this.previousRemark = previousRemark;
	}

	public Date getNextTime() {
		return nextTime;
	}

	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}

	public String getNextRemark() {
		return nextRemark;
	}

	public void setNextRemark(String nextRemark) {
		this.nextRemark = nextRemark;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}