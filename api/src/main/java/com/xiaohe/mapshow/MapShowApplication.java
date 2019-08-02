package com.xiaohe.mapshow;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.config.HttpServletRequestReplacedFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author wzq
 */
@EnableAsync
@MapperScan(basePackages = {"com.xiaohe.mapshow.modules.*.dao"})
@EnableCaching
@EnableScheduling
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class MapShowApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(MapShowApplication.class, args);
		String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			System.out.println("activeProfile = " + activeProfile);
		}
		//加载所有数据源
		ApplicationContextUtils.addDataSource();
	}

	@Bean
	public FilterRegistrationBean httpServletRequestReplacedRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new HttpServletRequestReplacedFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("httpServletRequestReplacedFilter");
		registration.setOrder(1);
		return registration;
	}


}
