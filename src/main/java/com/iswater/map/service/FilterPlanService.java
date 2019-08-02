package com.iswater.map.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.filter.Filter;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.FilterPlanVO;
import com.iswater.common.vo.PageVO;
import com.iswater.map.mapper.DeviceMapper;
import com.iswater.map.mapper.FilterPlanMapper;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.FilterPlan;

@Service
public class FilterPlanService {

	@Autowired
	private FilterPlanMapper filterPlanMapper;
	
	
	
	public PageVO getFilterPlanList(FilterPlanVO paramVO) {
		int total = filterPlanMapper.selectPageCount(paramVO);
		List<FilterPlan> list = filterPlanMapper.selectPage(paramVO);
		Integer pageCurrent = paramVO.getPageCurrent();
		int pagesize = paramVO.getPagesize();
		int pageCount = 1;
		if (total % pagesize == 0) {
			pageCount = total / pagesize;
		} else {
			pageCount = total / pagesize + 1;
		}
		return new PageVO(pageCurrent, total, pageCount, list);
	}
	
	
	public FilterPlan getFilterPlanById(Integer id){
		return filterPlanMapper.getFilterPlanById(id);
	}
	
	public FilterPlan getFilterPlanByDevId(String deviceId){
		return filterPlanMapper.getFilterPlanByDevId(deviceId);
	}
	
	public boolean isPlanExits(String deviceId){
		if (null != filterPlanMapper.getFilterPlanByDevId(deviceId)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void saveFilterPlan(FilterPlan filterPlan){
		filterPlanMapper.saveFilterPlan(filterPlan);
	}
	
	public void updateFilterPlan(FilterPlan filterPlan){
		filterPlanMapper.updateFilterPlan(filterPlan);
	}
	
	
	
	
	
	
}
