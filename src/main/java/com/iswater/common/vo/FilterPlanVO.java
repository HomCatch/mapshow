package com.iswater.common.vo;



public class FilterPlanVO extends PageParamVO  {
	
	Integer user_id;
	
	Integer region_id;
	
	Integer binded_user_id;
	
	Integer run_state;
	

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getBinded_user_id() {
		return binded_user_id;
	}

	public void setBinded_user_id(Integer binded_user_id) {
		this.binded_user_id = binded_user_id;
	}

	public Integer getRegion_id() {
		return region_id;
	}

	public void setRegion_id(Integer region_id) {
		this.region_id = region_id;
	}

	public Integer getRun_state() {
		return run_state;
	}

	public void setRun_state(Integer run_state) {
		this.run_state = run_state;
	}
	
	
		
}
