package com.xiaohe.mapshow.modules.filterlist.entity;

import java.io.Serializable;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */


public class QueryFilterList implements Serializable {


    private Integer id;
    private String deviceId;
    private String firstFilter;
    private String secondFilter;
    private String thirdFilter;
    private String fourthFilter;
    private String cust;
    private String address;
    private String tel;
    private String repairer;
    private Integer repairerPhoneNumber;

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