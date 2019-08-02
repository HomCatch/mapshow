package com.xiaohe.mapshow.modules.waterstatic.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

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

@Entity
@Table(name = "water_static")
@DynamicInsert
@DynamicUpdate
public class WaterStatic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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

    private double useTime;

    private Integer inTds;

    private Integer outTds;

    private double inColor;

    private double outColor;

    private double intTbdt;

    private double outTbdt;

    private double intTemp;

    private double outTemp;

    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

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

    public double getUseTime() {
        return useTime;
    }

    public void setUseTime(double useTime) {
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

    public double getInColor() {
        return inColor;
    }

    public void setInColor(double inColor) {
        this.inColor = inColor;
    }

    public double getOutColor() {
        return outColor;
    }

    public void setOutColor(double outColor) {
        this.outColor = outColor;
    }

    public double getIntTbdt() {
        return intTbdt;
    }

    public void setIntTbdt(double intTbdt) {
        this.intTbdt = intTbdt;
    }

    public double getOutTbdt() {
        return outTbdt;
    }

    public void setOutTbdt(double outTbdt) {
        this.outTbdt = outTbdt;
    }

    public double getIntTemp() {
        return intTemp;
    }

    public void setIntTemp(double intTemp) {
        this.intTemp = intTemp;
    }

    public double getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(double outTemp) {
        this.outTemp = outTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}