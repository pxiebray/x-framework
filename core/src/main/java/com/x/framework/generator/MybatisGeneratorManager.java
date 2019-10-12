package com.x.framework.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGeneratorManager {

	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUserId;
	private String jdbcPassword;

	public MybatisGeneratorManager(String jdbcDriver, String jdbcUrl, String jdbcUserId, String jdbcPassword) {
		this.jdbcDriver = jdbcDriver;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUserId = jdbcUserId;
		this.jdbcPassword = jdbcPassword;
	}

	/**
	 * 生成xml + model
	 *
	 * @param modelProject  model文件所在工程名称
	 * @param mapperProject mapper文件所在工程名称
	 * @param targetPackage       生成代码模块的package路径
	 * @param tableName           表格
	 * @param entityName          实体类名称
	 */
	public void generator(String modelProject, String mapperProject, String targetPackage, String tableName, String entityName) throws IOException {
		String targetMapperProject = Generator.getTargetProjectPath(modelProject);
		String targetModelProject = Generator.getTargetProjectPath(mapperProject);
		String targetMapperPackage = targetPackage + "." + Generator.PACKAGE_LAYER_MAPPER;
		String targetModelPackage = targetPackage + "." + Generator.PACKAGE_LAYER_MODEL;
		Configuration configuration = new Configuration();

		// context
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
		javaModelGeneratorConfiguration.setTargetPackage(targetModelPackage);
		javaModelGeneratorConfiguration.setTargetProject(targetModelProject + "/src/main/java");
		javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
		context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

		// xml
		SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
		sqlMapGeneratorConfiguration.setTargetPackage(targetMapperPackage);
		sqlMapGeneratorConfiguration.setTargetProject(targetMapperProject + "/src/main/java");
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
