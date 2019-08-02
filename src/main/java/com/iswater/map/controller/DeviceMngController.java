package com.iswater.map.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.iswater.common.utils.CommonUtil;
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.FormVO;
import com.iswater.common.vo.PageVO;
import com.iswater.common.vo.ResultVO;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.History;
import com.iswater.map.pojos.LatestRunInfo;
import com.iswater.map.pojos.User;
import com.iswater.map.service.DeviceService;

import net.sf.json.JSONObject;


@Controller
public class DeviceMngController {
	public static final int DEFAULT_REGION_ID = 38; //北京 region id
	
	@Autowired
	private DeviceService service;

	@RequestMapping(value = "/devmng")
	public ModelAndView doList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("device_mng");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		DeviceMngVO paramVO = new DeviceMngVO();
		

		paramVO.setBinded_user_id(user.getId());
		
		Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
		paramVO.setPageCurrent(pageCurrent);
		modelAndView.addObject("param", paramVO);

		PageVO pageVO = service.getRecordHistoryList(paramVO);
		modelAndView.addObject("page", pageVO);

		return modelAndView;
	}
	
	//设备总览标签跳转
	@RequestMapping(value = "/devtagmng")
	public ModelAndView doTagList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("device_mng");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String tagType = request.getParameter("tagType");
		System.out.println("tagType="+tagType);
		
		if (tagType.equals("2")) {//故障提醒标签
			DeviceMngVO paramVO = new DeviceMngVO();
			paramVO.setBinded_user_id(user.getId());
			paramVO.setRun_state(1);
			
			Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
			paramVO.setPageCurrent(pageCurrent);
			modelAndView.addObject("param", paramVO);

			PageVO pageVO = service.getBreakDownDevList(paramVO);
			modelAndView.addObject("page", pageVO);
			
		}else if (tagType.equals("1")) {//需换芯提醒标签
			DeviceMngVO paramVO = new DeviceMngVO();
			paramVO.setBinded_user_id(user.getId());
//			paramVO.setRun_state(5);//不存在的状态，目的是让查询数据库时，返回空列表
			paramVO.setRun_state(1);
			
			Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
			paramVO.setPageCurrent(pageCurrent);
			modelAndView.addObject("param", paramVO);

//			PageVO pageVO = service.getBreakDownDevList(paramVO);
			PageVO pageVO = service.getFilgerReplaceDevList(paramVO);
			modelAndView.addObject("page", pageVO);
			
		}else {//设备数量标签
			DeviceMngVO paramVO = new DeviceMngVO();
			paramVO.setBinded_user_id(user.getId());
			
			Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
			paramVO.setPageCurrent(pageCurrent);
			modelAndView.addObject("param", paramVO);

			PageVO pageVO = service.getRecordHistoryList(paramVO);
			modelAndView.addObject("page", pageVO);

		}
	
		return modelAndView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/saveDevice", method = RequestMethod.POST)
	public ResultVO saveDevice(HttpServletRequest request, @ModelAttribute Device device) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		resultVO.setMsg("操作失败");
		try {
			if (device != null) {
				
				HttpSession session = request.getSession();
				User user = (User)session.getAttribute("user");
				
				device.setBindedUserId(user.getId());
				device.setRegionId(user.getRegion_id());
				device.setStatus(1);
				device.setUpdateTime(new Date());
				
				if (device.getId() == null) { //添加设备
					
					Device  olddevice = service.getDeviceByDeviceId(device.getDeviceId());
					
					if (olddevice != null){
						resultVO.setCode(500);
						resultVO.setMsg("设备编码重复");		
					}else{
						service.addDeviceShortyly(device);
						resultVO.setCode(200);
						resultVO.setMsg("操作成功");		
					}									
	
				}else{ //修改设备
					
					Device oldDevice = service.getDeviceById(device.getId());
					
					// 更新device表设备信息
//					service.updateDeviceName(device);
					service.updateDeviceMng(device);
					
					//更新history表中的设备信息
					HashMap<String, Object> params = Maps.newHashMap();
					params.put("deviceId", device.getDeviceId());
					params.put("oldDeviceId", oldDevice.getDeviceId());
					params.put("deviceName", device.getDeviceName());
					params.put("deviceX", device.getDeviceX());
					params.put("deviceY", device.getDeviceY());
					params.put("remark", device.getRemark());
//					service.updateDeviceNameInHistory(device);
					service.updateDeviceMngInHistory(params);
					
					resultVO.setCode(200);
					resultVO.setMsg("操作成功");						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVO.setObj(e.getMessage());
		}
		return resultVO;
	}
	


	@ResponseBody
	@RequestMapping(value = "/getDeviceInfo", method = RequestMethod.POST)
	public ResultVO doGet(HttpServletRequest request) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		resultVO.setMsg("操作失败");
		try {
			String id = request.getParameter("id");
			Integer intId = Integer.parseInt(id);
			if (StringUtil.isNotBlank(id)) {
				Device device = service.getDeviceByDeviceKey(intId);
				if (device != null) {
					List<FormVO> list = CommonUtil.changeFormVO(device);
					resultVO.setCode(200);
					resultVO.setObj(list);
					resultVO.setMsg("操作成功");
				}
			}
		} catch (Exception e) {
			resultVO.setObj(e.getMessage());
		}
		return resultVO;
	}
	
	
	
	

}
