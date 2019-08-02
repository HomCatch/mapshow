package com.xiaohe.mapshow.modules.register.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

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
 * @since 2019-04-22
 */

@Entity
@Table(name = "register")
@DynamicInsert
@DynamicUpdate
public class Register implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Excel(name = "编号")
	private Integer id;
	/**
	 * 用户ID
	 */
	@Excel(name = "用户ID")
	private Integer userId;
	private String phone;
	private Date createTime;

	public Register() {
	}

	public Register(Integer userId, String phone) {
		this.userId = userId;
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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


}