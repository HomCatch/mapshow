package com.xiaohe.mapshow.modules.filterplan.entity;

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
 * 
 * </p>
 *
 * @author gmq
 * @since 2019-03-29
 */

@Entity
@Table(name="filter_plan")
@DynamicInsert
@DynamicUpdate
public class FilterPlan implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @Excel(name = "滤芯编号",width = 20.00)
    private String deviceId;
    /**
     * 上次滤芯更换时间
     */
    @Excel(name = "上次滤芯更换时间", format = "yyyy-MM-dd HH:mm:ss",width = 20.00)
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date previousTime;
    /**
     * 上次滤芯更换备注
     */
    @Excel(name = "上次滤芯更换备注",width = 20.00)
    private String previousRemark;
    /**
     * 下次滤芯更换时间
     */
    @Excel(name = "下次滤芯更换时间", format = "yyyy-MM-dd HH:mm:ss",width = 20.00)
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date nextTime;
    /**
     * 下次滤芯更换备注
     */
    @Excel(name = "下次滤芯更换备注",width = 20.00)
    private String nextRemark;
    /**
     * 记录时间
     */
//    @Excel(name = "记录时间")
//    @Excel(name = "记录时间", format = "yyyy-MM-dd HH:mm:ss")
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getDeviceId() {
      return deviceId;
   }
   public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
   }
   public Date getPreviousTime() {
      return previousTime;
   }
   public void setPreviousTime(Date previousTime) {
      this.previousTime = previousTime;
   }
   public String getPreviousRemark() {
      return previousRemark;
   }
   public void setPreviousRemark(String previousRemark) {
      this.previousRemark = previousRemark;
   }
   public Date getNextTime() {
      return nextTime;
   }
   public void setNextTime(Date nextTime) {
      this.nextTime = nextTime;
   }
   public String getNextRemark() {
      return nextRemark;
   }
   public void setNextRemark(String nextRemark) {
      this.nextRemark = nextRemark;
   }
   public Date getUpdateTime() {
      return updateTime;
   }
   public void setUpdateTime(Date updateTime) {
      this.updateTime = updateTime;
   }


}