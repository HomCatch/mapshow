package com.xiaohe.mapshow.modules.register.service;

import com.xiaohe.mapshow.base.IBaseServiceTwo;
import com.xiaohe.mapshow.modules.register.entity.EmailAuth;

public interface IEmailAuthService extends IBaseServiceTwo<EmailAuth,Integer> {

	/**
	 * 失效状态
	 * @return boolean
	 */
	boolean failureState(String email);
	/**
	 * 根据邮箱号查询
	 * @param email 邮箱号
	 * @return EmailAuth
	 */
	EmailAuth findByEmail(String email);

	/**
	 * 校验是否通过认证
	 * @param email 账号
	 * @param authCode 授权码
	 * @return boolean
	 */
	boolean authState(String email,String authCode);
}
