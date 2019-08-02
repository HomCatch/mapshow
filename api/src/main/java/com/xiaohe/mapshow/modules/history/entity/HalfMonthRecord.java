package com.xiaohe.mapshow.modules.history.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Gmq
 * @since 2019-04-11 18:51
 **/
@Entity
@Table(name = "half_month_record")
@DynamicInsert
@DynamicUpdate
public class HalfMonthRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;


	private String recordDate;
	@JsonProperty(value = "waterAmout")
	private Double waterAmount;

	private Integer userId;


	public HalfMonthRecord() {
	}

	public HalfMonthRecord(String recordDate, Double waterAmount, Integer userId) {
		this.recordDate = recordDate;
		this.waterAmount = waterAmount;
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
