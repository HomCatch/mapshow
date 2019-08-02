package com.xiaohe.mapshow.modules.register.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author Gmq
 * @since 2019-04-23 10:06
 **/
@Entity
@Table(name = "verifi_code_record")
@DynamicInsert
@DynamicUpdate
public class VerifiCodeRecord {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 手机号
	 */
	private String phone;

	private String verifiCode;

	public VerifiCodeRecord() {
	}

	public VerifiCodeRecord(String phone, String verifiCode) {
		this.phone = phone;
		this.verifiCode = verifiCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getVerifiCode() {
		return verifiCode;
	}

	public void setVerifiCode(String verifiCode) {
		this.verifiCode = verifiCode;
	}
}
