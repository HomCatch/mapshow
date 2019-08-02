package com.iswater.map.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.HistoryVO;
import com.iswater.common.vo.PageVO;
import com.iswater.map.pojos.User;
import com.iswater.map.service.HistoryService;


@Controller
public class DeviceHistoryController {

	@Autowired
	private HistoryService service;

	@RequestMapping(value = "/details")
	public ModelAndView doList(HttpServletRequest request) {
		
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		
		ModelAndView modelAndView = new ModelAndView("history_list");
	
		String device_id = request.getParameter("device_id");

	
		HistoryVO paramVO = new HistoryVO();
		

		paramVO.setDevice_id(device_id);
		paramVO.setBinded_user_id(user.getId());
		System.out.println(String.format("device_id=%s,user_id=%s", device_id, user.getId()));
		
		Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
		paramVO.setPageCurrent(pageCurrent);
		modelAndView.addObject("param", paramVO);

		PageVO pageVO = service.getRecordHistoryList(paramVO);
		modelAndView.addObject("page", pageVO);

		return modelAndView;
	}
	


}
