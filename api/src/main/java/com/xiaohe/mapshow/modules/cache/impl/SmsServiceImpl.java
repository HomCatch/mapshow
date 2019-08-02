package com.xiaohe.mapshow.modules.cache.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.modules.cache.CaptchaInfo;
import com.xiaohe.mapshow.modules.cache.SmsInfo;
import com.xiaohe.mapshow.modules.cache.SmsService;
import com.xiaohe.mapshow.modules.register.entity.VerifiCodeRecord;
import com.xiaohe.mapshow.modules.register.jpa.VerifiCodeRepository;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gmq
 * @since 2019-04-17 17:18
 **/
@Service
@DS("#session.tenantName")
public class SmsServiceImpl implements SmsService {
	private final VerifiCodeRepository verifiCodeRepository;

	public SmsServiceImpl(VerifiCodeRepository verifiCodeRepository) {
		this.verifiCodeRepository = verifiCodeRepository;
	}

	@Override
	public void setVerifiCode(SmsInfo info) {
		Cache cache = ApplicationContextUtils.getCache2();
		try {
			cache.put(new Element(info.getId(), info.getVerifiCode()));
			//持久化到数据库
//			查询手机号是否存在 存在覆盖验证码
			VerifiCodeRecord byPhone = verifiCodeRepository.findByPhone(info.getId());
			VerifiCodeRecord verifiCodeRecord = new VerifiCodeRecord(info.getId(), info.getVerifiCode());
			if(byPhone!=null){
				verifiCodeRecord.setId(byPhone.getId());
			}
			verifiCodeRepository.save(verifiCodeRecord);
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public String getVerifiCode(String key) {
		Cache cache = ApplicationContextUtils.getCache2();
		Element element = cache.get(key);
		if (element != null){
			return  (String)element.getObjectValue();
		}else {
			//缓存没有从数据库读取
			VerifiCodeRecord byPhone = verifiCodeRepository.findByPhone(key);
			return  byPhone==null?"":byPhone.getVerifiCode();
		}
	}
}
