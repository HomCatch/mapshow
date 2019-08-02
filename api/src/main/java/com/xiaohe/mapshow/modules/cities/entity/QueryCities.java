package com.xiaohe.mapshow.modules.cities.entity;

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


public class QueryCities implements Serializable {


                        private Integer id;
                            private String cityid;
                            private String city;
                            private String provinceid;
        
private Integer page=1;

private Integer pageSize=10;
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