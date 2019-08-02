package com.xiaohe.experienceUser.modules.cache;

public interface CaptchaService {

    /**
     *  设置验证码
     */
    public void setCaptcha(CaptchaInfo info);

    /**
     *  获取验证码
     * @param key
     * @return
     */
    public String getCaptcha(String key);
}
