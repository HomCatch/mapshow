package com.xiaohe.mapshow.modules.history.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Gmq
 * @since 2019-04-04 13:59
 **/
public class HistoryBrokenLineVo implements Serializable {

	private String recordDate;
	/**
	 * 净化前TDS
	 */
	private BigDecimal tds;
	/**
	 * 净化后TDS
	 */
	private BigDecimal purifyTds;
	/**
	 * 净化前色度
	 */
	private Double color;
	/**
	 * 净化后色度
	 */
	private Double purifyColor;
	/**
	 * 净化后浊度
	 */
	private Double tbdt;
	/**
	 * 净化后浊度
	 */
	private Double purifyTbdt;

	private Integer userId;


	public HistoryBrokenLineVo() {
	}

	public HistoryBrokenLineVo(String recordDate, BigDecimal tds, BigDecimal purifyTds, Double color, Double purifyColor, Double tbdt, Double purifyTbdt, Integer userId) {
		this.recordDate = recordDate;
		this.tds = tds;
		this.purifyTds = purifyTds;
		this.color = color;
		this.purifyColor = purifyColor;
		this.tbdt = tbdt;
		this.purifyTbdt = purifyTbdt;
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

	public BigDecimal getTds() {
		return tds;
	}

	public void setTds(BigDecimal tds) {
		this.tds = tds;
	}

	public BigDecimal getPurifyTds() {
		return purifyTds;
	}

	public void setPurifyTds(BigDecimal purifyTds) {
		this.purifyTds = purifyTds;
	}

	public Double getColor() {
		return color;
	}

	public void setColor(Double color) {
		this.color = color;
	}

	public Double getPurifyColor() {
		return purifyColor;
	}

	public void setPurifyColor(Double purifyColor) {
		this.purifyColor = purifyColor;
	}

	public Double getTbdt() {
		return tbdt;
	}

	public void setTbdt(Double tbdt) {
		this.tbdt = tbdt;
	}

	public Double getPurifyTbdt() {
		return purifyTbdt;
	}

	public void setPurifyTbdt(Double purifyTbdt) {
		this.purifyTbdt = purifyTbdt;
	}

}
