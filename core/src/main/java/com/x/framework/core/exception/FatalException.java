package com.x.framework.core.exception;

import java.util.Map;

/**
 * 系统内严重异常<br/>
 * 记录异常相关数据，扩展接入异常告警体系
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class FatalException extends RuntimeException {
	private String tag;
	private Map<Object, Object> context;
	private Object[] args;

	public FatalException(String tag, Object... args) {
		this.tag = tag;
		this.args = args;
	}

	public FatalException(String tag, Map<Object, Object> context, Object[] args) {
		this.tag = tag;
		this.context = context;
		this.args = args;
	}
}
