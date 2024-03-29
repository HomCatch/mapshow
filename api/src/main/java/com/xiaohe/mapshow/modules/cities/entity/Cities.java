package com.xiaohe.mapshow.modules.cities.entity;

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
 * 行政区域地州市信息表
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Entity
@Table(name="cities")
@DynamicInsert
@DynamicUpdate
public class Cities implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "")
    private String cityid;
    @Excel(name = "")
    private String city;
    @Excel(name = "")
    private String provinceid;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getCityid() {
      return cityid;
   }
   public void setCityid(String cityid) {
      this.cityid = cityid;
   }
   public String getCity() {
      return city;
   }
   public void setCity(String city) {
      this.city = city;
   }
   public String getProvinceid() {
      return provinceid;
   }
   public void setProvinceid(String provinceid) {
      this.provinceid = provinceid;
   }


}