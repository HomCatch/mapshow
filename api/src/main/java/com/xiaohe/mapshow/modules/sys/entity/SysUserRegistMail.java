
package com.xiaohe.mapshow.modules.sys.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class SysUserRegistMail implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static String EMAIL_REG = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;

	/**
	 * 用户名
	 */
	@Pattern(message = "\"账号\"格式错误,请重新输入", regexp = EMAIL_REG)
	private String username;

	/**
	 * 密码
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "密码不能为空")
	private String password;
	/**
	 * 重复密码
	 */
	@Transient
	private String repPassword;


	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;


	/**
	 * 用户地址
	 */
	private String address;

	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 商户名
	 */
	private String mchName;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 授权码
	 */
	@Transient
	private String authcode;

	private String key;

	private String captcha;
	/**
	 * 验证码
	 */
	private String verifiCode;


	/**
	 * 账号类型 0手机1邮箱
	 */
	private Integer accountType;

	/**
	 * 公司名称
	 */
	@Transient
	private String  companyName;

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getVerifiCode() {
		return verifiCode;
	}

	public void setVerifiCode(String verifiCode) {
		this.verifiCode = verifiCode;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getRepPassword() {
		return repPassword;
	}

	public void setRepPassword(String repPassword) {
		this.repPassword = repPassword;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getMchName() {
		return mchName;
	}

	public void setMchName(String mchName) {
		this.mchName = mchName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * 设置：
	 *
	 * @param userId
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 *
	 * @return Long
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * 设置：用户名
	 *
	 * @param username 用户名
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 *
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置：密码
	 *
	 * @param password 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 *
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置：邮箱
	 *
	 * @param email 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 *
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置：状态  0：禁用   1：正常
	 *
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 *
	 * @return Integer
	 */
	public Integer getStatus() {
		return status;
	}


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}


}
