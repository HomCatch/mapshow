package com.iswater.map.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.iswater.common.utils.StringUtil;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.History;
import com.iswater.map.pojos.Region;
import com.iswater.map.pojos.User;
import com.iswater.map.service.DeviceService;
import com.iswater.map.service.DictService;
import com.iswater.map.service.HistoryService;
import com.iswater.map.service.RegionService;
import com.iswater.map.service.UserService;

@Controller
public class WaterQualityMapController {

	@Autowired
	private RegionService regionService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private DictService dictService;
	
	
	@Autowired
	private UserService userService;

	
	/**
	 * 地图初始化
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/waterQualityMap")
	public ModelAndView index(HttpServletRequest request) {
		
		// 查询到代理商所在区域
		
		HttpSession session = request.getSession();  
		User user = (User)session.getAttribute("user");
		
	
		ModelAndView view = new ModelAndView("map/index");
		
		List<Region> regions = Lists.newArrayList();
		Region theRegion = regionService.getRegion(user.getRegion_id()); //根据代理商区域获取ID获取区域信息
		regions.add(theRegion);
		view.addObject("regions", regions);
		
		view.addObject("regionId", user.getRegion_id());
		view.addObject("isGlobal", 0);// 具体关键字
		
		
		view.addObject("keyword", user.getRegion_id());
		view.addObject("keyword_txt", theRegion.getRegionName());
		
		List<Device> devices = Lists.newArrayList();
		devices = deviceService.getDeviceList(theRegion.getRegionId(), user.getId(), 0);
		
		
		view.addObject("devices", devices);
		
		view.addObject("lastVersion", "2.0");
		
		return view;
		
		
		/*
		ModelAndView view = new ModelAndView("map/index");
		// 所有区域信息
		List<Region> regions = regionService.getRegionList();
		view.addObject("regions", regions);
		// 参数
		String keyword = request.getParameter("keyword");// 关键字
		String keyword_txt = request.getParameter("keyword_txt");// 显示的值
		
	
		if (StringUtil.isBlank(keyword) && StringUtil.isBlank(keyword_txt)) {
			Dict dict = dictService.getDict("defaultRegion");
			if(dict!=null){
				String value = dict.getDictValue();
				String txt = dict.getDictDesc();
				keyword = value;
				keyword_txt = txt;
			}
		}

		Device device = null;
		if (StringUtil.isNotBlank(keyword_txt) && !keyword_txt.equals("全国")) {
			List<Device> devices = Lists.newArrayList();
			// 根据设备ID查询
			if (keyword_txt.indexOf("D") > -1 || keyword_txt.indexOf("H") > -1) {
				device = deviceService.getDeviceByDeviceId(keyword.trim());
				devices.add(device);

				view.addObject("isGlobal", -2);// 具体设备
			} else {
				Region region = regionService.getRegionByName(keyword_txt);
				boolean isNum = StringUtil.isNumber(keyword);
				if (StringUtil.isNotBlank(keyword) && region != null && isNum ) {
					Integer regionId = Integer.valueOf("38");
					// 根据区域ID取得设备信息
					devices = deviceService.getDeviceList(regionId);
					view.addObject("regionId", regionId);
					view.addObject("isGlobal", 0);// 具体关键字
				} else {
					view.addObject("isGlobal", -1);// 具体区域
				}
			}
			view.addObject("devices", devices);
		} else {
			// 查询全国数据
			view.addObject("isGlobal", 1);// 全国
		}
		view.addObject("keyword", keyword);
		view.addObject("keyword_txt", keyword_txt);
		// 版本号
	//	String lastVersion = getLastVersion(request);
		view.addObject("lastVersion", "2.0");
		return view;
		*/
	}

	/**
	 * 根据区域ID/获取设备信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/retriveDeviceInfo")
	public JSONArray retriveDeviceInfo(HttpServletRequest request) {
		JSONArray jsonArray = new JSONArray();
		String regionId = request.getParameter("regionId");
		String deviceId = request.getParameter("deviceId");
		List<Device> dbObject = Lists.newArrayList();
		if (StringUtil.isBlank(regionId) && StringUtil.isBlank(deviceId)) {
			dbObject = deviceService.getDeviceList();
		} else {
			if (StringUtil.isNotBlank(regionId)) {
				dbObject = deviceService.getMngDeviceList(Integer.valueOf(regionId), 100);
			}
			
			if (StringUtil.isNotBlank(deviceId)) {
				Device device = deviceService.getDeviceByDeviceId(deviceId);
				dbObject.add(device);
			}
		}
		jsonArray = JSONArray.fromObject(dbObject);
		return jsonArray;
	}


	/**
	 * 根据坐标范围查询设备列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeviceByPoint")
	public JSONObject getDeviceByPoint(HttpServletRequest request, @ModelAttribute("json") String json) {
		JSONObject result = new JSONObject();

		JSONObject jsonObject = JSONObject.fromObject(json);
		Double xmin = jsonObject.getDouble("xmin");
		Double xmax = jsonObject.getDouble("xmax");
		Double ymin = jsonObject.getDouble("ymin");
		Double ymax = jsonObject.getDouble("ymax");
		List<Device> devices = deviceService.getDeviceList(null, xmin, xmax, ymin, ymax);
		JSONArray jsonArray = new JSONArray();
		if (StringUtil.isNotEmpty(devices)) {

			jsonArray = JSONArray.fromObject(devices);
			result.put("list", jsonArray);

			Device device = deviceService.getSumDeviceList(null, xmin, xmax, ymin, ymax, devices.size());
			device.setUpdateTime(new Date());
			JSONObject deviceObj = JSONObject.fromObject(device);
			result.put("sum", deviceObj);
		}
		return result;
	}

	/**
	 * 汇总
	 * 
	 * @param request
	 * @param json
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSum")
	public JSONObject getSum(HttpServletRequest request, @ModelAttribute("json") String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Double xmin = jsonObject.getDouble("xmin");
		Double xmax = jsonObject.getDouble("xmax");
		Double ymin = jsonObject.getDouble("ymin");
		Double ymax = jsonObject.getDouble("ymax");
		String keyword_txt = jsonObject.getString("keyword_txt");
		String keyword = jsonObject.getString("keyword");
		Integer regionId = jsonObject.getInt("regionId");

		Device device = null;
		Region record = null;
		if (xmin != 0 && xmax != 0 && ymin != 0 && ymax != 0) {
			// 坐标点范围内的汇总
			List<Device> devices = deviceService.getDeviceList(null, xmin, xmax, ymin, ymax);
			if (StringUtil.isNotEmpty(devices)) {
				device = deviceService.getSumDeviceList(null, xmin, xmax, ymin, ymax, devices.size());
			}
		} else {
			if (regionId != null && regionId != 0) {
				device = deviceService.getSumDeviceByRegionId(regionId);
			} else {
				if (StringUtil.isNotBlank(keyword_txt) && !keyword_txt.equals("全国")) {
					// 单个设备汇总信息
					if (keyword_txt.indexOf("D") > -1 || keyword_txt.indexOf("H") > -1) {
						device = deviceService.getDeviceByDeviceId(keyword.trim());
					}
				} else {
					// 全国汇总
					record = regionService.getSumRegion();
				}
			}
		}
		JSONObject deviceObj = new JSONObject();
		if (record != null) {
			record.setUpdateTime(new Date());
			deviceObj = JSONObject.fromObject(record);
		} else {
			device.setUpdateTime(new Date());
			deviceObj = JSONObject.fromObject(device);
		}
		return deviceObj;
	}


}
