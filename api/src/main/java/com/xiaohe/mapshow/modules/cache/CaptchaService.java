package com.xiaohe.mapshow.modules.cache;

public interface CaptchaService {

	/**
	 * 设置验证码
	 */
	void setCaptcha(CaptchaInfo info);

	/**
	 * 获取验证码
	 *
	 * @param key
	 * @return
	 */
	String getCaptcha(String key);
}
