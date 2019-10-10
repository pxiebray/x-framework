package com.x.framework.core.base;

import com.x.framework.core.exception.BusinessException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 统一请求返回工具类<br/>
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class ResponseUtils {

	/**
	 * 返回空成功
	 *
	 * @return
	 */
	public static <T> Response<T> returnSuccess() {
		return new Response(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "", null);
	}

	/**
	 * 返回对象结果
	 *
	 * @param t
	 * @return
	 */
	public static <T> Response<T> returnObjectSuccess(T t) {
		if (t == null) {
			return new Response(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "", null);
		} else {
			return new Response(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "", t);
		}
	}

	/**
	 * 返回列表结果
	 *
	 * @param collection
	 * @return
	 */
	public static <T> Response<T> returnListSuccess(Collection<?> collection) {
		if (collection == null) {
			return new Response(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "", new ArrayList());
		} else {
			return new Response(true, ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "", collection);
		}
	}

	/**
	 * 返回异常消息
	 *
	 * @param msg 异常提示信息
	 * @return
	 */
	public static <T> Response<T> returnCommonException(String msg) {
		return new Response<T>(false, ResponseCode.SYS_EXCEPTION.getCode(), msg, msg, null);
	}

	/**
	 * 返回异常信息
	 *
	 * @param e
	 * @return
	 */
	public static <T> Response<T> returnException(Exception e) {
		if (e instanceof BusinessException) {
			BusinessException qe = (BusinessException) e;
			return qe.getResponse();
		} else if (e instanceof IllegalArgumentException) {
			IllegalArgumentException ll = (IllegalArgumentException) e;
			return new Response<T>(false, ResponseCode.VALIDATE_EXCEPTION.getCode(), ResponseCode.VALIDATE_EXCEPTION.getMsg(), ll.getMessage() + " [Exception]:" + e, null);
		} else if (e instanceof MissingServletRequestParameterException) {
			MissingServletRequestParameterException msrp = (MissingServletRequestParameterException) e;
			return new Response<T>(false, ResponseCode.VALIDATE_EXCEPTION.getCode(), ResponseCode.VALIDATE_EXCEPTION.getMsg(), msrp.getMessage() + " [Exception]:" + e, null);
		} else {
			return new Response<T>(false, ResponseCode.SYS_EXCEPTION.getCode(), ResponseCode.SYS_EXCEPTION.getMsg(), ResponseCode.SYS_EXCEPTION.getErrorMsg() + " [Exception]:" + e, null);
		}
	}


	/**
	 * 解析Response结果
	 * 当res为null，或res的响应码为非成功状态时，直接抛出异常
	 *
	 * @param res
	 * @return T
	 * @throws
	 */
	public static <T> T getResponseData(Response<T> res) {
		if (null == res) {
			throw new BusinessException(ResponseCode.SYS_EXCEPTION.getCode(), "response object must not null", "response object must not null", null);
		}

		if (!res.success() || !ResponseCode.SUCCESS.getCode().equals(res.getCode())) {
			throw new BusinessException(res.getCode(), res.getMsg(), res.getErrorMsg(), res.getData());
		}
		return res.getData();
	}
}
