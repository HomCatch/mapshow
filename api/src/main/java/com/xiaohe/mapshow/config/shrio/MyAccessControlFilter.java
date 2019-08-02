package com.xiaohe.mapshow.config.shrio;

import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author  jack
 */
public class MyAccessControlFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(MyAccessControlFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setHeader("Access-control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", httpRequest.getMethod());
            httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject=getSubject(request,response);
        // 如果是记住我登录的，则需要处理一下
        // isRemembered为true、isAuthenticated为false
        if(!subject.isAuthenticated() && subject.isRemembered()){
            // 通过记住我第一次进程序，并且保存的principal中有内容，添加用户到session
            Session session = subject.getSession();
            if(session.getAttribute("username")==null && subject.getPrincipal() != null){
                SysUserEntity sysUserEntity = (SysUserEntity) subject.getPrincipal();
                String username = sysUserEntity.getUsername();
                session.setAttribute("username",username);
                //添加当前用户的数据源tenantName
                MemberService memberService = ApplicationContextUtils.getMemberService();
                String dbName = memberService.queryDbNameByUserName2("base", username);
                session.setAttribute("tenantName",dbName);
                log.info("当前用户使用cookie登录，所在库为:{}",dbName);
            }
        }
        return subject.isAuthenticated() || subject.isRemembered();

    }

}
