package com.x.framework.component.generator.config;

/**
 * JDBC 链接配置<br/>
 *
 * @author xiepeng
 * @version 1.0
 * @date 2019年10月12日16:32:05
 */
public class JdbcConfig {

	/**
	 * jdbc driver class name
	 */
	private String jdbcDriver;

	/**
	 * jdbc connect url
	 */
	private String jdbcUrl;

	/**
	 * database username
	 */
	private String jdbcUserId;

	/**
	 * database password
	 */
	private String jdbcPassword;

	public JdbcConfig() {

	}

	public JdbcConfig(String jdbcDriver, String jdbcUrl, String jdbcUserId, String jdbcPassword) {
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUserId = jdbcUserId;
		this.jdbcPassword = jdbcPassword;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getJdbcUserId() {
		return jdbcUserId;
	}

	public void setJdbcUserId(String jdbcUserId) {
		this.jdbcUserId = jdbcUserId;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

	public void setJdbcPassword(String jdbcPassword) {
		this.jdbcPassword = jdbcPassword;
	}
}
