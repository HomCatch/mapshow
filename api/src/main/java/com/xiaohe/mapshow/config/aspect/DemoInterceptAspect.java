package com.xiaohe.mapshow.config.aspect;

import com.xiaohe.mapshow.config.validate.XException;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.modules.untils.Constant;
import com.xiaohe.mapshow.utils.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * demo用户拦截切面
 *
 * @author Gmq
 * @since 2019-04-24 09:41
 **/
@Component
@Aspect
public class DemoInterceptAspect {

	@Pointcut("@annotation(com.xiaohe.mapshow.config.annotation.DemoIntercept)")
	public void demoInterceptCut() {

	}

	@Before("demoInterceptCut()")
	public void demoIntercept() throws Throwable {
		Result result = new Result();
		SysUserEntity user = ShiroUtils.getUserEntity();

		//如果是demo用户，则无权操作
		if (user.getDemoFlag() == null || Constant.DEMO_FLAG != user.getDemoFlag()) {
			return;
		}
		throw new XException("当前为体验版，没有操作权限" , 4001);
	}
}
