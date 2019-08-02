package com.xiaohe.experienceUser;

import com.xiaohe.experienceUser.config.HttpServletRequestReplacedFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wzq
 */
@SpringBootApplication
@EnableAsync
@MapperScan(basePackages = {"com.xiaohe.experienceUser.modules.*.dao"})
@EnableCaching
public class ExperienceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExperienceUserApplication.class, args);
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
