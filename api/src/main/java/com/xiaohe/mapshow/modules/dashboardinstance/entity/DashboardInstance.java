package com.xiaohe.mapshow.modules.dashboardinstance.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.dashboardproperty.entity.DashboardProperty;
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

@Entity
@Table(name = "dashboard_instance")
@DynamicInsert
@DynamicUpdate
public class DashboardInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    @Transient
    private Object boardPosition;

    /**
     * 定位
     */
    @Excel(name = "定位")
    private String position;
    /**
     * 风格属性ID
     */
    @Excel(name = "风格属性ID")
    private Integer dashboardPropertyId;
    /**
     * 实例名称
     */
    @Excel(name = "实例名称")
    private String name;
    /**
     * 背景颜色
     */
    @Excel(name = "背景颜色")
    private String backgroundColor;
    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    @Transient
    private DashboardProperty dashboardProperty;
//    @Transient
//    private List<DashboardInstance> dashboardInstanceList;

    public Object getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(Object boardPosition) {
        this.boardPosition = boardPosition;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

//    public List<DashboardInstance> getDashboardInstanceList() {
//        return dashboardInstanceList;
//    }
//
//    public void setDashboardInstanceList(List<DashboardInstance> dashboardInstanceList) {
//        this.dashboardInstanceList = dashboardInstanceList;
//    }

    public DashboardProperty getDashboardProperty() {
        return dashboardProperty;
    }

    public void setDashboardProperty(DashboardProperty dashboardProperty) {
        this.dashboardProperty = dashboardProperty;
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