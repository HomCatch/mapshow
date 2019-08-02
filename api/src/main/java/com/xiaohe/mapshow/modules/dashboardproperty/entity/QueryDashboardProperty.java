package com.xiaohe.mapshow.modules.dashboardproperty.entity;

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


public class QueryDashboardProperty implements Serializable {


    private Integer id;
    /**
     * 统计图类型
     */
    private String type;
    /**
     * 风格数据
     */
    private String styleData;

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