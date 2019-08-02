package com.xiaohe.mapshow.modules.filterlist.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Entity
@Table(name = "filter_list")
@DynamicInsert
@DynamicUpdate
public class FilterList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "")
    private String deviceId;
    @Excel(name = "")
    private String firstFilter;
    @Excel(name = "")
    private String secondFilter;
    @Excel(name = "")
    private String thirdFilter;
    @Excel(name = "")
    private String fourthFilter;
    @Excel(name = "")
    private String cust;
    @Excel(name = "")
    private String address;
    @Excel(name = "")
    private String tel;
    @Excel(name = "")
    private String repairer;
    @Excel(name = "")
    private Integer repairerPhoneNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFirstFilter() {
        return firstFilter;
    }

    public void setFirstFilter(String firstFilter) {
        this.firstFilter = firstFilter;
    }

    public String getSecondFilter() {
        return secondFilter;
    }

    public void setSecondFilter(String secondFilter) {
        this.secondFilter = secondFilter;
    }

    public String getThirdFilter() {
        return thirdFilter;
    }

    public void setThirdFilter(String thirdFilter) {
        this.thirdFilter = thirdFilter;
    }

    public String getFourthFilter() {
        return fourthFilter;
    }

    public void setFourthFilter(String fourthFilter) {
        this.fourthFilter = fourthFilter;
    }

    public String getCust() {
        return cust;
    }

    public void setCust(String cust) {
        this.cust = cust;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRepairer() {
        return repairer;
    }

    public void setRepairer(String repairer) {
        this.repairer = repairer;
    }

    public Integer getRepairerPhoneNumber() {
        return repairerPhoneNumber;
    }

    public void setRepairerPhoneNumber(Integer repairerPhoneNumber) {
        this.repairerPhoneNumber = repairerPhoneNumber;
    }


}