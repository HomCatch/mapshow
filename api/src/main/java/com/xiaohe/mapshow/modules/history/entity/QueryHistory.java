package com.xiaohe.mapshow.modules.history.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
 * @since 2019-03-29
 */


public class QueryHistory implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer historyId;
    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private String deviceId;
    @Excel(name = "")
    private String deviceName;
    /**
     * 历史记录时间
     */
//    @Excel(name = "历史记录时间")
//    @Excel(name = "历史记录时间", format = "yyyy-MM-dd HH:mm:ss")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date recordTime;
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
    /**
     * 净化后水量
     */
    @Excel(name = "净化后水量")
    private Float purifyAmount;
    /**
     * 净化后水量
     */
    @Excel(name = "净化后水量")
    private Float purifyAmountBase;
    /**
     * 净化后水量
     */
    @Excel(name = "净化后水量")
    private Float purifyAmountTotal;
    /**
     * 当日用水量
     */
    @Excel(name = "当日用水量")
    private Float todayAmount;
    /**
     * 周用水量
     */
    @Excel(name = "周用水量")
    private Float weekAmount;
    /**
     * 月用水量
     */
    @Excel(name = "月用水量")
    private Float monthAmount;
    @Excel(name = "")
    @JsonProperty("deviceX")
    private BigDecimal deviceX;
    @Excel(name = "")
    @JsonProperty("deviceY")
    private BigDecimal deviceY;
    @Excel(name = "")
    private String deviceDesc;
    /**
     * 区域
     */
    @Excel(name = "区域")
    private Integer regionId;
    /**
     * 绑定的经销商ID
     */
    @Excel(name = "绑定的经销商ID")
    private Integer bindedUserId;

    /**
     * 数据是否过滤，0：未过滤；1：已过虑
     */
    @Excel(name = "数据是否过滤，0：未过滤；1：已过虑")
    private Integer filtered;
    /**
     * 绑定的二级代理商ID
     */
    @Excel(name = "绑定的二级代理商ID")
    private Integer bindedSubUserId;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

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

    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
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

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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

    public Float getPurifyAmount() {
        return purifyAmount;
    }

    public void setPurifyAmount(Float purifyAmount) {
        this.purifyAmount = purifyAmount;
    }

    public Float getPurifyAmountBase() {
        return purifyAmountBase;
    }

    public void setPurifyAmountBase(Float purifyAmountBase) {
        this.purifyAmountBase = purifyAmountBase;
    }

    public Float getPurifyAmountTotal() {
        return purifyAmountTotal;
    }

    public void setPurifyAmountTotal(Float purifyAmountTotal) {
        this.purifyAmountTotal = purifyAmountTotal;
    }

    public Float getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Float todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Float getWeekAmount() {
        return weekAmount;
    }

    public void setWeekAmount(Float weekAmount) {
        this.weekAmount = weekAmount;
    }

    public Float getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Float monthAmount) {
        this.monthAmount = monthAmount;
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

    public Integer getBindedUserId() {
        return bindedUserId;
    }

    public void setBindedUserId(Integer bindedUserId) {
        this.bindedUserId = bindedUserId;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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

    public Integer getFiltered() {
        return filtered;
    }

    public void setFiltered(Integer filtered) {
        this.filtered = filtered;
    }

    public Integer getBindedSubUserId() {
        return bindedSubUserId;
    }

    public void setBindedSubUserId(Integer bindedSubUserId) {
        this.bindedSubUserId = bindedSubUserId;
    }
}