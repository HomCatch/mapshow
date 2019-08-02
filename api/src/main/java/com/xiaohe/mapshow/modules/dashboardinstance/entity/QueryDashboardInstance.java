package com.xiaohe.mapshow.modules.dashboardinstance.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 仪表盘实例
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */


public class QueryDashboardInstance implements Serializable {


    private Integer id;
    /**
     * 定位
     */
    private String position;
    /**
     * 风格属性ID
     */
    private Integer dashboardPropertyId;
    /**
     * 实例名称
     */
    private String name;
    private Long userId;
    /**
     * 背景颜色
     */
    private String backgroundColor;

    private Integer page = 1;

    private Integer pageSize = 10;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getDashboardPropertyId() {
        return dashboardPropertyId;
    }

    public void setDashboardPropertyId(Integer dashboardPropertyId) {
        this.dashboardPropertyId = dashboardPropertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }


}