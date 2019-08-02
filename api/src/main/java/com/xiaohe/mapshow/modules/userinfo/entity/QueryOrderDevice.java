package com.xiaohe.mapshow.modules.userinfo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */


public class QueryOrderDevice implements Serializable {


    /**
     * 主键
     */
    private Integer id;
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 绑定时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signTime;
    /**
     * 剩余期数
     */
    private Integer remainStage;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 运行状态
     */
    private Integer runState;
    /**
     * 运行年份
     */
    private Integer year;
    /**
     * 运行月份
     */
    private Integer month;
    /**
     * 设备编号
     */
    private String deviceNo;
    private Integer scheme;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;
    private String iccid;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date coreExchangeTime;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operTime;
    /**
     * 安装地经纬度
     */
    private Integer amapId;
    /**
     * 共享用户的编号，以，隔开
     */
    private String shareUsers;

    private Integer page = 1;

    private Integer pageSize = 10;

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getRemainStage() {
        return remainStage;
    }

    public void setRemainStage(Integer remainStage) {
        this.remainStage = remainStage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Integer getScheme() {
        return scheme;
    }

    public void setScheme(Integer scheme) {
        this.scheme = scheme;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public Date getCoreExchangeTime() {
        return coreExchangeTime;
    }

    public void setCoreExchangeTime(Date coreExchangeTime) {
        this.coreExchangeTime = coreExchangeTime;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public Integer getAmapId() {
        return amapId;
    }

    public void setAmapId(Integer amapId) {
        this.amapId = amapId;
    }

    public String getShareUsers() {
        return shareUsers;
    }

    public void setShareUsers(String shareUsers) {
        this.shareUsers = shareUsers;
    }


}