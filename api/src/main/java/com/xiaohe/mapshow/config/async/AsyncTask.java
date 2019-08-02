package com.xiaohe.mapshow.config.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.modules.datasource.service.IDynamicDataSource;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.loginlog.entity.LoginLog;
import com.xiaohe.mapshow.modules.loginlog.service.LoginLogService;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.operatelog.entity.OperateLog;
import com.xiaohe.mapshow.modules.operatelog.service.OperateLogService;
import com.xiaohe.mapshow.modules.register.entity.Register;
import com.xiaohe.mapshow.modules.register.service.RegisterService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.utils.SendMailUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

/**
 * @program: purifier
 * @description: 异步任务
 * @author: Gmq
 * @date: 2019-03-13 10:39
 **/
@Component
public class AsyncTask {
	private final static Logger logger = LoggerFactory.getLogger(AsyncTask.class);

	private final RestTemplate restTemplate;
	private final LoginLogService loginLogService;
	private final OperateLogService operateLogService;
	private final SysUserService sysUserService;
	private final RegisterService registerService;
	private final IDynamicDataSource iDynamicDataSource;
	private final MemberService memberService;
	private final DbInfoService dbInfoService;
	private Gson gson = new GsonBuilder().create();

	@Value("${ds_base}")
	private String dbBase;

	@Autowired
	public AsyncTask(RestTemplate restTemplate, LoginLogService loginLogService, OperateLogService operateLogService, SysUserService sysUserService, RegisterService registerService, IDynamicDataSource iDynamicDataSource, MemberService memberService, DbInfoService dbInfoService) {
		this.restTemplate = restTemplate;
		this.loginLogService = loginLogService;
		this.operateLogService = operateLogService;
		this.sysUserService = sysUserService;
		this.registerService = registerService;
		this.iDynamicDataSource = iDynamicDataSource;
		this.memberService = memberService;
		this.dbInfoService = dbInfoService;
	}

	/**
	 * 记录登陆日志异步任务
	 *
	 * @param loginLog 登录对象
	 */
	@Async
	public void saveLoginLog(LoginLog loginLog) {

		//调用淘宝位置接口存入城市
		String jsonStr = "";
		String city = "";
		try {
			jsonStr = restTemplate.getForObject("http://ip.taobao.com/service/getIpInfo.php?ip=" + loginLog.getIp(), String.class);
			JSONObject jsonObject = JSON.parseObject(jsonStr);
			JSONObject data = jsonObject != null ? jsonObject.getJSONObject("data") : null;
			city = data == null ? city : data.getString("region") + "-" + data.getString("city");
			loginLog.setCity(city);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取淘宝地理位置API出错，连接超时！");
		}
		//存入日志表
		//从基础库查询所属数据源
		String dbName = memberService.queryDbNameByUserName2(dbBase, loginLog.getUserName());
		loginLogService.save(dbName, loginLog);
		//kafka收集
//        kafkaTemplate.send("loginLog",gson.toJson(loginLog));
		logger.info("+++++++++++++++++++++  message = {}", gson.toJson(loginLog));
		logger.info("记录登陆日志......");
	}

	/**
	 * 保存操作日志
	 *
	 * @param joinPoint joinPoint
	 * @param time      请求时间
	 * @param status    状态
	 */
	@Async
	public void saveOperateLog(JoinPoint joinPoint, long time, int status, OperateLog operateLog) {
		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();
			LogOperate logOperate = method.getAnnotation(LogOperate.class);
			//请求的参数
			String params = "";
			Object[] args = joinPoint.getArgs();
			try {
				params = new Gson().toJson(args[0]);
				operateLog.setReqParam(params);
			} catch (Exception e) {

			}
			//状态
			operateLog.setStatus(0 == status ? 1 : 0);
			//请求时间
			operateLog.setReqTime((int) time);
			int adStatus;
			String adlog = "";
			String userName = operateLog.getUserName();
			String requestURI = operateLog.getReqUrl();
			if (StringUtils.isNotBlank(requestURI)) {
				if (requestURI.contains("/access/sendSms")) {
					//发送验证码的用户名
					userName = params.replace("\"", "");
				} else if (requestURI.contains("/access/register")) {
					//注册的用户名
					JSONObject jsonObject = JSON.parseObject(params);
					userName = jsonObject.get("username").toString();
				}
			}

			//用户名
			operateLog.setUserName(StringUtils.isNotBlank(userName) ? userName : null);
			//操作信息
			operateLog.setOperateInfo(StringUtils.isNotBlank(adlog) ? adlog : logOperate.description());
			operateLog.setReqTime((int) time);
			operateLog.setCreateTime(new Date());
			//保存系统日志
			//从基础库查询所属数据源
			String dbName = memberService.queryDbNameByUserName2(dbBase, userName);
			operateLogService.save(dbName,operateLog);
			logger.info("+++++++++++++++++++++  message = {}", gson.toJson(operateLog));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//kafka收集
//        kafkaTemplate.send("operateLog", gson.toJson(operateLog));
		logger.info("保存操作日志......");
	}

	/**
	 * 发送注册邮箱通知和保存注册信息的异步任务
	 */
	@Async
	public void sendMail(String username) {
		SendMailUtil.send("772826099@qq.com", "注册通知", "有新的用户注册" + username, "smtp", "smtp.exmail.qq.com", "developer@he-live.com", "465", "developer@he-live.com", "XHDeveloper123");
	}

	/**
	 * 发送HTML邮件
	 */
	@Async
	public void sendHtmlMail(String subject, String username, String connect) {
		SendMailUtil.sendHtmlMail(username, subject, connect, "smtp", "smtp.exmail.qq.com", "developer@he-live.com", "465", "developer@he-live.com", "XHDeveloper123");
	}

	/**
	 * 保存注册信息的异步任务
	 */
	@Async
	public void saveRegisterInfo(String username) {
		//查询用户表中的注册用户信息
		try {
			SysUserEntity byUsername1 = null;
			for (int i = 0; i < 2; i++) {
				Thread.sleep(1000);
				byUsername1 = sysUserService.findByUsername(username);
				if (byUsername1 != null) {
					break;
				}
			}
			//保存到注册表信息
			if (byUsername1 != null) {
				registerService.save(new Register(byUsername1.getUserId().intValue(), username));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新增数据库和添加该数据源
	 */
	@Async
	public void addDbAndDs(String dsBase) {
		//执行脚本并动态新增数据源
		DbInfo database = iDynamicDataSource.createDatabase(dsBase);
		ArrayList<DbInfo> dbInfos = new ArrayList<>();
		dbInfos.add(database);
		//dbinfo新增数据
		dbInfoService.addDbInfos(dsBase, dbInfos);
	}
}
