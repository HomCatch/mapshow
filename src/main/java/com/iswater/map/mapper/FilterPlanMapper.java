package com.iswater.map.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iswater.common.vo.FilterPlanVO;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.FilterPlan;

public interface FilterPlanMapper {
	
	public Integer selectPageCount(FilterPlanVO paramVO);
	public List<FilterPlan> selectPage(FilterPlanVO paramVO);
	
	public FilterPlan getFilterPlanById(@Param("id")Integer id);
	
	public FilterPlan getFilterPlanByDevId(@Param("deviceId")String deviceId);
	
	public void saveFilterPlan(FilterPlan filterPlan);
	
	public void updateFilterPlan(FilterPlan filterPlan);
	
	public FilterPlan selectById(@Param("id")Integer id);
	
}







