package com.xiaohe.mapshow.config.validate;

import com.xiaohe.mapshow.modules.errorlog.entity.ErrorLog;
import com.xiaohe.mapshow.modules.errorlog.service.ErrorLogService;
import com.xiaohe.mapshow.utils.HttpHelper;
import com.xiaohe.mapshow.utils.IPUtils;
import com.xiaohe.mapshow.utils.Result;
import com.xiaohe.mapshow.utils.StringUtil;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 捕获异常
 *
 * @author hzh
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@Autowired
	private ErrorLogService errorLogService;

	private final static Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

	/**
	 * 声明要捕获的异常
	 */
	@ExceptionHandler(XException.class)
	@ResponseBody
	public Result defaultExceptionHandler(XException e) {
		//记录异常日志
		if (e.getRet() != 4001) {
			saveSysLog(StringUtil.errorInfo(e));
		}
		Result result = new Result();
		return result.error(e.getRet(), e.getMsg());

	}

	/**
	 * 入参校验异常
	 *
	 * @param e
	 * @return Result
	 * @throws Exception
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseBody
	public Result bindExceptionErrorHandler(MethodArgumentNotValidException e) {
		saveSysLog(StringUtil.errorInfo(e));
		Result r = new Result();
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		StringBuilder sb = new StringBuilder();
		if (!CollectionUtils.isEmpty(fieldErrors)) {
			for (FieldError fieldError : fieldErrors) {
				sb.append(fieldError.getDefaultMessage() + ",");
			}
		}
		return r.error(-1, sb.toString());
	}

	/**
	 * 不可读 转化异常
	 *
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	@ResponseBody
	public Result bindExceptionErrorHandler(HttpMessageNotReadableException e) {
		saveSysLog(StringUtil.errorInfo(e));
		Result r = new Result();
		String message = e.getMessage();
		if (!StringUtils.isEmpty(message)) {
			message = "请输入合法参数！";
		}
		return r.error(-1, message);
	}

	/**
	 * 全局异常
	 *
	 * @param e
	 * @return Result
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Result exceptionErrorHandler(Exception e) {
		saveSysLog(StringUtil.errorInfo(e));
		Result r = new Result();
		String message = e.getMessage();
		if (!StringUtils.isEmpty(message)) {
			message = "系统异常！";
		}
		return r.error(-101, message);
	}

	/**
	 * 没有权限异常
	 *
	 * @param e
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(value = AuthorizationException.class)
	@ResponseBody
	public Result bindExceptionErrorHandler(AuthorizationException e) {
		saveSysLog(StringUtil.errorInfo(e));
		logger.error("没有权限");
		return new Result().error(-401, "没有权限");
	}

	/**
	 * 记录异常日志
	 *
	 * @param errorMsg
	 */
	private void saveSysLog(String errorMsg) {//request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Map parameterMap = request.getParameterMap();
		ErrorLog errorLog = new ErrorLog();
		String requestURI = request.getRequestURI();
		String method = request.getMethod();
		errorLog.setReqType(method);
		String bodyString = null;
		String reqParam = null;
		try {
			if (RequestMethod.GET.name().equals(method) || RequestMethod.DELETE.name().equals(method)) {
				reqParam = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.length());
			} else {
				bodyString = HttpHelper.getBodyString(request);
				reqParam = CollectionUtils.isEmpty(parameterMap) ? bodyString : parameterMap.toString();
			}
		} catch (IOException e) {
			errorMsg += StringUtil.errorInfo(e);
		}
		errorLog.setReqParam(reqParam);
		errorLog.setReqUrl(requestURI);
		errorLog.setIp(IPUtils.getIpAddr(request));
		errorLog.setUserAgent(request.getHeader("User-Agent"));
		errorLog.setCreateTime(new Date());
		errorLog.setErrorInfo(errorMsg);

		//保存异常日志
		errorLogService.save(errorLog);
		logger.info("记录异常日志......");
	}

}