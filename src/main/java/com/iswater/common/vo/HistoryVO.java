package com.iswater.common.vo;



public class HistoryVO extends PageParamVO  {
	
	private String device_id;
	
	private Integer binded_user_id; //对应的用户ID

	public Integer getBinded_user_id() {
		return binded_user_id;
	}

	public void setBinded_user_id(Integer binded_user_id) {
		this.binded_user_id = binded_user_id;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	

	
}
