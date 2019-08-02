package com.xiaohe.mapshow.modules.changelist.entity;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */


public class QueryChangeList implements Serializable {


    private Integer id;
    /**
     * 设备号
     */
    private String deviceId;
    /**
     * 客户
     */
    private String customer;
    /**
     * 手机号
     */
    private String phoneNumber;
    /**
     * 0：更换--1：不更换
     */
    private Integer replaceFirstFilter;
    /**
     * 0：更换--1：不更换
     */
    private Integer replaceSecondFilter;
    /**
     * 0：更换--1：不更换
     */
    private Integer replaceThirdFilter;
    /**
     * 0：更换--1：不更换
     */
    private Integer replaceFouthFilter;
    /**
     * 需要更换时间
     */
    private String planReplaceTime;
    /**
     * 更换时间
     */
    private String realReplaceTime;
    /**
     * 地址
     */
    private String address;
    /**
     * 是否已更换(0：已换，1：未换）
     */
    private Integer replaceFinshed;
    private String repairer;
    private Integer repairerPhoneNumber;
    private String remark;

    private Integer page = 1;

    private Integer pageSize = 10;
    private String month;
    private String year;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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