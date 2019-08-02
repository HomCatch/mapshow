package com.xiaohe.mapshow.modules.waterstatic.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author wzq
 * @since 2019-06-04
 */


public class QueryWaterStatic implements Serializable {


    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用水量
     */
    private Integer waterAmount;
    /**
     * 用水频次
     */
    private Integer useCount;
    /**
     * 用水时长(Min)
     */
    private Integer useTime;
    private Integer inTds;
    private Integer outTds;
    private Integer inColor;
    private Integer outColor;
    private Integer intTbdt;
    private Integer outTbdt;
    private Integer intTemp;
    private Integer outTemp;
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWaterAmount() {
        return waterAmount;
    }

    public void setWaterAmount(Integer waterAmount) {
        this.waterAmount = waterAmount;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Integer getInTds() {
        return inTds;
    }

    public void setInTds(Integer inTds) {
        this.inTds = inTds;
    }

    public Integer getOutTds() {
        return outTds;
    }

    public void setOutTds(Integer outTds) {
        this.outTds = outTds;
    }

    public Integer getInColor() {
        return inColor;
    }

    public void setInColor(Integer inColor) {
        this.inColor = inColor;
    }

    public Integer getOutColor() {
        return outColor;
    }

    public void setOutColor(Integer outColor) {
        this.outColor = outColor;
    }

    public Integer getIntTbdt() {
        return intTbdt;
    }

    public void setIntTbdt(Integer intTbdt) {
        this.intTbdt = intTbdt;
    }

    public Integer getOutTbdt() {
        return outTbdt;
    }

    public void setOutTbdt(Integer outTbdt) {
        this.outTbdt = outTbdt;
    }

    public Integer getIntTemp() {
        return intTemp;
    }

    public void setIntTemp(Integer intTemp) {
        this.intTemp = intTemp;
    }

    public Integer getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(Integer outTemp) {
        this.outTemp = outTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}