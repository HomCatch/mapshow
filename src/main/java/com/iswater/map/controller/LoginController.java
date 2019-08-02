package com.iswater.map.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.iswater.common.utils.CaptchaUtil;
import com.iswater.common.utils.DESUtil;
import com.iswater.common.utils.StringUtil;
import com.iswater.common.vo.DeviceMngVO;
import com.iswater.common.vo.PageVO;
import com.iswater.common.vo.ResultVO;
import com.iswater.map.pojos.User;
import com.iswater.map.service.DeviceService;
import com.iswater.map.service.DictService;
import com.iswater.map.service.HistoryService;
import com.iswater.map.service.RegionService;
import com.iswater.map.service.UserService;

@Controller
public class LoginController {

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

	
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ResponseBody
    public void captcha(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        CaptchaUtil.outputCaptcha(request, response);
    }
	

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResultVO login(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		
		ResultVO resultVO = new ResultVO();
		resultVO.setCode(500);
		

		try {
			
			// 判断是否已经登录
			User user;
		
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
		
			user  = userService.getUserInfo(username);
	
			if (user == null){
				resultVO.setMsg("用户名，密码不匹配");		
			}else{
					
				String joinPwd = password + user.getSalt();
				
				String pwd = DESUtil.getMd5(joinPwd);
				
				if (userService.isUserPwdMatch(username, pwd)){
								
					session.setAttribute("user", user);
					resultVO.setCode(200);
					resultVO.setMsg("登录成功");
					resultVO.setObj(user.getId());
				}else{
							
					resultVO.setMsg("用户名，密码不匹配");				
				}
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultVO.setMsg("操作异常");
			resultVO.setObj(e.getMessage());
		}
		return resultVO;
	}

	@RequestMapping(value = "/loginSucc")
	public ModelAndView loginSucc(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		
		ModelAndView modelAndView = new ModelAndView("show");
	
		modelAndView.addObject("userName", user.getName());
		modelAndView.addObject("userId", user.getId());
		return modelAndView;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
			
		request.getSession().removeAttribute("user");
		
		ModelAndView modelAndView = new ModelAndView("login");
	
		return modelAndView;
	}	
	

}
