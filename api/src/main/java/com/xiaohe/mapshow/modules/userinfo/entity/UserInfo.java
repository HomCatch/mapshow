package com.xiaohe.mapshow.modules.userinfo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Entity
@Table(name = "hxcl_user_info")
@DynamicInsert
@DynamicUpdate
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@Id
	@Excel(name = "用户ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 性别0女1男
	 */
	@Excel(name = "性别(0女1男)")
	private Integer sex;
	/**
	 * 出生年份
	 */
	@Excel(name = "出生年份")
	private String birth;

	/**
	 * 电话号码
	 */
	@Excel(name = "电话号码")
	private String phoneNumber;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 令牌
	 */
	private String token;
	/**
	 * 注册时间
	 */
	@Excel(name = "注册时间")
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date registeredTime;
	/**
	 * 修改时间
	 */
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date modifiedTime;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 上次登录时间
	 */
	@Excel(name = "上次登录时间")
	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastLoginTime;
	private String lastLoginOs;
	private Integer lastLoginSdkVersion;
	/**
	 * 个人头像
	 */
	private String imgUrl;
	/**
	 * 个人昵称
	 */
	@Excel(name = "个人昵称")
	private String nickName;
	/**
	 * 小区ID
	 */
	private Integer communityId;
	/**
	 * 用户积分数
	 */
	private Integer bonusPoint;
	/**
	 * 是否认证
	 */
	private Integer isAuthorized;
	/**
	 * 折扣数量
	 */
	private Integer discount;
	/**
	 * 用户拥有水数量
	 */
	private Integer waterBottles;
	/**
	 * 月费抵扣券
	 */
	private Integer couponCount;
	/**
	 * 累计使用抵扣券
	 */
	private Integer totalCoupon;
	private String agentNo;
	/**
	 * 注册时经纬度
	 */
	private Integer amapId;
	/**
	 * 引导状态,0:未引导，1已引导
	 */
	private String leadStatus;
	/**
	 * 介绍人
	 */
	private Integer sponsor;
	/**
	 * 第三方昵称
	 */
	private String thirdNickname;
	/**
	 * 第三方头像
	 */
	private String thirdPortrait;
	/**
	 * 第三方来源（1：qq,2:weixin,3:weibo）
	 */
	private Integer thirdSource;
	/**
	 * 第三方用户id
	 */
	private String thirdUserId;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 出生地
	 */
	private String birthland;
	/**
	 * 居住地
	 */
	private String livePlace;
	/**
	 * 博主简介（用户简介）
	 */
	private String profile;
	/**
	 * 是否是博主用户
	 */
	private Integer isBlogger;
	/**
	 * 第三方qq昵称
	 */
	private String thirdQqNickname;
	/**
	 * 第三方qq头像
	 */
	private String thirdQqPortrait;
	/**
	 * 第三方qq用户id
	 */
	private String thirdQqUserId;
	/**
	 * 第三方weixin昵称
	 */
	private String thirdWeixinNickname;
	/**
	 * 第三方weixin头像
	 */
	private String thirdWeixinPortrait;
	/**
	 * 第三方weixin用户id
	 */
	private String thirdWeixinUserId;
	/**
	 * 第三方weibo昵称
	 */
	private String thirdWeiboNickname;
	/**
	 * 第三方weibo头像
	 */
	private String thirdWeiboPortrait;
	/**
	 * 第三方weibo用户id
	 */
	private String thirdWeiboUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getRegisteredTime() {
		return registeredTime;
	}

	public void setRegisteredTime(Date registeredTime) {
		this.registeredTime = registeredTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginOs() {
		return lastLoginOs;
	}

	public void setLastLoginOs(String lastLoginOs) {
		this.lastLoginOs = lastLoginOs;
	}

	public Integer getLastLoginSdkVersion() {
		return lastLoginSdkVersion;
	}

	public void setLastLoginSdkVersion(Integer lastLoginSdkVersion) {
		this.lastLoginSdkVersion = lastLoginSdkVersion;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public Integer getBonusPoint() {
		return bonusPoint;
	}

	public void setBonusPoint(Integer bonusPoint) {
		this.bonusPoint = bonusPoint;
	}

	public Integer getIsAuthorized() {
		return isAuthorized;
	}

	public void setIsAuthorized(Integer isAuthorized) {
		this.isAuthorized = isAuthorized;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Integer getWaterBottles() {
		return waterBottles;
	}

	public void setWaterBottles(Integer waterBottles) {
		this.waterBottles = waterBottles;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public Integer getTotalCoupon() {
		return totalCoupon;
	}

	public void setTotalCoupon(Integer totalCoupon) {
		this.totalCoupon = totalCoupon;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public Integer getAmapId() {
		return amapId;
	}

	public void setAmapId(Integer amapId) {
		this.amapId = amapId;
	}

	public String getLeadStatus() {
		return leadStatus;
	}

	public void setLeadStatus(String leadStatus) {
		this.leadStatus = leadStatus;
	}

	public Integer getSponsor() {
		return sponsor;
	}

	public void setSponsor(Integer sponsor) {
		this.sponsor = sponsor;
	}

	public String getThirdNickname() {
		return thirdNickname;
	}

	public void setThirdNickname(String thirdNickname) {
		this.thirdNickname = thirdNickname;
	}

	public String getThirdPortrait() {
		return thirdPortrait;
	}

	public void setThirdPortrait(String thirdPortrait) {
		this.thirdPortrait = thirdPortrait;
	}

	public Integer getThirdSource() {
		return thirdSource;
	}

	public void setThirdSource(Integer thirdSource) {
		this.thirdSource = thirdSource;
	}

	public String getThirdUserId() {
		return thirdUserId;
	}

	public void setThirdUserId(String thirdUserId) {
		this.thirdUserId = thirdUserId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthland() {
		return birthland;
	}

	public void setBirthland(String birthland) {
		this.birthland = birthland;
	}

	public String getLivePlace() {
		return livePlace;
	}

	public void setLivePlace(String livePlace) {
		this.livePlace = livePlace;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Integer getIsBlogger() {
		return isBlogger;
	}

	public void setIsBlogger(Integer isBlogger) {
		this.isBlogger = isBlogger;
	}

	public String getThirdQqNickname() {
		return thirdQqNickname;
	}

	public void setThirdQqNickname(String thirdQqNickname) {
		this.thirdQqNickname = thirdQqNickname;
	}

	public String getThirdQqPortrait() {
		return thirdQqPortrait;
	}

	public void setThirdQqPortrait(String thirdQqPortrait) {
		this.thirdQqPortrait = thirdQqPortrait;
	}

	public String getThirdQqUserId() {
		return thirdQqUserId;
	}

	public void setThirdQqUserId(String thirdQqUserId) {
		this.thirdQqUserId = thirdQqUserId;
	}

	public String getThirdWeixinNickname() {
		return thirdWeixinNickname;
	}

	public void setThirdWeixinNickname(String thirdWeixinNickname) {
		this.thirdWeixinNickname = thirdWeixinNickname;
	}

	public String getThirdWeixinPortrait() {
		return thirdWeixinPortrait;
	}

	public void setThirdWeixinPortrait(String thirdWeixinPortrait) {
		this.thirdWeixinPortrait = thirdWeixinPortrait;
	}

	public String getThirdWeixinUserId() {
		return thirdWeixinUserId;
	}

	public void setThirdWeixinUserId(String thirdWeixinUserId) {
		this.thirdWeixinUserId = thirdWeixinUserId;
	}

	public String getThirdWeiboNickname() {
		return thirdWeiboNickname;
	}

	public void setThirdWeiboNickname(String thirdWeiboNickname) {
		this.thirdWeiboNickname = thirdWeiboNickname;
	}

	public String getThirdWeiboPortrait() {
		return thirdWeiboPortrait;
	}

	public void setThirdWeiboPortrait(String thirdWeiboPortrait) {
		this.thirdWeiboPortrait = thirdWeiboPortrait;
	}

	public String getThirdWeiboUserId() {
		return thirdWeiboUserId;
	}

	public void setThirdWeiboUserId(String thirdWeiboUserId) {
		this.thirdWeiboUserId = thirdWeiboUserId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}
}