package com.x.framework.generator;

import com.x.framework.generator.config.JdbcConfig;
import com.x.framework.generator.config.TargetConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于Mybatis-generator的代码生成
 *
 * @author xiepeng
 * @version 1.0
 * @date 2019年10月12日16:32:05
 */
public class MybatisGeneratorManager {

	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUserId;
	private String jdbcPassword;

	public MybatisGeneratorManager(JdbcConfig jdbcConfig) throws Exception {
		if (jdbcConfig == null) {
			throw new Exception("请配置jdbcDriver/jdbcUrl/jdbcUserId/jdbcPassword");
		}

		this.jdbcDriver = jdbcConfig.getJdbcDriver();
		this.jdbcUrl = jdbcConfig.getJdbcUrl();
		this.jdbcUserId = jdbcConfig.getJdbcUserId();
		this.jdbcPassword = jdbcConfig.getJdbcPassword();
		if (jdbcDriver == null || jdbcUrl == null || jdbcUserId == null || jdbcPassword == null) {
			throw new Exception("请配置jdbcDriver/jdbcUrl/jdbcUserId/jdbcPassword");
		}
	}

	public MybatisGeneratorManager(String jdbcDriver, String jdbcUrl, String jdbcUserId, String jdbcPassword) throws Exception{
		if (jdbcDriver == null || jdbcUrl == null || jdbcUserId == null || jdbcPassword == null) {
			throw new Exception("请配置jdbcDriver/jdbcUrl/jdbcUserId/jdbcPassword");
		}
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUserId = jdbcUserId;
		this.jdbcPassword = jdbcPassword;
	}

	/**
	 * 生成xml + model
	 *
	 * @param tableName           表格
	 * @param entityName          实体类名称
	 */
	protected void generator(String tableName, String entityName, TargetConfig xmlConfig, TargetConfig modelConfig) throws IOException {
		// target project and package path
		String targetXmlProject = TargetPathUtil.getTargetFullSourcePath(xmlConfig.getProjectName(), xmlConfig.getSourcePath());
		String targetModelProject = TargetPathUtil.getTargetFullSourcePath(modelConfig.getProjectName(), modelConfig.getSourcePath());
		File xmlDirectory = new File(targetXmlProject);
		File modelDirectory = new File(targetModelProject);
		if (!xmlDirectory.exists()) {
			xmlDirectory.mkdirs();
		}
		if (!modelDirectory.exists()) {
			modelDirectory.mkdirs();
		}

		String targetMapperPackage = TargetPathUtil.getFullPackagePath(xmlConfig.getPackagePath(), xmlConfig.getPackageLayer());
		String targetModelPackage = TargetPathUtil.getFullPackagePath(modelConfig.getPackagePath(), modelConfig.getPackageLayer());

		// configuration
		Configuration configuration = new Configuration();
		Context context = new Context(null);
		context.setId("generator");
		context.setTargetRuntime("MyBatis3");

		// jdbc connection
		JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
		jdbcConnectionConfiguration.setDriverClass(this.jdbcDriver);
		jdbcConnectionConfiguration.setConnectionURL(this.jdbcUrl);
		jdbcConnectionConfiguration.setUserId(this.jdbcUserId);
		jdbcConnectionConfiguration.setPassword(this.jdbcPassword);
		context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

		// tables
		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName(tableName);
		tableConfiguration.setDomainObjectName(entityName);
		context.addTableConfiguration(tableConfiguration);

		// javaModel
		JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
		javaModelGeneratorConfiguration.setTargetProject(targetModelProject);
		javaModelGeneratorConfiguration.setTargetPackage(targetModelPackage);
		javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		// xml
		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetProject(targetXmlProject);
		sqlMapGeneratorConfiguration.setTargetPackage(targetMapperPackage);
		sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
		context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

		// todo plugin
		configuration.addContext(context);

		try {
			List<String> warning = new ArrayList<>();
			MyBatisGenerator generator = new MyBatisGenerator(configuration, null, warning);
			generator.generate(null);
			warning.forEach(System.out::println);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
