package com.xiaohe.mapshow.modules.userstatic.entity;

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


public class QueryUserStatic implements Serializable {


    private Integer id;
    private Integer userId;
    private Integer registCount;
    private Integer activeToday;
    private Integer activeWeek;
    private Integer activeMonth;
    private Integer addCount;
    private Integer totalCount;
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

    public Integer getRegistCount() {
        return registCount;
    }

    public void setRegistCount(Integer registCount) {
        this.registCount = registCount;
    }

    public Integer getActiveToday() {
        return activeToday;
    }

    public void setActiveToday(Integer activeToday) {
        this.activeToday = activeToday;
    }

    public Integer getActiveWeek() {
        return activeWeek;
    }

    public void setActiveWeek(Integer activeWeek) {
        this.activeWeek = activeWeek;
    }

    public Integer getActiveMonth() {
        return activeMonth;
    }

    public void setActiveMonth(Integer activeMonth) {
        this.activeMonth = activeMonth;
    }

    public Integer getAddCount() {
        return addCount;
    }

    public void setAddCount(Integer addCount) {
        this.addCount = addCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}