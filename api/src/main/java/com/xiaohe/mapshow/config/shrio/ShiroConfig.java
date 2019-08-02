package com.xiaohe.mapshow.config.shrio;


import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jack
 */
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        filtersMap.put("myAccessControlFilter", new MyAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //注意过滤器配置顺序 不能颠倒
        filterChainDefinitionMap.put("/access/logout", "logout");
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/access/login", "anon");
        filterChainDefinitionMap.put("/access/captcha", "anon");
        filterChainDefinitionMap.put("/access/sendSms", "anon");
        filterChainDefinitionMap.put("/access/register", "anon");
        filterChainDefinitionMap.put("/access/forgetPwd", "anon");
        //邮箱账户注册验证修改
        filterChainDefinitionMap.put("/access/registerMail", "anon");
        filterChainDefinitionMap.put("/access/forgetEmailPwd", "anon");
        filterChainDefinitionMap.put("/access/email/activate", "anon");
        filterChainDefinitionMap.put("/access/email/auth", "anon");
        filterChainDefinitionMap.put("/access/modifyPwd", "anon");
        filterChainDefinitionMap.put("/access/sendActivateEmail", "anon");
        filterChainDefinitionMap.put("/access/sendModifyEmail", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");
//        数据源相关接口
        filterChainDefinitionMap.put("/datasource/**", "anon");
//      filterChainDefinitionMap.put("/**", "anon"); // 调试状态下，可开启本行，同时注释掉下面的行
//        filterChainDefinitionMap.put("/", "user");
        filterChainDefinitionMap.put("/**", "myAccessControlFilter");
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/access/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public MyCredentialsMatcher getCredentialsMatcher() {
        MyCredentialsMatcher matcher = new MyCredentialsMatcher();
        return matcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(getCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        //设置RememberMe  cookie管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public SessionManager sessionManager() {
        MySessionMng mySessionManager = new MySessionMng();
        //TODO 待恢复
        mySessionManager.setSessionIdCookieEnabled(false);
        return mySessionManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    /**
     *Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(false);
        //记住我cookie生效时间,单位是秒
        simpleCookie.setMaxAge(604800);
        return simpleCookie;
    }

    /**
     * cookie管理器;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

}