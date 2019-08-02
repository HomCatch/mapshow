package com.xiaohe.mapshow.config.aspect;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaohe.mapshow.config.async.AsyncTask;
import com.xiaohe.mapshow.modules.loginlog.entity.LoginLog;
import com.xiaohe.mapshow.modules.loginlog.service.LoginLogService;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.utils.IPUtils;
import com.xiaohe.mapshow.utils.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @program: xplatform
 * @description: 登陆切面类
 * @author: Gmq
 * @date: 2018-12-24 16:48
 **/
@Component
@Aspect
public class LoginLogAspect {

    private final LoginLogService loginLogService;
    private final RestTemplate restTemplate;
    private final AsyncTask asyncTask;
    private final MemberService memberService;

    private Gson gson = new GsonBuilder().create();

    private final static Logger logger = LoggerFactory.getLogger(LoginLogAspect.class);
    @Value("${ds_base}")
    private String dsBase;

    @Autowired
    public LoginLogAspect(LoginLogService loginLogService, RestTemplate restTemplate, AsyncTask asyncTask, MemberService memberService) {
        this.loginLogService = loginLogService;
        this.restTemplate = restTemplate;
        this.asyncTask = asyncTask;
        this.memberService = memberService;
    }


    @Around(value = "@annotation(com.xiaohe.mapshow.config.annotation.LogLogin)")
    public Result around(ProceedingJoinPoint joinPoint) throws Throwable {
        //调用执行目标方法(result为目标方法执行结果)
        Result result = (Result) joinPoint.proceed();
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求头对象
        HttpServletRequest request = attributes.getRequest();
        LoginLog loginLog = new LoginLog();
        String username = request.getParameter("username");
        loginLog.setUserName(username);
        loginLog.setOperateType(1);
        String ipAddr = IPUtils.getIpAddr(request);
        loginLog.setIp(ipAddr);
        loginLog.setUserAgent(request.getHeader("User-Agent"));
        loginLog.setStatus(0 == result.getRet() ? 1 : 0);
        loginLog.setCreateTime(new Date());
        //异步写登录日志
        //获取用户所属数据库 并切库
        request.getSession().setAttribute("dsBase", dsBase);
        String dbName = memberService.queryDbNameByUserName(username);
        request.getSession().setAttribute("tenantName", dbName);

        asyncTask.saveLoginLog(loginLog);
        logger.info(result.getRet() == 0 ? "登录成功" : "登录失败");
        return result;
    }
}

