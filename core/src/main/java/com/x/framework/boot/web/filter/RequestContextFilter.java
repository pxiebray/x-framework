package com.x.framework.boot.web.filter;

import com.ztest.framework.core.context.RequestContext;
import com.ztest.framework.core.context.RequestHeader;
import com.ztest.framework.core.context.RequestHeaderUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 上下文过滤器
 * 拦截Servlet请求，将请求的Header信息置入RequestContext
 *
 * @author xiepeng
 * @version 1.0
 * @data 2019/4/3 0003 49
 * @see RequestContext
 */
public class RequestContextFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) servletRequest;
			RequestHeader requestHeader = RequestHeaderUtil.getHeaderByServletRequest(request);
			RequestContext.setRequestHeader(requestHeader);
		} catch (Exception e) {
			// 不处理 没有request header没关系
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
