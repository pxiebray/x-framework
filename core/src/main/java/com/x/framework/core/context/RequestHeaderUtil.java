package com.x.framework.core.context;

import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.HttpRequestWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Header 上下文工具类
 *
 * @author xiepeng
 * @version 1.0
 * @date 2018年1月8日 上午11:18:39
 */
public class RequestHeaderUtil {

	public static RequestHeader getHeaderByServletRequest(HttpServletRequest request) {
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setTenantId(request.getHeader("tenantId"));
		requestHeader.setAppVersion(request.getHeader("appVersion"));
		requestHeader.setClientVersion(request.getHeader("clientVersion"));
		requestHeader.setDevId(request.getHeader("devId"));
		requestHeader.setDevName(request.getHeader("devName"));
		requestHeader.setDevType(request.getHeader("devType"));
		requestHeader.setDitchCode(request.getHeader("ditchCode"));
		requestHeader.setIp(request.getHeader("ip"));
		requestHeader.setNet(request.getHeader("net"));
		requestHeader.setSign(request.getHeader("sign"));
		requestHeader.setToken(request.getHeader("token"));
		requestHeader.setUserAgent(request.getHeader("User-Agent"));
		requestHeader.setUserId(request.getHeader("userId"));
		requestHeader.setxForwardedFor(getClientIP(request));
		return requestHeader;
	}

	/**
	 * 用openApi的header覆盖内部restTemplate的header, restTemplate为空的既覆盖
	 *
	 * @param apiHeader  用户入口header
	 * @param request    RestTemplate即将发出的请求
	 */
	public static void convertClientHeader(RequestHeader apiHeader, HttpRequestWrapper request) {
		if (apiHeader == null) {
			return;
		}

		HttpHeaders headers = request.getHeaders();
		if (StringUtils.isBlank(headers.getFirst("tenantId"))) {
			request.getHeaders().set("tenantId", apiHeader.getTenantId());
		}
		if (StringUtils.isBlank(headers.getFirst("appVersion"))) {
			request.getHeaders().set("appVersion", apiHeader.getAppVersion());
		}
		if (StringUtils.isBlank(headers.getFirst("clientVersion"))) {
			request.getHeaders().set("clientVersion", apiHeader.getClientVersion());
		}
		if (StringUtils.isBlank(headers.getFirst("devId"))) {
			request.getHeaders().set("devId", apiHeader.getDevId());
		}
		if (StringUtils.isBlank(headers.getFirst("devName"))) {
			request.getHeaders().set("devName", apiHeader.getDevName());
		}
		if (StringUtils.isBlank(headers.getFirst("devType"))) {
			request.getHeaders().set("devType", apiHeader.getDevType());
		}
		if (StringUtils.isBlank(headers.getFirst("ditchCode"))) {
			request.getHeaders().set("ditchCode", apiHeader.getDitchCode());
		}
		if (StringUtils.isBlank(headers.getFirst("ip"))) {
			request.getHeaders().set("ip", apiHeader.getIp());
		}
		if (StringUtils.isBlank(headers.getFirst("net"))) {
			request.getHeaders().set("net", apiHeader.getNet());
		}
		if (StringUtils.isBlank(headers.getFirst("sign"))) {
			request.getHeaders().set("sign", apiHeader.getSign());
		}
		if (StringUtils.isBlank(headers.getFirst("token"))) {
			request.getHeaders().set("token", apiHeader.getIp());
		}
		if (StringUtils.isBlank(headers.getFirst("User-Agent"))) {
			request.getHeaders().set("User-Agent", apiHeader.getUserAgent());
		}
		if (StringUtils.isBlank(headers.getFirst("userId"))) {
			request.getHeaders().set("userId", apiHeader.getUserId());
		}
		if (StringUtils.isBlank(headers.getFirst("X-Forwarded-For"))) {
			request.getHeaders().set("X-Forwarded-For", apiHeader.getxForwardedFor());
		}

	}

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
		return ip;
	}
}
