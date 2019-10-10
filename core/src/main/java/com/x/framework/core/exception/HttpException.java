package com.x.framework.core.exception;

import com.x.framework.core.base.Response;
import org.springframework.http.HttpStatus;

/**
 * HTTP状态异常声明
 * 该异常仅声明需返回指定的http异常状态码，例如401等。使用时遵循HTTP状态码规范，仅用于请求级异常对接，常规业务不得接入。
 * 需要具体框架支撑对该类声明的解析处理，例如SpringMVC框架的统一异常处理器中扩展处理。
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class HttpException extends RuntimeException {

	/**
	 * 指定http返回状态码
	 */
	private Integer statusCode;

	/**
	 * 响应数据
	 */
	private Response response;

	public HttpException(int statusCode) {
		this.statusCode = statusCode;
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		this.response = new Response(httpStatus.is2xxSuccessful(), Integer.toString(httpStatus.value()), httpStatus.getReasonPhrase(), httpStatus.getReasonPhrase());
	}

	public HttpException(int statusCode, Response response) {
		this.statusCode = statusCode;
		this.response = response;
	}

	public HttpException(int statusCode, String msg) {
		this.statusCode = statusCode;
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		this.response = new Response(httpStatus.is2xxSuccessful(), Integer.toString(httpStatus.value()), msg, httpStatus.getReasonPhrase());
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
}
