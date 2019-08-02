package com.iswater.map.pojos;



public class User {
    private Integer id;
    private String name;
    private String pwd;
    private Integer intervals = 30; //页面刷新时间
    
  
	public Integer getIntervals() {
		return intervals;
	}
	public void setIntervals(Integer intervals) {
		this.intervals = intervals;
	}
	private Integer region_id;
    
    private String salt;
    
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public Integer getRegion_id() {
		return region_id;
	}
	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}