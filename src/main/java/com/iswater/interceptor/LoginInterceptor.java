package com.iswater.interceptor;

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  
  

import org.springframework.web.servlet.HandlerInterceptor;  
import org.springframework.web.servlet.ModelAndView;  

import com.iswater.map.pojos.User;


public class LoginInterceptor implements HandlerInterceptor{
	
		
	
		 public void afterCompletion(HttpServletRequest request,  
		            HttpServletResponse response, Object handler, Exception exc)  
		            throws Exception {  
		          
		 }  
		 
	
	    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
	            Object handler, ModelAndView modelAndView) throws Exception {  
	    }  
	  	 
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  Object handler) throws Exception {  
	  
	    	
	    	
	        String url = request.getRequestURI();  
	      
	        /**
	         *  登录请求放行
	         */
	        if(url.equals("/mapShow/login")){  
	            return true;  
	        }  
	        //获取Session  
	        HttpSession session = request.getSession();  
	        User user = (User)session.getAttribute("user");  
	          
	        if(user != null){  
	            return true;  
	        }  
	        //不符合条件的，跳转到登录界面  
	        request.getRequestDispatcher("login.jsp").forward(request, response);  
	          
	        return false;  
	        
	    }  	 
}
