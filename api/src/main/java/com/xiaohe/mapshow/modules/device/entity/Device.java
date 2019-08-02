package com.xiaohe.mapshow.modules.device.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
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

@Entity
@Table(name = "device")
@DynamicInsert
@DynamicUpdate
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    /**
     * 该设备是否是特殊设备，特殊设备只是为了演示用，不必用真实值
     */
    @Excel(name = "该设备是否是特殊设备，特殊设备只是为了演示用，不必用真实值")
    private Integer special;
    /**
     * 一般情况下入水tds
     */
    @Excel(name = "一般情况下入水tds")
    private Integer generalInTds;
    /**
     * 一般情况色度
     */
    @Excel(name = "一般情况色度")
    private Float generalInSd;
    /**
     * 一般情况下浊度
     */
    @Excel(name = "一般情况下浊度")
    private Float generalInZd;
    /**
     * 是否拥有水量探头，0：没有，1：有
     */
    @Excel(name = "是否拥有水量探头，0：没有，1：有")
    private Integer hasAmountProbe;
    /**
     * 0:使用中，1：未使用
     */
    @Excel(name = "0:使用中，1：未使用")
    private Integer status;
    /**
     * 0:正常运行；1:故障
     */
    @Excel(name = "0:正常运行；1:故障")
    private Integer runState;
    /**
     * 该设备对应的代理商（用户）ID,默认值为1是小荷用户
     */
    @Excel(name = "该设备对应的代理商（用户）ID,默认值为1是小荷用户")
    private Integer bindedUserId;
    @Excel(name = "")
    private String deviceId;
    @Excel(name = "")
    private String deviceName;
    /**
     * 换芯提醒阀值
     */
    @Excel(name = "换芯提醒阀值")
    private String replaceFilterValue;
    /**
     * 是否要更换滤芯(1：需要更换，0：不需要更换)
     */
    @Excel(name = "是否要更换滤芯(1：需要更换，0：不需要更换)")
    private Integer whetherReplace;
    /**
     * total_amount
     */
    @Excel(name = "total_amount")
    private String totalAmount;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;
    /**
     * 记录时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private Date updateTime;
    /**
     * 记录时间
     */

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "记录时间")
    private Date insertDeviceTime;
    /**
     * 记录时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDeviceTime;
    /**
     * 设备上传数据的时间间隔，超过该间隔，则设备故障
     */
    @Excel(name = "设备上传数据的时间间隔，超过该间隔，则设备故障")
    private Integer recordInterval;
    /**
     * 净化前tds 值
     */
    @Excel(name = "净化前tds 值")
    private Integer tds;
    /**
     * 净化前ph 值
     */
    @Excel(name = "净化前ph 值")
    private Float ph;
    /**
     * 净化前色度
     */
    @Excel(name = "净化前色度")
    private Float color;
    /**
     * 净化前温度
     */
    @Excel(name = "净化前温度")
    private Float trt;
    /**
     * 净化前水浊度
     */
    @Excel(name = "净化前水浊度")
    private Float tbdt;
    /**
     * 净化后的tds
     */
    @Excel(name = "净化后的tds")
    private Integer purifyTds;
    /**
     * 净化后的ph 值
     */
    @Excel(name = "净化后的ph 值")
    private Float purifyPh;
    /**
     * 净化后的色度
     */
    @Excel(name = "净化后的色度")
    private Float purifyColor;
    /**
     * 净化后的温度
     */
    @Excel(name = "净化后的温度")
    private Float purifyTrt;
    /**
     * 净化后的水浊度
     */
    @Excel(name = "净化后的水浊度")
    private Float purifyTbdt;
    /**
     * 水量
     */
    @Excel(name = "水量")
    private Float amount;
    @Excel(name = "")
    @JsonProperty("deviceX")
    private BigDecimal device_x;
    @Excel(name = "")
    @JsonProperty("deviceY")
    private BigDecimal device_y;
    /**
     * 净化后水量
     */
    @Excel(name = "净化后水量")
    private Float purifyAmount;
    @Excel(name = "")
    private String province;
    @Excel(name = "")
    private String city;
    /**
     * 二级代理商ID
     */
    @Excel(name = "二级代理商ID")
    private Integer bindedSubUserId;
    /**
     * 设备状态数据
     */
    @Excel(name = "设备状态数据")
    private String statusData;

    @Excel(name = "所属经销商id")
    @Transient
    private String deptId;
    /**
     * 绑定时间
     */
    private Date bindTime;

    /**
     * 滤芯编号
     */
    private String filterCode;

    /**
     * 滤芯寿命
     */
    private String filterLifetime;

    /**
     * ip
     */
    private String ip;

    /**
     * 绑定设备账户
     */
    @Transient
    private String bindAccount;

    /**
     * 绑定状态0未绑定1已绑定
     */
    @Transient
    private Integer bindStatus;
    /**
     * app绑定用户ID
     */
    private Integer bindedAppId;

    public Integer getBindedAppId() {
        return bindedAppId;
    }

    public void setBindedAppId(Integer bindedAppId) {
        this.bindedAppId = bindedAppId;
    }

    public Integer getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(Integer bindStatus) {
        this.bindStatus = bindStatus;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

	public BigDecimal getDevice_x() {
		return device_x;
	}

	public void setDevice_x(BigDecimal device_x) {
		this.device_x = device_x;
	}

	public BigDecimal getDevice_y() {
		return device_y;
	}

	public void setDevice_y(BigDecimal device_y) {
		this.device_y = device_y;
	}

    public String getReplaceFilterValue() {
        return replaceFilterValue;
    }

    public void setReplaceFilterValue(String replaceFilterValue) {
        this.replaceFilterValue = replaceFilterValue;
    }

    public Integer getWhetherReplace() {
        return whetherReplace;
    }

    public void setWhetherReplace(Integer whetherReplace) {
        this.whetherReplace = whetherReplace;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getInsertDeviceTime() {
        return insertDeviceTime;
    }

    public void setInsertDeviceTime(Date insertDeviceTime) {
        this.insertDeviceTime = insertDeviceTime;
    }

    public Date getUpdateDeviceTime() {
        return updateDeviceTime;
    }

    public void setUpdateDeviceTime(Date updateDeviceTime) {
        this.updateDeviceTime = updateDeviceTime;
    }

    public Float getPurifyAmount() {
        return purifyAmount;
    }

    public void setPurifyAmount(Float purifyAmount) {
        this.purifyAmount = purifyAmount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getBindedSubUserId() {
        return bindedSubUserId;
    }

    public void setBindedSubUserId(Integer bindedSubUserId) {
        this.bindedSubUserId = bindedSubUserId;
    }

    public String getStatusData() {
        return statusData;
    }

    public void setStatusData(String statusData) {
        this.statusData = statusData;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public String getFilterCode() {
        return filterCode;
    }

    public void setFilterCode(String filterCode) {
        this.filterCode = filterCode;
    }

    public String getFilterLifetime() {
        return filterLifetime;
    }

    public void setFilterLifetime(String filterLifetime) {
        this.filterLifetime = filterLifetime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBindAccount() {
        return bindAccount;
    }

    public void setBindAccount(String bindAccount) {
        this.bindAccount = bindAccount;
    }
}