package com.xiaohe.mapshow.modules.cloudwaterdeposit.entity;

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
@Table(name="cloud_water_deposit")
@DynamicInsert
@DynamicUpdate
public class CloudWaterDeposit implements Serializable {

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
    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String userName;
    /**
     * 注册手机
     */
    @Excel(name = "注册手机")
    private String phoneNumber;
    /**
     * 收货人
     */
    @Excel(name = "收货人")
    private String receiver;
    /**
     * 收货地址
     */
    @Excel(name = "收货地址")
    private String receivAddress;
    /**
     * 收货手机号
     */
    @Excel(name = "收货手机号")
    private String receivPhoneNumber;
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
     * 支付方式
     */
    @Excel(name = "支付方式")
    private Integer payType;
    /**
     * 押金
     */
    @Excel(name = "押金")
    private BigDecimal deposit;
    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    private Integer orderType;
    /**
     * 下单时间
     */
    @Excel(name = "下单时间", format = "yyyy-MM-dd HH:mm:ss")
 	@Temporal(value = TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderDate;
    /**
     * 第三方流水号
     */
    @Excel(name = "第三方流水号")
    private String thirdNumber;
    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
    private String deviceId;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getOrderNumber() {
      return orderNumber;
   }
   public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
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
   public String getReceiver() {
      return receiver;
   }
   public void setReceiver(String receiver) {
      this.receiver = receiver;
   }
   public String getReceivAddress() {
      return receivAddress;
   }
   public void setReceivAddress(String receivAddress) {
      this.receivAddress = receivAddress;
   }
   public String getReceivPhoneNumber() {
      return receivPhoneNumber;
   }
   public void setReceivPhoneNumber(String receivPhoneNumber) {
      this.receivPhoneNumber = receivPhoneNumber;
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
   public Integer getPayType() {
      return payType;
   }
   public void setPayType(Integer payType) {
      this.payType = payType;
   }
   public BigDecimal getDeposit() {
      return deposit;
   }
   public void setDeposit(BigDecimal deposit) {
      this.deposit = deposit;
   }
   public Integer getOrderType() {
      return orderType;
   }
   public void setOrderType(Integer orderType) {
      this.orderType = orderType;
   }
   public Date getOrderDate() {
      return orderDate;
   }
   public void setOrderDate(Date orderDate) {
      this.orderDate = orderDate;
   }
   public String getThirdNumber() {
      return thirdNumber;
   }
   public void setThirdNumber(String thirdNumber) {
      this.thirdNumber = thirdNumber;
   }
   public String getDeviceId() {
      return deviceId;
   }
   public void setDeviceId(String deviceId) {
      this.deviceId = deviceId;
   }


}