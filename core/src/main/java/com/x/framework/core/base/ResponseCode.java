package com.x.framework.core.base;

/**
 * 通用返回码枚举<br/>
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public enum ResponseCode {

	//成功
	SUCCESS("200", "success", "success"),

	//系统级异常
	SYS_EXCEPTION("100", "网络开小差了，请稍候再试！", "系统异常"),
	TOKEN_INVALID("101", "TOKEN无效", "TOKEN无效"),
	SORT_TOKEN_INVALID("102", "短TOKEN过期", "短TOKEN过期"),
	LONG_TOKEN_INVALID("103", "长TOKEN过期", "长TOKEN过期"),
	USER_FROZEN("104", "用户被冻结", "用户被冻结"),

	//业务级异常
	BUSINESS_EXCEPTION("1000", "网络开小差了，请稍后再试", "业务逻辑异常"),
	VALIDATE_EXCEPTION("2000", "网络开小差了，请稍候再试！", "数据验证失败！");


	private String code;
	private String msg;
	private String errorMsg;

	ResponseCode(String code, String msg, String errorMsg) {
		this.code = code;
		this.msg = msg;
		this.errorMsg = errorMsg;
	}

	/**
	 * @return code
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}

	/**
	 * @return errorMsg
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

}
