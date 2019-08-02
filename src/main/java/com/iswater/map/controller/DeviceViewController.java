package com.iswater.map.controller;

import java.util.Date;
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

import com.iswater.common.utils.CommonUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.FormVO;
import com.iswater.common.vo.PageVO;
import com.iswater.common.vo.ResultVO;
import com.iswater.map.pojos.Device;
import com.iswater.map.pojos.User;
import com.iswater.map.service.DeviceService;


@Controller
public class DeviceViewController {
	public static final int DEFAULT_REGION_ID = 38; //北京 region id
	
	@Autowired
	private DeviceService service;

	@RequestMapping(value = "/deviceview")
	public ModelAndView doList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("device_view");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
//		DeviceMngVO paramVO = new DeviceMngVO();
//		
//
//		paramVO.setBinded_user_id(user.getId());
//		
//		Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
//		paramVO.setPageCurrent(pageCurrent);
//		modelAndView.addObject("param", paramVO);
//
//		PageVO pageVO = service.getRecordHistoryList(paramVO);
//		modelAndView.addObject("page", pageVO);

		return modelAndView;
	}
	
	
}
