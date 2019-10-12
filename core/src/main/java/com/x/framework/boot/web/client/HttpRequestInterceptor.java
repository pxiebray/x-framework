package com.x.framework.boot.web.client;

import com.x.framework.core.context.RequestContext;
import com.x.framework.core.context.RequestHeader;
import com.x.framework.core.context.RequestHeaderUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

import java.io.IOException;

/**
 * HttpClient 拦截器
 * 用于拦截对外Http请求，通过header实现链路数据传递
 *
 * @author xiepeng
 * @version 1.0
 * @data 2019/4/3 0003 49
 */
public class HttpRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(httpRequest);
		RequestHeader apiHeader = RequestContext.getRequestHeader();
		RequestHeaderUtil.convertClientHeader(apiHeader, requestWrapper);
		return clientHttpRequestExecution.execute(requestWrapper, bytes);
	}
}
