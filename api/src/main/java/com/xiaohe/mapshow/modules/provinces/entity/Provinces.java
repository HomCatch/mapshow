package com.xiaohe.mapshow.modules.provinces.entity;

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
 * 省份信息表
 * </p>
 *
 * @author hzh
 * @since 2019-04-08
 */

@Entity
@Table(name="provinces")
@DynamicInsert
@DynamicUpdate
public class Provinces implements Serializable {

    private static final long serialVersionUID = 1L;

  	@Id  	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Excel(name = "编号")
    private Integer id;
    @Excel(name = "")
    private String provinceid;
    @Excel(name = "")
    private String province;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

   public String getProvinceid() {
      return provinceid;
   }
   public void setProvinceid(String provinceid) {
      this.provinceid = provinceid;
   }
   public String getProvince() {
      return province;
   }
   public void setProvince(String province) {
      this.province = province;
   }


}