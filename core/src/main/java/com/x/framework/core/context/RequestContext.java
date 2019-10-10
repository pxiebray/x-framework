package com.x.framework.core.context;

/**
 * 基于ThreadLocal的上下文数据<br/>
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class RequestContext {
	private static ThreadLocal<RequestHeader> context = new ThreadLocal<>();

	public static RequestHeader getRequestHeader() {
		RequestHeader obj = context.get();
		if (null == obj) {
			obj = new RequestHeader();
		}
		return obj;
	}

	public static void setRequestHeader(RequestHeader obj) {
		context.set(obj);
	}

	public static void reset() {
		context.remove();
	}

}
