package com.x.framework.boot;

import com.x.framework.boot.web.ExceptionAdvice;
import com.x.framework.boot.web.ResponseAdvice;
import com.x.framework.boot.web.client.HttpRequestInterceptor;
import com.x.framework.boot.web.filter.RequestContextFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BaseAutoConfiguration {


	@ConditionalOnMissingBean(RestTemplate.class)
//	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new HttpRequestInterceptor());
		return restTemplate;
	}

	@Bean
	public ResponseAdvice responseAdvice() {
		return new ResponseAdvice();
	}

	@Bean
	public ExceptionAdvice exceptionAdvice() {
		return new ExceptionAdvice();
	}

	@Bean
	public RequestContextFilter requestContextFilter() {
		return new RequestContextFilter();
	}

}
