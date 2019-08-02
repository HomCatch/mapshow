package com.xiaohe.experienceUser.modules.cache.impl;

import com.xiaohe.experienceUser.config.ApplicationContextUtils;
import com.xiaohe.experienceUser.modules.cache.CaptchaInfo;
import com.xiaohe.experienceUser.modules.cache.CaptchaService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Service;

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Override
    public void setCaptcha(CaptchaInfo captchaInfo){

        Cache cache = ApplicationContextUtils.getCache();
        try {
            cache.put(new Element(captchaInfo.getId(), captchaInfo.getCaptcha()));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String getCaptcha(String key){
        Cache cache = ApplicationContextUtils.getCache();
        Element element = cache.get(key);
        if (element != null){
            return  (String)element.getObjectValue();
        }else {
            return  "";
        }
    }
}
