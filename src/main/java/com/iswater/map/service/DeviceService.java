package com.iswater.map.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.iswater.common.utils.CommonUtil;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.PageVO;
import com.iswater.map.mapper.DeviceMapper;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.Region;

@Service
public class DeviceService {

	@Autowired
	private DeviceMapper deviceMapper;
	@Autowired
	private RegionService regionService;
	
	
	public PageVO getRecordHistoryList(DeviceMngVO paramVO) {
		int total = deviceMapper.selectPageCount(paramVO);
		List<Device> list = deviceMapper.selectPage(paramVO);
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
	
	public PageVO getBreakDownDevList(DeviceMngVO paramVO) {
		int total = deviceMapper.selectBreakDevPageCount(paramVO);
		List<Device> list = deviceMapper.selectBreakDevPage(paramVO);
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
	
	public PageVO getFilgerReplaceDevList(DeviceMngVO paramVO) {
		int total = deviceMapper.selectFilterDevPageCount(paramVO);
		List<Device> list = deviceMapper.selectFilterDevPage(paramVO);
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
	

	public List<Device> getDeviceList() {
		return deviceMapper.selectAll();
	}
	
	
	public List<Device> getMngingDeviceList() {
		return deviceMapper.selectAllMng();
	}

	public List<Device> getDeviceList(Integer regionId) {
		return deviceMapper.selectByRegionId(regionId);
	}
	
	
	public List<Device> getMngDeviceList(Integer regionId, int size) {
		return deviceMapper.selectMngDeviceByRegionId(regionId, size);
	}
	
	public List<Device> getDeviceList(Integer regionId, Integer userId, Integer status) {
		return deviceMapper.selectByRegionAndUser(regionId, userId, status);
	}
	
	public List<Device> getDeviceListByUser(Integer userId, Integer status) {
		return deviceMapper.selectByUserAndStatus(userId, status);
	}
	
	public List<Device> getDeviceListByRunState(Integer userId, Integer status, Integer runState) {
		return deviceMapper.selectByUserAndRunStateAndStatus(userId,  status, runState);
	}
	
	public List<Device> getDeviceListByUser(Integer userId) {
		return deviceMapper.selectByUser(userId);
	}
	
	public List<Device> getDeviceListByRunState(Integer userId, Integer runState) {
		return deviceMapper.selectByUserAndRunState(userId, runState);
	}
	
	public List<Device> getDevFilterReplace(Integer userId) {
		return deviceMapper.selectFilterReplace(userId);
	}


	public Device getSumDeviceByRegionId(Integer regionId) {
		Device device = null;
		Integer count = deviceMapper.selectCountByRegionId(regionId);
		if (count > 0) {
			HashMap<String, Object> params = Maps.newHashMap();
			params.put("count", count);
			params.put("regionId", regionId);
			device = deviceMapper.selectSumByRegionId(params);
		}
		return device;
	}

	public List<Device> getDeviceList(Integer regionId, Double xmin, Double xmax, Double ymin, Double ymax) {
		HashMap<String, Object> params = Maps.newHashMap();
		if (regionId != null) {
			params.put("regionId", regionId);
		}
		params.put("xmin", xmin);
		params.put("xmax", xmax);
		params.put("ymin", ymin);
		params.put("ymax", ymax);
		return deviceMapper.selectDeviceByPoint(params);
	}

	public Device getSumDeviceList(Integer regionId, Double xmin, Double xmax, Double ymin, Double ymax, Integer count) {
		HashMap<String, Object> params = Maps.newHashMap();
		if (regionId != null) {
			params.put("regionId", regionId);
		}
		params.put("count", count);
		params.put("xmin", xmin);
		params.put("xmax", xmax);
		params.put("ymin", ymin);
		params.put("ymax", ymax);
		return deviceMapper.selectSumByPoint(params);
	}

	public Device getDeviceByDeviceId(String deviceId) {
		return deviceMapper.selectByDeviceId(deviceId);
	}
	
	public Device getDeviceById(Integer id) {
		return deviceMapper.selectById(id);
	}
	
	public Device getDeviceByDeviceKey(Integer key) {
		return deviceMapper.selectByDeviceKey(key);
	}

	public void addDevice(Device record) {
		deviceMapper.insertSelective(record);
	}
	
	
	public void addDeviceShortyly(Device record) {
		deviceMapper.insertShortly(record);
	}

	public void updateDevice(Device record) {
		deviceMapper.updateByPrimaryKeySelective(record);
	}
	
	public void updateDevRunSate(Device device) {
		deviceMapper.updateRunState(device);
	}

	public void initDevice(String json) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String regionName = jsonObject.getString("region");
			JSONArray f_x = jsonObject.getJSONArray("x");
			JSONArray f_y = jsonObject.getJSONArray("y");

			double d_x1 = f_x.getDouble(0);
			double d_x2 = f_x.getDouble(1);
			double d_y1 = f_y.getDouble(0);
			double d_y2 = f_y.getDouble(1);
			// 生成多少个点
			int count = jsonObject.getInt("count");

			List<BigDecimal> x_list = CommonUtil.getRandomValue(d_x1, d_x2, count);
			List<BigDecimal> y_list = CommonUtil.getRandomValue(d_y1, d_y2, count);
			Region region = regionService.getRegionByName(regionName);
			if (region != null) {
				Integer regionId = region.getRegionId();
				for (int j = 0; j < x_list.size(); j++) {
					double x = x_list.get(j).doubleValue();
					double y = y_list.get(j).doubleValue();
					addDevice(regionId, regionName + "设备" + (j + 1), x, y);
				}
			}
		}

	}

	public void updateDevice(Integer regionId, Integer min, Integer max) {
		List<Device> devices = deviceMapper.selectByRegionId(regionId);
		for (Device device : devices) {
			Integer tds = CommonUtil.getRandomIntValue(min, max);
			Integer purifyTds = CommonUtil.getRandomIntValue(1, 5);
			device.setTds(tds);
			device.setPurifyTds(purifyTds);
			deviceMapper.updateByPrimaryKeySelective(device);
		}
	}

	/**
	 * 新增设备
	 * 
	 * @param regionId
	 * @param deviceName
	 * @param x
	 * @param y
	 */
	public void addDevice(Integer regionId, String deviceName, double x, double y) {
		
		String deviceId = getDeviceID("D");
		Device device = new Device();
		device.setDeviceName(deviceName);
		/**
		 * TDS 总溶解固体（英文：Total dissolved solids，缩写TDS），又称溶解性固体总量 测量单位为毫克/升（mg/L）
		 * 单位： mg/L 毫克每升 值范围： 净化前后对比：前>后
		 */
		Integer tds = CommonUtil.getRandomIntValue(100, 190);
		Integer purifyTds = CommonUtil.getRandomIntValue(1, 5);
		device.setTds(tds);
		device.setPurifyTds(purifyTds);
		/**
		 * 色度 单位： 无单位，该污水的色度是10,就表示把该污水稀释10倍,其透明度才能达到清水的透明度 值范围： 0~30 净化前后对比：前<后
		 */
		Float color = CommonUtil.getRandomValue(1f, 2.6f);
		Float purifyColor = CommonUtil.getRandomValue(0.3, 0.5);
		if (color - purifyColor > 0.5 || purifyColor - color > 0.5) {
			purifyColor = color + 0.5f;
		}
		device.setColor(Float.parseFloat(color.toString()));
		device.setPurifyColor(Float.parseFloat(purifyColor.toString()));

		/**
		 * 温度
		 */
		Float trt = CommonUtil.getRandomValue(15f, 30f);
		Float purifyTrt = CommonUtil.getRandomValue(15f + 0.2f, 30f - 0.5f);
		device.setTrt(trt);
		device.setPurifyTrt(purifyTrt);

		/**
		 * 水浊度
		 * 
		 * 单位： NTU 值范围： 饮用水浊度国家标准是：不得超过1NTU，当水源与净水技术条件限制时不得超过3NTU 净化前后对比：前>后
		 */
		Float tbdt = CommonUtil.getRandomValue(6.3f, 7.5f);
		Float purifyTbdt = CommonUtil.getRandomValue(0.9, 1.3);
		device.setTbdt(tbdt);
		device.setPurifyTbdt(purifyTbdt);
		/**
		 * 水量
		 */
		Integer amount = CommonUtil.getRandomIntValue(5, 100);
		device.setAmount(Float.parseFloat(amount.toString()));

		device.setPh(0f);
		device.setPurifyPh(0f);

		device.setDeviceX(new BigDecimal(x));
		device.setDeviceY(new BigDecimal(y));
		if (regionId != null) {
			device.setRegionId(regionId);
		}
		device.setDeviceDesc(deviceName);
		device.setDeviceId(deviceId);
		device.setUpdateTime(new Date());
		deviceMapper.insertSelective(device);
	}

	public String getDeviceID(String prefix) {
		return prefix + new Date().getTime();
	}
	
	
	public void updateDeviceName(Device device){
		deviceMapper.updateDeviceName(device);
	}
	
	public void updateDeviceMng(Device device){
		deviceMapper.updateDeviceMng(device);
	}
	
	public void updateDeviceNameInHistory(Device device){
		deviceMapper.updateDeviceNameInHistory(device);
	}
	
	public void updateDeviceMngInHistory(HashMap<String, Object> params){
		deviceMapper.updateDeviceMngInHistory(params);
	}
	
	
	
}
