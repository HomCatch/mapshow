package com.xiaohe.mapshow.modules.history.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gmq
 * @since 2019-04-11 18:51
 **/
public class HalfMonthRecordVo implements Serializable {


	private String recordDate;

	private Double waterAmount;

	private Integer userId;


	public HalfMonthRecordVo() {
	}

	public HalfMonthRecordVo(String recordDate, Double waterAmount, Integer userId) {
		this.recordDate = recordDate;
		this.waterAmount = waterAmount;
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public Double getWaterAmount() {
		return waterAmount;
	}

	public void setWaterAmount(Double waterAmount) {
		this.waterAmount = waterAmount;
	}

}
