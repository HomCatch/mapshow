package com.iswater.map.mapper;


import org.apache.ibatis.annotations.Param;

import com.iswater.map.pojos.User;



public interface UserMapper {
	
	boolean isUserPwdMatch(@Param("name")String name, @Param("pwd")String pwd);
	
	String getSalt(String name);
	
	User getUserInfo(String name);

}