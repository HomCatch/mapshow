package com.xiaohe.mapshow.config;


import com.xiaohe.mapshow.modules.datasource.service.IDynamicDataSource;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.loginlog.service.LoginLogService;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.register.entity.Register;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;

@Component
@EnableAutoConfiguration
public class ApplicationContextUtils implements ApplicationContextAware {

	private static Logger log = LoggerFactory.getLogger(ApplicationContextUtils.class);


	public static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
	}

	// 传入线程中
	public static <T> T getBean(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}

	// 国际化使用
	public static String getMessage(String key) {
		return applicationContext.getMessage(key, null, Locale.getDefault());
	}

	/// 获取当前环境
	public static String getActiveProfile() {
		return applicationContext.getEnvironment().getActiveProfiles()[0];
	}


	public static Cache getCache() {
		EhCacheCacheManager cacheCacheManager = ApplicationContextUtils.applicationContext.getBean(EhCacheCacheManager.class);
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		Cache cache = cacheManager.getCache("captcha");
		return cache;
	}

	public static Cache getCache2() {
		EhCacheCacheManager cacheCacheManager = ApplicationContextUtils.applicationContext.getBean(EhCacheCacheManager.class);
		CacheManager cacheManager = cacheCacheManager.getCacheManager();
		Cache cache = cacheManager.getCache("sms");
		return cache;
	}

	public static boolean addDataSource() {
		DbInfoService dbInfoService = ApplicationContextUtils.applicationContext.getBean(DbInfoService.class);
		IDynamicDataSource iDynamicDataSource = ApplicationContextUtils.applicationContext.getBean(IDynamicDataSource.class);
		//
		List<DbInfo> dbInfos = dbInfoService.selectList("base");
		for (DbInfo dbInfo : dbInfos) {
			if("master".equals(dbInfo.getDbName())){
				continue;
			}
			if (iDynamicDataSource.addDataSource(dbInfo)) {
				log.info("初始化数据源{}成功",dbInfo.getDbName());
			}
		}
		return true;
	}
	public static RestTemplate getRestTemplate() {
		return ApplicationContextUtils.applicationContext.getBean(RestTemplate.class);
	}
	public static DbInfoService getDbInfoService() {
		return ApplicationContextUtils.applicationContext.getBean(DbInfoService.class);
	}
	public static LoginLogService getLoginLogService() {
		return ApplicationContextUtils.applicationContext.getBean(LoginLogService.class);
	}
	public static MemberService getMemberService() {
		return ApplicationContextUtils.applicationContext.getBean(MemberService.class);
	}


}
