package com.x.framework.boot.web;

import com.ztest.framework.core.base.Response;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一返回拦截处理
 *
 * @author xiepeng
 * @version 1.0
 * @data 2019/4/3 0003 25
 */
@ControllerAdvice(basePackages = "com")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return methodParameter.getMethod().getReturnType().isAssignableFrom(Response.class);
	}

	@Override
	public Object beforeBodyWrite(Object responseObject, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//		if(responseObject instanceof Response){
//			Response response = (Response)responseObject;
//		}
		return responseObject;
	}

}
