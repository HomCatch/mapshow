package com.xiaohe.mapshow.modules.dashboardproperty.entity;

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
 * 仪表盘属性
 * </p>
 *
 * @author gmq
 * @since 2019-04-01
 */

@Entity
@Table(name = "dashboard_property")
@DynamicInsert
@DynamicUpdate
public class DashboardProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 统计图类型
     */
    @Excel(name = "统计图类型")
    private String type;
    /**
     * 风格数据
     */
    @Excel(name = "风格数据")
    private String styleData;
    /**
     * 数据模型
     * 0演示1真实
     */
    private Integer dataModel;

    @Transient
    private Object styleJson;

    public Integer getDataModel() {
        return dataModel;
    }

    public void setDataModel(Integer dataModel) {
        this.dataModel = dataModel;
    }

    public Object getStyleJson() {
        return styleJson;
    }

    public void setStyleJson(Object styleJson) {
        this.styleJson = styleJson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStyleData() {
        return styleData;
    }

    public void setStyleData(String styleData) {
        this.styleData = styleData;
    }


}