package com.xiaohe.mapshow.modules.devicestatic.entity;

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
@Table(name = "device_static")
@DynamicInsert
@DynamicUpdate
public class DeviceStatic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private Integer userId;

    private Integer activeDeviceCount;

    private Integer onlineCount;

    private Integer offlineCount;

    private Integer activeFilterCount;

    private Integer maturityFilterCount;

    private Integer faultDeviceCount;

    private Integer addDeviceCount;

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

    public Integer getActiveDeviceCount() {
        return activeDeviceCount;
    }

    public void setActiveDeviceCount(Integer activeDeviceCount) {
        this.activeDeviceCount = activeDeviceCount;
    }

    public Integer getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    public Integer getOfflineCount() {
        return offlineCount;
    }

    public void setOfflineCount(Integer offlineCount) {
        this.offlineCount = offlineCount;
    }

    public Integer getActiveFilterCount() {
        return activeFilterCount;
    }

    public void setActiveFilterCount(Integer activeFilterCount) {
        this.activeFilterCount = activeFilterCount;
    }

    public Integer getMaturityFilterCount() {
        return maturityFilterCount;
    }

    public void setMaturityFilterCount(Integer maturityFilterCount) {
        this.maturityFilterCount = maturityFilterCount;
    }

    public Integer getFaultDeviceCount() {
        return faultDeviceCount;
    }

    public void setFaultDeviceCount(Integer faultDeviceCount) {
        this.faultDeviceCount = faultDeviceCount;
    }

    public Integer getAddDeviceCount() {
        return addDeviceCount;
    }

    public void setAddDeviceCount(Integer addDeviceCount) {
        this.addDeviceCount = addDeviceCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}