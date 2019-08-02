package com.iswater.map.controller;

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
import com.iswater.common.utils.DateUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.FilterPlanVO;
import com.iswater.common.vo.FormVO;
import com.iswater.common.vo.PageVO;
import com.iswater.common.vo.ResultVO;
import com.iswater.map.pojos.FilterPlan;
import com.iswater.map.pojos.User;
import com.iswater.map.service.FilterPlanService;


@Controller
public class FilterPlanController {
	public static final int DEFAULT_REGION_ID = 38; //北京 region id
	
	@Autowired
	private FilterPlanService filterPlanService;

	@RequestMapping(value = "/filter_plan")
	public ModelAndView doList(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("filter_plan");
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		FilterPlanVO paramVO = new FilterPlanVO();
		

		paramVO.setBinded_user_id(user.getId());
		paramVO.setUser_id(user.getId());
		Integer pageCurrent = StringUtil.isBlank(request.getParameter("pageCurrent")) ? 1 : Integer.valueOf(request.getParameter("pageCurrent"));
		paramVO.setPageCurrent(pageCurrent);
		modelAndView.addObject("param", paramVO);

		PageVO pageVO = filterPlanService.getFilterPlanList(paramVO);
		modelAndView.addObject("page", pageVO);
		modelAndView.addObject("userId", user.getId());
		return modelAndView;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPlanInfo", method = RequestMethod.POST)
	public ResultVO doGet(HttpServletRequest request) {
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		resultVO.setMsg("操作失败");
		try {
			String id = request.getParameter("id");
			
			if (!StringUtil.isEmpty(id)) {
				Integer intId = Integer.parseInt(id);
				FilterPlan filterPlan = filterPlanService.getFilterPlanById(intId);
				System.out.println("getPlanInfo_id="+id);
				if (filterPlan != null) {
					List<FormVO> list = CommonUtil.changeFormVODate(filterPlan);
					resultVO.setCode(200);
					resultVO.setObj(list);
					resultVO.setMsg("操作成功");
				}else {
					resultVO.setCode(200);
					resultVO.setMsg("plan为空");
				}
			}
		} catch (Exception e) {
			resultVO.setObj(e.getMessage());
		}
		return resultVO;
	}
	
	@ResponseBody
	@RequestMapping(value = "/savePlan", method = RequestMethod.POST)
	public ResultVO savePlan(HttpServletRequest request/*, @ModelAttribute FilterPlan filterPlan*/) {
		
		String deviceId = request.getParameter("deviceId");
		String time = request.getParameter("time");
		String remark = request.getParameter("remark");
		String type = request.getParameter("type");
		
		System.out.println(String.format("savePlan_deviceId=%s,type=%s,time=%s,remark=%s", deviceId, type, time, remark));
		FilterPlan filterPlan = new FilterPlan();
		
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		resultVO.setMsg("操作失败");
		
		if (type != null && type.equals("1")) {
			filterPlan.setDeviceId(deviceId);
			filterPlan.setPreviousTime(DateUtil.parse(time, DateUtil.DATE_FORMAT));
			filterPlan.setPreviousRemark(remark);
		}else if(type != null && type.equals("2")) {
			filterPlan.setDeviceId(deviceId);
			filterPlan.setNextTime(DateUtil.parse(time, DateUtil.DATE_FORMAT));
			filterPlan.setNextRemark(remark);
		}
		try {
			if (filterPlan != null) {
				if (filterPlanService.isPlanExits(filterPlan.getDeviceId())){
					filterPlanService.updateFilterPlan(filterPlan);
				}else {
					filterPlanService.saveFilterPlan(filterPlan);
				}
				resultVO.setCode(200);
				resultVO.setMsg("操作成功");		
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultVO.setObj(e.getMessage());
		}
		return resultVO;
	}
	
	
	
	
	

}
