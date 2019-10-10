package com.x.framework.boot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ztest.framework.core.base.Response;
import com.ztest.framework.core.base.ResponseCode;
import com.ztest.framework.core.exception.BusinessException;
import com.ztest.framework.core.exception.FatalException;
import com.ztest.framework.core.exception.HttpException;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 全局统一异常处理
 *
 * @author xiepeng
 * @version 1.0
 * @data 2019/4/3 0003 25
 */
@ControllerAdvice
public class ExceptionAdvice {

	@Value("${exception.printStackTrace.enable:false}")
	private boolean isPrintStackTrace;

	@Autowired
	private ObjectMapper objectMapper;

	@ResponseBody
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<Object> exceptionHandler(Exception e) {
		return new ResponseEntity<>(handleException(e, this.isPrintStackTrace), HttpStatus.OK);
	}

	public static ResponseEntity<Object> handleException(Exception e, boolean isPrintStackTrace) {
		Response<Object> response = null;
		HttpStatus status = HttpStatus.OK;

		if (e instanceof BusinessException) {
			// 自定义业务异常
			response = ((BusinessException) e).getResponse();
		} else if (e instanceof HttpException) {
			// 自定义http code异常
			HttpException biz = (HttpException) e;
			response = biz.getResponse();
			status = HttpStatus.valueOf(biz.getStatusCode());
		} else if (e instanceof FatalException) {
			// 自定义严重异常, 将异常相关数据置入logger的threadLocal中
			MDC.put("alter", "true");
			MDC.put("extension", getExceptionExtension((FatalException) e));
			response = new Response<>(false, ResponseCode.SYS_EXCEPTION.getCode(), ResponseCode.SYS_EXCEPTION.getShowMsg(),
					getStackTrace(e, isPrintStackTrace));
		} else if (e instanceof IllegalArgumentException) {
			response = new Response<>(false, ResponseCode.VALIDATE_EXCEPTION.getCode(), e.getMessage(),
					getStackTrace(e, isPrintStackTrace));
		} else {
			response = new Response<>(false, ResponseCode.SYS_EXCEPTION.getCode(), ResponseCode.SYS_EXCEPTION.getShowMsg(),
					getStackTrace(e, isPrintStackTrace));
		}
		return new ResponseEntity(response, status);
	}

	/**
	 * 获取FatalException数据
	 *
	 * @param fatalException
	 * @return
	 */
	private static String getExceptionExtension(FatalException fatalException) {
//		JSONObject extension = new JSONObject();
//		extension.put("tag", fatalException.getTag());
//		extension.put("args", JSON.toJSONString(fatalException.getArgs()));
//		return extension.toJSONString();
		return "";
	}

	/**
	 * 获取异常堆栈，用于返回调试
	 *
	 * @param e
	 * @param isPrintStackTrace
	 * @return
	 */
	private static String getStackTrace(Exception e, boolean isPrintStackTrace) {
		if (isPrintStackTrace) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			return sw.toString();
		} else {
			String errorMsg = e.getMessage();
			if (StringUtils.isEmpty(errorMsg)) {
				errorMsg = ResponseCode.SYS_EXCEPTION.getErrorMsg();
			}
			return errorMsg;
		}
	}
}
