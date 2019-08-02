package com.xiaohe.mapshow.modules.sys.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Producer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.xiaohe.mapshow.config.ApplicationContextUtils;
import com.xiaohe.mapshow.config.annotation.LogLogin;
import com.xiaohe.mapshow.config.annotation.LogOperate;
import com.xiaohe.mapshow.config.async.AsyncTask;
import com.xiaohe.mapshow.modules.cache.CaptchaInfo;
import com.xiaohe.mapshow.modules.cache.CaptchaService;
import com.xiaohe.mapshow.modules.cache.SmsInfo;
import com.xiaohe.mapshow.modules.cache.SmsService;
import com.xiaohe.mapshow.modules.company.entity.Company;
import com.xiaohe.mapshow.modules.company.service.CompanyService;
import com.xiaohe.mapshow.modules.dashboardinstance.entity.DashboardInstance;
import com.xiaohe.mapshow.modules.dashboardinstance.service.DashboardInstanceService;
import com.xiaohe.mapshow.modules.dbinfo.entity.DbInfo;
import com.xiaohe.mapshow.modules.dbinfo.service.DbInfoService;
import com.xiaohe.mapshow.modules.member.entity.Member;
import com.xiaohe.mapshow.modules.member.service.MemberService;
import com.xiaohe.mapshow.modules.register.entity.EmailAuth;
import com.xiaohe.mapshow.modules.register.service.IEmailAuthService;
import com.xiaohe.mapshow.modules.sys.entity.SysUserEntity;
import com.xiaohe.mapshow.modules.sys.entity.SysUserRegist;
import com.xiaohe.mapshow.modules.sys.entity.SysUserRegistMail;
import com.xiaohe.mapshow.modules.sys.service.SysUserService;
import com.xiaohe.mapshow.modules.sys.shiro.ShiroUtils;
import com.xiaohe.mapshow.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@RequestMapping(value = "/access")
@RestController
public class AccessController {
	private static Logger log = LoggerFactory.getLogger(AccessController.class);
	@Autowired
	private AsyncTask asyncTask;
	@Autowired
	private Producer producer;

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private DashboardInstanceService dashboardInstanceService;
	@Autowired
	private IEmailAuthService iEmailAuthService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private DbInfoService dbInfoService;

	@Value("${ds_base}")
	private String dsBase;

	@RequestMapping(value = "/unauth", method = RequestMethod.GET)
	public Result unauth() {
		Result result = new Result();
		result.error(2000, "尚未登录");
		return result;
	}

	/**
	 * 登出接口
	 *
	 * @return
	 */
	@RequestMapping("/logout")
	@LogOperate(description = "登出")
	public Result logout() {
		Result result = new Result();
		result.ok();

		Subject subject = SecurityUtils.getSubject();
		subject.logout();

		return result;
	}

	/**
	 * 生成验证码
	 *
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("captcha")
	public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//生成文字验证码
		String text = producer.createText();
		//生成图片验证码
		BufferedImage image = producer.createImage(text);

		captchaService.setCaptcha(new CaptchaInfo(key, text));

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录接口
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("/login")
	@LogLogin
	public CommonResult login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, @RequestParam(value = "key") String key, @RequestParam(value = "captcha") String captcha, @RequestParam(value = "rememberMe",required = false) boolean rememberMe, HttpServletRequest request) {

		String kaptcha = captchaService.getCaptcha(key);

		CommonResult result = new CommonResult();

		if (StringUtils.isBlank(kaptcha) || !kaptcha.equals(captcha)) {
			result.error(2004, "验证码错误");
			return result;
		}

		Map<String, String> map = new HashMap<String, String>();
		result.error(0, "success");

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
		//记住我
		usernamePasswordToken.setRememberMe(rememberMe);
		try {
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);
			//获取用户所属数据库 并切库
			String dbName = memberService.queryDbNameByUserName(username);

			session.setAttribute("tenantName", dbName);
			//验证邮箱账号是否激活
			SysUserEntity byUsername = sysUserService.findByUsername(username);
			Integer status = byUsername.getStatus();
			if (byUsername != null && null != status && 0 == status) {
				result.error(ConstantUtil.MAIL_UNACTIVATE, "邮箱账号未激活！");
				return result;
			}
			subject.login(usernamePasswordToken);
			//过期时间为5个小时
			subject.getSession().setTimeout(18000000);

			String sessionId = (String) subject.getSession().getId();

			session.setAttribute("username", username);

			map.put("token", sessionId);
			map.put("userId", ShiroUtils.getUserId().toString());
			result.setDatas(map);
		} catch (AuthenticationException e) {
			result.error(2001, "\"账号\"错误，请重新输入");

		} catch (Exception e) {
			e.printStackTrace();
			result.error(2002, "未知异常");
		}
		return result;
	}

	@RequiresPermissions("test")
	@RequestMapping("/dotest")
	public Result secondApi() {

		Result result = new Result();
		result.error(1, "test success");
		return result;
	}

	/**
	 * 生成验证码2
	 *
	 * @throws IOException
	 */
	@RequestMapping("/getCaptcha")
	public String getCaptcha(@RequestParam(value = "key") String key) throws IOException {

		//生成文字验证码
		String text = producer.createText();
		captchaService.setCaptcha(new CaptchaInfo(key, text));
		return text;
	}

	/**
	 * 发送消息(手机注册忘记密码用)
	 *
	 * @param phone 用户手机
	 */
	@GetMapping(value = "/sendSms")
	@LogOperate(description = "发送注册验证码")
	public Result sendSms(@NotBlank(message = "手机号码不能为空") @RequestParam(value = "phone") String phone,
	                      @RequestParam(value = "isForget", defaultValue = "0") Integer isForget,HttpServletRequest request) {
		Result result = new Result();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);
			//验证是否合法
			if (!StringUtil.verifiPhone(phone)) {
				return result.error(ConstantUtil.PHONEERRO, ConstantUtil.PHONE_FORMAL_ERROR);
			}
			//区别注册和忘记密码
			if (1 != isForget) {
				//验证是否已注册  改为新规则
//				SysUserEntity user = sysUserService.findByUsername(phone);
				int count = companyService.selectCompanyCount(phone);
				if (count != 0) {
					return result.error(ConstantUtil.PHONEERRO, ConstantUtil.REGISTERED);
				}
			}
			String nonceStr = RandomCode.getNonceStr();
			String activeProfile = ApplicationContextUtils.getActiveProfile();
			//只有生产环境才发送
			String sendResult;
			if("prod".equals(activeProfile)){
				sendResult = SmsUtil.sendSms(phone, nonceStr);
			}
			//TODO 未来打开
			/**
			 * 云之讯短信平台
			 */
//			if (sendResult != null) {
//				Gson gson = new Gson();
//				Map<String, Map<String, Object>> resultMap = gson.fromJson(sendResult, new TypeToken<Map<String, Map<String, Object>>>() {
//				}.getType());
//				String respCode = (String) resultMap.get("resp").get("respCode");
//				if ("000000".equals(respCode)) {
//					smsService.setVerifiCode(new SmsInfo(phone, nonceStr));
//					result.setMsg(sendResult);
//					result.setRet(0);
//				} else {
//					result.setMsg("发送验证码失败");
//					result.setRet(1);
//				}
//			} else {
//				return result;
//			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return result.error(-1, "系统异常");
		}
		return result;
	}

	/**
	 * 注册账号（手机）
	 *
	 * @param sysUserRegist
	 * @return Result
	 */
	@PostMapping("/register")
	@LogOperate(description = "注册账号")
	public Result register(@Validated @RequestBody SysUserRegist sysUserRegist,HttpServletRequest request) {
		Result result = new Result();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);

			String username = sysUserRegist.getUsername();
			//验证是否已注册 新规则为从base查询是否注册

//			SysUserEntity user = sysUserService.findByUsername(username);
			int count = companyService.selectCompanyCount(username);
			if (count != 0) {
				return result.error(ConstantUtil.PHONEERRO, ConstantUtil.REGISTERED);
			}
			//保存到base数据库基本信息
			//查询空闲库
			List<DbInfo> dbInfos = dbInfoService.queryUnbound();
			if(CollectionUtils.isEmpty(dbInfos)||dbInfos.size()==0){
				return result.error(2006, "当前没有空闲数据库，请通知管理员新增");
			}
			DbInfo dbInfo = dbInfos.get(0);
			session.setAttribute("tenantName", dbInfo.getDbName());
			Company company =new Company();
			company.setDbId(dbInfo.getId());
			company.setUsername(username);
			Member member=new Member();
			member.setUsername(username);
			companyService.registerCompany(company,member);


			//验证验证码 TODO 需要验证时打开
//			String verifiCode = smsService.getVerifiCode(username);
//			if (!sysUserRegist.getVerifiCode().equals(verifiCode)) {
//				return result.error(ConstantUtil.PHONEERRO, ConstantUtil.VERIFICODE_ERROR);
//			}
			SysUserEntity sysUserEntity = new SysUserEntity();
			sysUserEntity.setUsername(username);
			sysUserEntity.setPassword(sysUserRegist.getPassword());
			sysUserEntity.setMobile(username);
			//为注册用户分配超管
			sysUserEntity.setDeptId(1L);
			//默认超管
			sysUserEntity.setUserId(1L);
			sysUserEntity.setDemoFlag(0);
			List<Long> roleList = new LinkedList<>();
			roleList.add(1L);
			sysUserEntity.setRoleIdList(roleList);
			//目前不支持分布式事务  重新save方法
			sysUserService.saveSys(sysUserEntity);
			//保存到注册表  不需要保存7.23
//			asyncTask.saveRegisterInfo(username);

			//发送邮件通知
			asyncTask.sendMail(username);
			//新增数据库和数据源
			asyncTask.addDbAndDs(dsBase);
			log.info(username + "注册成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}


	/**
	 * 忘记密码(手机)
	 *
	 * @param sysUserRegist
	 * @return Result
	 */
	@PostMapping("/forgetPwd")
	@LogOperate(description = "忘记账号")
	public Result forgetPwd(@RequestBody SysUserRegist sysUserRegist,HttpServletRequest request) {
		Result result = new Result();
		try {
			String kaptcha = captchaService.getCaptcha(sysUserRegist.getKey());

			//获取用户
			String username = sysUserRegist.getUsername();
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);
			String dbName = memberService.queryDbNameByUserName(username);
			session.setAttribute("tenantName", dbName);
			SysUserEntity user = sysUserService.findByUsername(username);
			//验证验证码
			String verifiCode = smsService.getVerifiCode(username);
			if (!sysUserRegist.getVerifiCode().equals(verifiCode)) {
				return result.error(ConstantUtil.PHONEERRO, ConstantUtil.VERIFICODE_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}

	/**
	 * 注册账号（邮箱）
	 *
	 * @param sysUserRegistMail
	 * @return Result
	 */
	@PostMapping("/registerMail")
	@LogOperate(description = "注册账号")
	public Result registerMail(@Validated @RequestBody SysUserRegistMail sysUserRegistMail,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try {
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);

			String kaptcha = captchaService.getCaptcha(sysUserRegistMail.getKey());
			//图形验证码
			if (StringUtils.isBlank(kaptcha) || !kaptcha.equals(sysUserRegistMail.getCaptcha())) {
				result.error(2004, "图形验证码错误");
				return result;
			}
			//验证是否已注册    新规则为从base查询是否注册
			String username = sysUserRegistMail.getUsername();
//			SysUserEntity user = sysUserService.findByUsername(username);
			int count = companyService.selectCompanyCount(username);
			if (count != 0) {
				return result.error(ConstantUtil.MAILERRO, ConstantUtil.MAILREGISTERED);
			}
			//保存到base数据库基本信息
			//查询空闲库
			List<DbInfo> dbInfos = dbInfoService.queryUnbound();
			if(CollectionUtils.isEmpty(dbInfos)){
				result.error(2006, "当前没有空闲数据库，请通知管理员新增");
			}
			DbInfo dbInfo = dbInfos.get(0);
			session.setAttribute("tenantName", dbInfo.getDbName());
			Company company =new Company();
			company.setDbId(dbInfo.getId());
			company.setUsername(username);
			Member member=new Member();
			member.setUsername(username);
			companyService.registerCompany(company,member);

			SysUserEntity sysUserEntity = new SysUserEntity();
			sysUserEntity.setUsername(username);
			sysUserEntity.setPassword(sysUserRegistMail.getPassword());
			sysUserEntity.setMobile(username);
			//为注册用户分配超管
			sysUserEntity.setDeptId(1L);
			//默认超管
			sysUserEntity.setUserId(1L);
			sysUserEntity.setDemoFlag(0);
			List<Long> roleList = new LinkedList<>();
			roleList.add(1L);
			sysUserEntity.setRoleIdList(roleList);
			//设置未激活
			sysUserEntity.setStatus(0);
			sysUserService.saveSys(sysUserEntity);
			//保存到注册表  不需要保存7.23
//			asyncTask.saveRegisterInfo(username);

			//发送验证邮件
//			sendActivateEmail(username,request);
			//发送邮件通知
			asyncTask.sendMail(username);
			//新增数据库和数据源
			asyncTask.addDbAndDs(dsBase);
			result.setDatas(username);
			log.info(username + "注册成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}

	/**
	 * 找回密码(邮箱)
	 *
	 * @param sysUserRegistMail
	 * @return Result
	 */
	@PostMapping("/forgetEmailPwd")
	@LogOperate(description = "忘记账号")
	public Result forgetEmailPwd(@RequestBody SysUserRegistMail sysUserRegistMail,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		try {
			String kaptcha = captchaService.getCaptcha(sysUserRegistMail.getKey());
			//图形验证码
			if (StringUtils.isBlank(kaptcha) || !kaptcha.equals(sysUserRegistMail.getCaptcha())) {
				result.error(2004, "图形验证码错误");
				return result;
			}
			//获取用户
			String username = sysUserRegistMail.getUsername();
			HttpSession session = request.getSession();
			session.setAttribute("dsBase", dsBase);
			String dbName = memberService.queryDbNameByUserName(username);
			session.setAttribute("tenantName", dbName);
			SysUserEntity user = sysUserService.findByUsername(username);
			//发送验证邮件
//			sendModifyEmail(username,request);
			result.setDatas(username);
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}

	/**
	 * 激活邮箱账户
	 *
	 * @param email
	 * @param ticket
	 * @return
	 */
	@GetMapping("/email/activate")
	@LogOperate(description = "激活邮箱账户")
	public Result activateEmail(@RequestParam(value = "email") String email, @RequestParam(value = "ticket") String ticket,HttpServletRequest request) {
		Result result = new Result();
		//校验ticket
		try {
			request.getSession().setAttribute("dsBase", dsBase);
			//获取用户所属数据库 并切库
			String dbName = memberService.queryDbNameByUserName(email);
			request.getSession().setAttribute("tenantName", dbName);
			boolean authState = iEmailAuthService.authState(email, ticket);
			if (authState) {
				//激活修改账户status为1
				sysUserService.updateUserStatus(email, 1);
			} else {
				return result.error(-1, "验证链接已失效，请重新发送邮件！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}

	/**
	 * 验证修改邮箱账户的凭证
	 *
	 * @param email
	 * @param authcode
	 * @return
	 */
	@GetMapping("/email/auth")
	@LogOperate(description = "验证修改邮箱账户的凭证")
	public Result activateEmailModify(@RequestParam(value = "email") String email, @RequestParam(value = "authcode") String authcode,HttpServletRequest request) {
		CommonResult result = new CommonResult();
		//获取用户所属数据库 并切库
		request.getSession().setAttribute("dsBase", dsBase);
		String dbName = memberService.queryDbNameByUserName(email);
		request.getSession().setAttribute("tenantName", dbName);
		//校验authCode
		boolean authState = iEmailAuthService.authState(email, authcode);
		if (authState) {
			Map<String, String> map = new HashMap<>(2);
			map.put("email", email);
			map.put("authcode", authcode);
			result.setDatas(map);
			result.ok();
		} else {
			result.error(-1, "验证链接已失效，请重新发送邮件！");
		}
		//返回认证成功
		return result;
	}

	/**
	 * 修改密码（手机&邮箱）
	 *
	 * @param sysUserRegistMail 账户
	 * @return
	 */
	@PostMapping("/modifyPwd")
	@LogOperate(description = "修改邮箱密码")
	public Result modifyEmailPwd(@RequestBody SysUserRegistMail sysUserRegistMail,HttpServletRequest request) {
		Result result = new Result();
		boolean authState;
		String username = "";
		try {
			Integer accountType = sysUserRegistMail.getAccountType();
			//手机校验
			if (accountType == null) {
				return result.error(-1, "账户类型必传！");
			}
			String password = sysUserRegistMail.getPassword();
			String repPassword = sysUserRegistMail.getRepPassword();
			if (StringUtils.isBlank(repPassword) || !repPassword.equals(password)) {
				return result.error(-1, "两次密码不一致！");
			}
			if (0 == accountType) {
				//验证验证码
				username = sysUserRegistMail.getUsername();
				//获取用户所属数据库 并切库
				request.getSession().setAttribute("dsBase", dsBase);
				String dbName = memberService.queryDbNameByUserName(username);
				request.getSession().setAttribute("tenantName", dbName);
				String verifiCode = smsService.getVerifiCode(username);
				String verifiCode1 = sysUserRegistMail.getVerifiCode();
				if (StringUtils.isBlank(verifiCode1) || !verifiCode1.equals(verifiCode)) {
					return result.error(ConstantUtil.PHONEERRO, ConstantUtil.VERIFICODE_ERROR);
				}
				authState = true;
			} else if (1 == accountType) {
				//邮箱校验authCode
				String authcode = sysUserRegistMail.getAuthcode();
				String email = sysUserRegistMail.getUsername();
				//获取用户所属数据库 并切库
				request.getSession().setAttribute("dsBase", dsBase);
				String dbName = memberService.queryDbNameByUserName(email);
				request.getSession().setAttribute("tenantName", dbName);
				authState = iEmailAuthService.authState(email, authcode);
				username = email;
			} else {
				return result.error(-1, "账户类型未知！");
			}
			if (authState) {
				//修改密码
				SysUserEntity byUsername = sysUserService.findByUsername(username);
				if (byUsername != null) {
					byUsername.setPassword(password);
					sysUserService.updateById(byUsername);
				}
			} else {
				return result.error(-1, "验证链接已失效，请重新发送邮件！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}

	/**
	 * 发送激活邮件
	 * @param username 用户名
	 * @return Result
	 */
	@GetMapping("/sendActivateEmail")
	public Result sendActivateEmail(@RequestParam(value = "username")String username ,HttpServletRequest request){
		Result result=new Result();
		try {
			Date deadline = DateUtil.addDay(7);
			UUID uuid = UUID.randomUUID();
			request.getSession().setAttribute("  dsBase", dsBase);
			//获取用户所属数据库 并切库
			String dbName = memberService.queryDbNameByUserName(username);
			request.getSession().setAttribute("tenantName", dbName);
			EmailAuth byEmail = iEmailAuthService.findByEmail(username);
			EmailAuth emailAuth = new EmailAuth(username, uuid.toString(), deadline);
			if (byEmail != null) {
				emailAuth.setId(byEmail.getId());
			}
			iEmailAuthService.save(emailAuth);
			asyncTask.sendHtmlMail("账号激活", username, SendMailUtil.getActivateContent(username, deadline, uuid.toString()));
			log.info(username + "发送激活邮件成功！");
		}catch (Exception e){
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}
	/**
	 * 发送修改验证邮件
	 * @param username 用户名
	 * @return Result
	 */
	@GetMapping("/sendModifyEmail")
	public Result sendModifyEmail(@RequestParam(value = "username")String username,HttpServletRequest request){
		Result result=new Result();
		try {
			Date deadline = DateUtil.addDay(7);
			UUID uuid = UUID.randomUUID();
			request.getSession().setAttribute("dsBase", dsBase);
			//获取用户所属数据库 并切库
			String dbName = memberService.queryDbNameByUserName(username);
			request.getSession().setAttribute("tenantName", dbName);
			EmailAuth byEmail = iEmailAuthService.findByEmail(username);
			EmailAuth emailAuth = new EmailAuth(username, uuid.toString(), deadline);
			if (byEmail != null) {
				emailAuth.setId(byEmail.getId());
			}
			iEmailAuthService.save(emailAuth);
			asyncTask.sendHtmlMail("找回密码", username, SendMailUtil.getModifyPwdContent(username, deadline, uuid.toString()));
			log.info(username + "发送找回邮箱邮件成功！");
		}catch (Exception e){
			e.printStackTrace();
			return result.error(-1, e.getMessage());
		}
		return result.ok();
	}
}
