package com.x.framework.core.exception;


import com.x.framework.core.base.Response;
import com.x.framework.core.base.ResponseCode;

/**
 * 业务通用异常<br/>
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class BusinessException extends RuntimeException {

	/**
	 * 异常相关信息
	 */
	private Response response;

	public BusinessException(String msg) {
		super(msg);
		this.response = new Response<>(false, ResponseCode.BUSINESS_EXCEPTION.getCode(), msg, msg);
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.response = new Response<>(false, code, msg, msg);
	}

	public BusinessException(String code, String msg, String errorMsg, Object data) {
		super(msg);
		this.response = new Response<>(false, code, msg, errorMsg, data);
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
