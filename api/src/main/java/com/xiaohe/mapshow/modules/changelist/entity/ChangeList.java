package com.xiaohe.mapshow.modules.changelist.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Entity
@Table(name="change_list")
@DynamicInsert
@DynamicUpdate
public class ChangeList implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    /**
     * 设备号
     */
    @Excel(name = "设备号")
    private String deviceId;
    /**
     * 客户
     */
    @Excel(name = "客户")
    private String customer;
    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phoneNumber;
    /**
     * 0：更换--1：不更换
     */
    @Excel(name = "0：更换--1：不更换")
    private Integer replaceFirstFilter;
    /**
     * 0：更换--1：不更换
     */
    @Excel(name = "0：更换--1：不更换")
    private Integer replaceSecondFilter;
    /**
     * 0：更换--1：不更换
     */
    @Excel(name = "0：更换--1：不更换")
    private Integer replaceThirdFilter;
    /**
     * 0：更换--1：不更换
     */
    @Excel(name = "0：更换--1：不更换")
    private Integer replaceFouthFilter;
    /**
     * 需要更换时间
     */
    @Excel(name = "需要更换时间")
    private String planReplaceTime;
    /**
     * 更换时间
     */
    @Excel(name = "更换时间")
    private String realReplaceTime;
    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;
    /**
     * 是否已更换(0：已换，1：未换）
     */
    @Excel(name = "是否已更换(1：已换，0：未换）")
    private Integer replaceFinshed;
    @Excel(name = "")
    private String repairer;
    @Excel(name = "")
    private Integer repairerPhoneNumber;
    @Excel(name = "")
    private String remark;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getDeviceId() {
      return deviceId;
   }
   public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
   }
   public String getCustomer() {
      return customer;
   }
   public void setCustomer(String customer) {
      this.customer = customer;
   }
   public String getPhoneNumber() {
      return phoneNumber;
   }
   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
   public Integer getReplaceFirstFilter() {
      return replaceFirstFilter;
   }
   public void setReplaceFirstFilter(Integer replaceFirstFilter) {
      this.replaceFirstFilter = replaceFirstFilter;
   }
   public Integer getReplaceSecondFilter() {
      return replaceSecondFilter;
   }
   public void setReplaceSecondFilter(Integer replaceSecondFilter) {
      this.replaceSecondFilter = replaceSecondFilter;
   }
   public Integer getReplaceThirdFilter() {
      return replaceThirdFilter;
   }
   public void setReplaceThirdFilter(Integer replaceThirdFilter) {
      this.replaceThirdFilter = replaceThirdFilter;
   }
   public Integer getReplaceFouthFilter() {
      return replaceFouthFilter;
   }
   public void setReplaceFouthFilter(Integer replaceFouthFilter) {
      this.replaceFouthFilter = replaceFouthFilter;
   }
   public String getPlanReplaceTime() {
      return planReplaceTime;
   }
   public void setPlanReplaceTime(String planReplaceTime) {
      this.planReplaceTime = planReplaceTime;
   }
   public String getRealReplaceTime() {
      return realReplaceTime;
   }
   public void setRealReplaceTime(String realReplaceTime) {
      this.realReplaceTime = realReplaceTime;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public Integer getReplaceFinshed() {
      return replaceFinshed;
   }
   public void setReplaceFinshed(Integer replaceFinshed) {
      this.replaceFinshed = replaceFinshed;
   }
   public String getRepairer() {
      return repairer;
   }
   public void setRepairer(String repairer) {
      this.repairer = repairer;
   }
   public Integer getRepairerPhoneNumber() {
      return repairerPhoneNumber;
   }
   public void setRepairerPhoneNumber(Integer repairerPhoneNumber) {
      this.repairerPhoneNumber = repairerPhoneNumber;
   }
   public String getRemark() {
      return remark;
   }
   public void setRemark(String remark) {
      this.remark = remark;
   }


}