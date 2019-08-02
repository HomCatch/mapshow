package com.xiaohe.mapshow.utils;

/**
 * 常量池
 *
 * @author hzh
 * @date 2018/10/16
 */
public interface ConstantUtil {


    /**
     * 腾讯短信返回ok
     */
    String MSG_OK = "OK";
    String REGISTERED = "手机号已注册";
    String MAILREGISTERED = "该邮箱已注册";
    String PHONE_FORMAL_ERROR = "手机号格式有误";
    String VERIFICODE_ERROR = "\"验证码\"错误，请重新输入";
    int PHONEERRO = 1007;
    int MAILERRO= 1008;
    /**
     * 手机号正则
     */
    String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    int MAIL_UNACTIVATE = 1010;

}
