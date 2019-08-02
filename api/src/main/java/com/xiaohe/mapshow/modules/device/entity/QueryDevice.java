package com.xiaohe.mapshow.modules.device.entity;

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
 * @since 2019-03-28
 */


public class QueryDevice implements Serializable {


    private Integer id;
    /**
     * 是否是特殊设备，特殊设备只是为了演示，不一定用真实的数据
     */
    private Integer special;
    /**
     * 一般情况下入水tds值，要根据当地的值确定
     */
    private Integer generalInTds;
    /**
     * 一般情况色度
     */
    private Float generalInSd;
    /**
     * 一般情况浊度
     */
    private Float generalInZd;
    /**
     * 是否拥有水质探头，1：有，0：无
     */
    private Integer hasAmountProbe;
    /**
     * 0:管理中，1：申请管理中
     */
    private Integer status;
    /**
     * 设备运行状态
     */
    private Integer runState;
    /**
     * 设备上传数据时间间隔
     */
    private Integer recordInterval;
    /**
     * 该设备对应的代理商（用户）ID
     */
    private Integer bindedUserId;
    private String deviceId;
    private String deviceName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 记录时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    /**
     * 净化前tds 值
     */
    private Integer tds;
    /**
     * 净化前ph 值
     */
    private Float ph;
    /**
     * 净化前色度
     */
    private Float color;
    /**
     * 净化前温度
     */
    private Float trt;
    /**
     * 净化前水浊度
     */
    private Float tbdt;
    /**
     * 净化后的tds
     */
    private Integer purifyTds;
    /**
     * 净化后的ph 值
     */
    private Float purifyPh;
    /**
     * 净化后的色度
     */
    private Float purifyColor;
    /**
     * 净化后的温度
     */
    private Float purifyTrt;
    /**
     * 净化后的水浊度
     */
    private Float purifyTbdt;
    /**
     * 水量
     */
    private Float amount;
    private BigDecimal deviceX;
    private BigDecimal deviceY;
    private String deviceDesc;
    /**
     * 区域
     */
    private Integer regionId;
    /**
     * 净化后水量
     */
    private Float purifyAmount;
    /**
     * 0:ERP数据；1:UdpServer数据
     */
    private Integer dataSource;
    private String province;

    private String bindAccount;

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

    public Integer getSpecial() {
        return special;
    }

    public void setSpecial(Integer special) {
        this.special = special;
    }

    public Integer getGeneralInTds() {
        return generalInTds;
    }

    public void setGeneralInTds(Integer generalInTds) {
        this.generalInTds = generalInTds;
    }

    public Float getGeneralInSd() {
        return generalInSd;
    }

    public void setGeneralInSd(Float generalInSd) {
        this.generalInSd = generalInSd;
    }

    public Float getGeneralInZd() {
        return generalInZd;
    }

    public void setGeneralInZd(Float generalInZd) {
        this.generalInZd = generalInZd;
    }

    public Integer getHasAmountProbe() {
        return hasAmountProbe;
    }

    public void setHasAmountProbe(Integer hasAmountProbe) {
        this.hasAmountProbe = hasAmountProbe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRunState() {
        return runState;
    }

    public void setRunState(Integer runState) {
        this.runState = runState;
    }

    public Integer getRecordInterval() {
        return recordInterval;
    }

    public void setRecordInterval(Integer recordInterval) {
        this.recordInterval = recordInterval;
    }

    public Integer getBindedUserId() {
        return bindedUserId;
    }

    public void setBindedUserId(Integer bindedUserId) {
        this.bindedUserId = bindedUserId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTds() {
        return tds;
    }

    public void setTds(Integer tds) {
        this.tds = tds;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Float getColor() {
        return color;
    }

    public void setColor(Float color) {
        this.color = color;
    }

    public Float getTrt() {
        return trt;
    }

    public void setTrt(Float trt) {
        this.trt = trt;
    }

    public Float getTbdt() {
        return tbdt;
    }

    public void setTbdt(Float tbdt) {
        this.tbdt = tbdt;
    }

    public Integer getPurifyTds() {
        return purifyTds;
    }

    public void setPurifyTds(Integer purifyTds) {
        this.purifyTds = purifyTds;
    }

    public Float getPurifyPh() {
        return purifyPh;
    }

    public void setPurifyPh(Float purifyPh) {
        this.purifyPh = purifyPh;
    }

    public Float getPurifyColor() {
        return purifyColor;
    }

    public void setPurifyColor(Float purifyColor) {
        this.purifyColor = purifyColor;
    }

    public Float getPurifyTrt() {
        return purifyTrt;
    }

    public void setPurifyTrt(Float purifyTrt) {
        this.purifyTrt = purifyTrt;
    }

    public Float getPurifyTbdt() {
        return purifyTbdt;
    }

    public void setPurifyTbdt(Float purifyTbdt) {
        this.purifyTbdt = purifyTbdt;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public BigDecimal getDeviceX() {
        return deviceX;
    }

    public void setDeviceX(BigDecimal deviceX) {
        this.deviceX = deviceX;
    }

    public BigDecimal getDeviceY() {
        return deviceY;
    }

    public void setDeviceY(BigDecimal deviceY) {
        this.deviceY = deviceY;
    }

    public String getDeviceDesc() {
        return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
        this.deviceDesc = deviceDesc;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Float getPurifyAmount() {
        return purifyAmount;
    }

    public void setPurifyAmount(Float purifyAmount) {
        this.purifyAmount = purifyAmount;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getBindAccount() {
        return bindAccount;
    }

    public void setBindAccount(String bindAccount) {
        this.bindAccount = bindAccount;
    }
}