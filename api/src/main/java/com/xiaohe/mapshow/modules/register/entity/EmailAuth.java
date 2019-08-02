package com.xiaohe.mapshow.modules.register.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Gmq
 * @since 2019-07-02 09:45
 **/
@Entity
@Table(name = "email_auth")
@DynamicInsert
@DynamicUpdate
public class EmailAuth implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String email;
	private String authCode;
	private Date deadline;

	public EmailAuth() {
	}

	public EmailAuth(String email, String authCode, Date deadline) {
		this.email = email;
		this.authCode = authCode;
		this.deadline = deadline;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
