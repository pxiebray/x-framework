package com.x.framework.generator;

import com.x.framework.generator.config.JdbcConfig;
import com.x.framework.generator.config.TargetConfig;

/**
 * 代码生成工具主体类
 *
 * @author xiepeng
 * @version 1.0
 * @date 2019年10月12日16:32:05
 */
public class Generator {

	/**
	 * 模块名称，对应实体Entity类名
	 */
	private String modelName;

	/**
	 * 模块Package路径
	 */
	private String packagePath;

	/**
	 * jdbc连接配置
	 */
	private JdbcConfig jdbcConfig;

	/**
	 * 目标文件相关可选配置
	 */
	private TargetConfig controllerTargetConfig;
	private TargetConfig serviceTargetConfig;
	private TargetConfig serviceImplTargetConfig;
	private TargetConfig mapperTargetConfig;
	private TargetConfig xmlTargetConfig;
	private TargetConfig modelTargetConfig;

	public Generator(String modelName, String packagePath) throws Exception {
		if (modelName == null || "".equals(modelName.trim())) {
			throw new Exception("缺少modelName");
		}
		if (packagePath == null || "".equals(packagePath.trim())) {
			throw new Exception("缺少packagePath");
		}
		this.modelName = modelName;
		this.packagePath = packagePath;
	}

	/**
	 * 执行代码生成
	 *
	 * @param tableNames
	 * @throws Exception
	 */
	public void generator(String tableNames) throws Exception {
		if (tableNames == null) {
			throw new Exception("需要填写tableNames");
		}

		// parse default config
		parseDefaultConfig();

		// do generator
		generatorMybatis(tableNames);
		generatorVelocity();
	}

	public void generatorMybatis(String tableNames) throws Exception {
		MybatisGeneratorManager mybatisGeneratorManager = new MybatisGeneratorManager(jdbcConfig);
		mybatisGeneratorManager.generator(tableNames, modelName, mapperTargetConfig, modelTargetConfig);
	}

	public void generatorVelocity() throws Exception {
		VelocityManager.generatorServiceImpl(modelName, serviceImplTargetConfig);
		VelocityManager.generatorService(modelName, serviceTargetConfig);
		VelocityManager.generatorMapper(modelName, mapperTargetConfig);
		VelocityManager.generatorController(modelName, controllerTargetConfig);
	}

	/**
	 * 构建默认配置,使用默认值填充NULL配置
	 */
	private void parseDefaultConfig() {
		if (serviceTargetConfig == null) {
			serviceTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_SERVICE);
		} else {
			serviceTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_SERVICE);
		}

		if (serviceImplTargetConfig == null) {
			serviceImplTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_SERVICE_IMPL);
		} else {
			serviceImplTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_SERVICE_IMPL);
		}

		if (controllerTargetConfig == null) {
			controllerTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_CONTROLLER);
		} else {
			controllerTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_CONTROLLER);
		}

		if (modelTargetConfig == null) {
			modelTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_MODEL);
		} else {
			modelTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_MODEL);
		}

		if (mapperTargetConfig == null) {
			mapperTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_MAPPER);
		} else {
			modelTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_MAPPER);
		}

		if (xmlTargetConfig == null) {
			xmlTargetConfig = new TargetConfig(null, TargetConfig.SOURCE_PATH_JAVA,
					packagePath, TargetConfig.PACKAGE_LAYER_MAPPER);
		} else {
			xmlTargetConfig.fillNull(TargetConfig.SOURCE_PATH_JAVA, packagePath, TargetConfig.PACKAGE_LAYER_MAPPER);
		}
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}

	public void setJdbcConfig(JdbcConfig jdbcConfig) {
		this.jdbcConfig = jdbcConfig;
	}

	public TargetConfig getControllerTargetConfig() {
		return controllerTargetConfig;
	}

	public void setControllerTargetConfig(TargetConfig controllerTargetConfig) {
		this.controllerTargetConfig = controllerTargetConfig;
	}

	public TargetConfig getServiceTargetConfig() {
		return serviceTargetConfig;
	}

	public void setServiceTargetConfig(TargetConfig serviceTargetConfig) {
		this.serviceTargetConfig = serviceTargetConfig;
	}

	public TargetConfig getMapperTargetConfig() {
		return mapperTargetConfig;
	}

	public void setMapperTargetConfig(TargetConfig mapperTargetConfig) {
		this.mapperTargetConfig = mapperTargetConfig;
	}

	public TargetConfig getModelTargetConfig() {
		return modelTargetConfig;
	}

	public void setModelTargetConfig(TargetConfig modelTargetConfig) {
		this.modelTargetConfig = modelTargetConfig;
	}

	public TargetConfig getServiceImplTargetConfig() {
		return serviceImplTargetConfig;
	}

	public void setServiceImplTargetConfig(TargetConfig serviceImplTargetConfig) {
		this.serviceImplTargetConfig = serviceImplTargetConfig;
	}

	public TargetConfig getXmlTargetConfig() {
		return xmlTargetConfig;
	}

	public void setXmlTargetConfig(TargetConfig xmlTargetConfig) {
		this.xmlTargetConfig = xmlTargetConfig;
	}

	/**
	 * Build
	 */
	public static class Builder {
		private String modelName;
		private String packagePath;
		private JdbcConfig jdbcConfig;

		private TargetConfig controllerTargetConfig;
		private TargetConfig serviceTargetConfig;
		private TargetConfig serviceImplTargetConfig;
		private TargetConfig mapperTargetConfig;
		private TargetConfig xmlTargetConfig;
		private TargetConfig modelTargetConfig;

		public Builder setModelName(String modelName) {
			this.modelName = modelName;
			return this;
		}

		public Builder setPackagePath(String packagePath) {
			this.packagePath = packagePath;
			return this;
		}

		public Builder setJdbcConfig(JdbcConfig jdbcConfig) {
			this.jdbcConfig = jdbcConfig;
			return this;
		}

		public Builder setControllerTargetConfig(TargetConfig controllerTargetConfig) {
			this.controllerTargetConfig = controllerTargetConfig;
			return this;
		}

		public Builder setServiceTargetConfig(TargetConfig serviceTargetConfig) {
			this.serviceTargetConfig = serviceTargetConfig;
			return this;
		}

		public Builder setMapperTargetConfig(TargetConfig mapperTargetConfig) {
			this.mapperTargetConfig = mapperTargetConfig;
			return this;
		}

		public Builder setModelTargetConfig(TargetConfig modelTargetConfig) {
			this.modelTargetConfig = modelTargetConfig;
			return this;
		}

		public Builder setServiceImplTargetConfig(TargetConfig serviceImplTargetConfig) {
			this.serviceImplTargetConfig = serviceImplTargetConfig;
			return this;
		}

		public Builder setXmlTargetConfig(TargetConfig xmlTargetConfig) {
			this.xmlTargetConfig = xmlTargetConfig;
			return this;
		}

		public Generator build() throws Exception {
			Generator generator = new Generator(modelName, packagePath);
			generator.setJdbcConfig(jdbcConfig);
			generator.setControllerTargetConfig(controllerTargetConfig);
			generator.setMapperTargetConfig(mapperTargetConfig);
			generator.setXmlTargetConfig(xmlTargetConfig);
			generator.setServiceTargetConfig(serviceTargetConfig);
			generator.setServiceImplTargetConfig(serviceImplTargetConfig);
			generator.setModelTargetConfig(modelTargetConfig);
			return generator;
		}
	}
}
