package com.xiaohe.mapshow.modules.cloudwaterorder.entity;

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
 * @since 2019-04-19
 */

@Entity
@Table(name="cloud_water_order")
@DynamicInsert
@DynamicUpdate
public class CloudWaterOrder implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNumber;
    @Excel(name = "")
    private String deviceNumber;
    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String userName;
    /**
     * 注册号码
     */
    @Excel(name = "注册号码")
    private String phoneNumber;
    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名")
    private String realName;
    /**
     * 套餐类型
     */
    @Excel(name = "套餐类型")
    private Integer type;
    /**
     * 单价
     */
    @Excel(name = "单价")
    private BigDecimal price;
    /**
     * 共计水量
     */
    @Excel(name = "共计水量")
    private BigDecimal totalAmount;
    /**
     * 扣减水量
     */
    @Excel(name = "扣减水量")
    private BigDecimal minusAmount;
    /**
     * 账单金额
     */
    @Excel(name = "账单金额")
    private BigDecimal billPrice;
    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private Integer payType;
    /**
     * 支付金额
     */
    @Excel(name = "支付金额")
    private BigDecimal payPrice;
    /**
     * 第三方流水号
     */
    @Excel(name = "第三方流水号")
    private String thirdNumber;
    /**
     * 账单时间
     */
    @Excel(name = "账单时间", format = "yyyy-MM-dd HH:mm:ss")
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date billDate;
    /**
     * 支付时间
     */
    @Excel(name = "支付时间", format = "yyyy-MM-dd HH:mm:ss")
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payDate;
    /**
     * 创建时间
     */
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Transient
    private String startTime ;
    @Transient
    private String endTime;
    public String getStartTime() {
            return startTime;
            }

    public void setStartTime(String startTime) {
            this.startTime = startTime;
            }

    public String getEndTime() {
            return endTime;
            }

    public void setEndTime(String endTime) {
            this.endTime = endTime;
            }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getOrderNumber() {
      return orderNumber;
   }
   public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
   }
   public String getDeviceNumber() {
      return deviceNumber;
   }
   public void setDeviceNumber(String deviceNumber) {
      this.deviceNumber = deviceNumber;
   }
   public String getUserName() {
      return userName;
   }
   public void setUserName(String userName) {
      this.userName = userName;
   }
   public String getPhoneNumber() {
      return phoneNumber;
   }
   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }
   public String getRealName() {
      return realName;
   }
   public void setRealName(String realName) {
      this.realName = realName;
   }
   public Integer getType() {
      return type;
   }
   public void setType(Integer type) {
      this.type = type;
   }
   public BigDecimal getPrice() {
      return price;
   }
   public void setPrice(BigDecimal price) {
      this.price = price;
   }
   public BigDecimal getTotalAmount() {
      return totalAmount;
   }
   public void setTotalAmount(BigDecimal totalAmount) {
      this.totalAmount = totalAmount;
   }
   public BigDecimal getMinusAmount() {
      return minusAmount;
   }
   public void setMinusAmount(BigDecimal minusAmount) {
      this.minusAmount = minusAmount;
   }
   public BigDecimal getBillPrice() {
      return billPrice;
   }
   public void setBillPrice(BigDecimal billPrice) {
      this.billPrice = billPrice;
   }
   public Integer getPayType() {
      return payType;
   }
   public void setPayType(Integer payType) {
      this.payType = payType;
   }
   public BigDecimal getPayPrice() {
      return payPrice;
   }
   public void setPayPrice(BigDecimal payPrice) {
      this.payPrice = payPrice;
   }
   public String getThirdNumber() {
      return thirdNumber;
   }
   public void setThirdNumber(String thirdNumber) {
      this.thirdNumber = thirdNumber;
   }
   public Date getBillDate() {
      return billDate;
   }
   public void setBillDate(Date billDate) {
      this.billDate = billDate;
   }
   public Date getPayDate() {
      return payDate;
   }
   public void setPayDate(Date payDate) {
      this.payDate = payDate;
   }
   public Date getCreateTime() {
      return createTime;
   }
   public void setCreateTime(Date createTime) {
      this.createTime = createTime;
   }


}