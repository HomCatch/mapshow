package com.iswater.map.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.iswater.map.mapper.UserMapper;
import com.iswater.map.pojos.User;


@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public boolean isUserPwdMatch(String name, String pwd){
		return userMapper.isUserPwdMatch(name, pwd);
	}
	
	public String getSalt(String name){
		return userMapper.getSalt(name);
	}
	
	public User getUserInfo(String name){
		return userMapper.getUserInfo(name);
	}
}
