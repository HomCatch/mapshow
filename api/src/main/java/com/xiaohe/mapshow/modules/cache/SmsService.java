package com.xiaohe.mapshow.modules.cache;

public interface SmsService {

	/**
	 * 设置验证码
	 */
	void setVerifiCode(SmsInfo info);

	/**
	 * 获取验证码
	 *
	 * @param key
	 * @return
	 */
	String getVerifiCode(String key);
}
