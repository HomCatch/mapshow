package com.xiaohe.mapshow.modules.history.entity;

import java.math.BigDecimal;

/**
 * @program: mapShow
 * @description: HistoryRecord
 * @author: Gmq
 * @date: 2019-04-03 15:11
 **/
public class HistoryRecord {
    private String recordDate;
    private BigDecimal waterAmout;
    private String deviceId;

    public HistoryRecord() {
    }

    public HistoryRecord(String recordDate, BigDecimal waterAmout, String deviceId) {
        this.recordDate = recordDate;
        this.waterAmout = waterAmout;
        this.deviceId = deviceId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public BigDecimal getWaterAmout() {
        return waterAmout;
    }

    public void setWaterAmout(BigDecimal waterAmout) {
        this.waterAmout = waterAmout;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
