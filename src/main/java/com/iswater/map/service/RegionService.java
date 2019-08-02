package com.iswater.map.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iswater.map.mapper.RegionMapper;
import com.iswater.map.pojos.Region;

@Service
public class RegionService {

	@Autowired
	private RegionMapper mapper;

	public Region getRegion(Integer regionId) {
		return mapper.selectByPrimaryKey(regionId);
	}
	
	public Region getRegionByName(String regionName) {
		return mapper.selectByName(regionName);
	}

	
	public Region getSumRegion(){
		return mapper.selectSumAll();
	}
	
	public List<Region> getRegionList() {
		return mapper.selectAll();
	}

	public void updateRegion(Region record) {
		record.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 新增34个省数据
	 * 
	 * @param json
	 */
	public void initRegion(String json) {
		JSONArray array = JSONArray.fromObject(json);
		if (array != null && array.size() > 0) {
			for (int i = 0; i < array.size(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String regionName = jsonObject.getString("region");
				BigDecimal x = new BigDecimal(jsonObject.getDouble("x"));
				BigDecimal y = new BigDecimal(jsonObject.getDouble("y"));

				Region record = new Region();
				record.setRegionName(regionName);
				record.setDeviceX(x);
				record.setDeviceY(y);
				record.setRegionDesc(regionName);
				record.setUpdateTime(new Date());
				mapper.insertSelective(record);
			}
		}
	}
}
