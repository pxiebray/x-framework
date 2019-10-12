package com.x.framework.generator;

import java.io.File;
import java.io.IOException;

/**
 * 代码生成主体类
 * <p>
 * velocity生成文件：controller、service、serviceImpl、mapper，重复执行时不会覆盖已存在文件。
 * mybatis-generator生成文件：mapper.xml、mapper_ext.xml、entity
 *
 * @author xiepeng
 * @data 2019年10月11日13:33:52
 */
public class Generator {

	private String modelName;
	private String packageModelPath;

	/**
	 * JDBC 配置
	 */
	private String jdbcDriver;
	private String jdbcUrl;
	private String jdbcUserId;
	private String jdbcPassword;

	/**
	 * 目标文件需放置的工程名称
	 */
	private String controllerProjectName;
	private String mapperProjectName;
	private String serviceProjectName;
	private String modelProjectName;

	/**
	 * package layer name
	 */
	protected static final String PACKAGE_LAYER_CONTROLLER = "controller";
	protected static final String PACKAGE_LAYER_SERVICE = "service";
	protected static final String PACKAGE_LAYER_SERVICE_IMPL = "service/impl";
	protected static final String PACKAGE_LAYER_MAPPER = "dao";
	protected static final String PACKAGE_LAYER_MODEL = "entity";

	protected static final String JAVA_FILE_PATH = "/src/main/java";
	protected static final String RESOURCE_FILE_PATH = "/src/main/resources";

	public Generator(String modelName, String packageModelPath) {
		this.modelName = modelName;
		this.packageModelPath = packageModelPath;
	}

	/**
	 * 执行代码生成
	 * todo batch tables
	 */
	public void generator(String tableName) throws Exception {
		executeMybatisGenerator(tableName);
		executeVelocityGenerator();
	}

	/**
	 * 执行Mybatis代码生成
	 *
	 * @param tableName
	 */
	public void executeMybatisGenerator(String tableName) throws Exception {
		if (tableName == null || "".equals(tableName)) {
			throw new Exception("tableName不合法");
		}

		if (jdbcDriver == null || jdbcUrl == null || jdbcUserId == null || jdbcPassword == null) {
			throw new Exception("jdbc配置缺失");
		}
		MybatisGeneratorManager mybatisGeneratorManager = new MybatisGeneratorManager(jdbcDriver, jdbcUrl, jdbcUserId, jdbcPassword);
		mybatisGeneratorManager.generator(mapperProjectName, modelProjectName, packageModelPath, tableName, modelName);
	}

	/**
	 * 执行Velocity代码生产
	 */
	public void executeVelocityGenerator() throws Exception {
		VelocityManager.generatorController(controllerProjectName, packageModelPath, modelName);
		VelocityManager.generatorMapper(mapperProjectName, packageModelPath, modelName);
		VelocityManager.generatorService(serviceProjectName, packageModelPath, modelName);
		VelocityManager.generatorServiceImpl(serviceProjectName, packageModelPath, modelName);
	}

	/**
	 * 定位目标所在工程路径
	 *
	 * @param projectName 支持绝对路径，null表示当前工程，否则在本工程同级目录下寻找
	 * @return
	 *
	 * todo file separator
	 *
	 */
	protected static String getTargetProjectPath(String projectName) {
		if (projectName != null && (projectName.contains(File.separator) || projectName.contains("/"))) {
			return projectName;
		}

		String path = Generator.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (path.contains("/target")) {
			path = path.substring(0, path.indexOf("/target"));
		}

		if (projectName != null && !"".equals(projectName)) {
			path = path.substring(0, path.lastIndexOf("/"));
			path += "/" + projectName;
		}
		return path;
	}

	/**
	 * 定位目标文件路径，自动创建父文件夹
	 *
	 * @param projectName      目标所在的工程名称，用户传入, null则默认为当前工程
	 * @param packageModelPath 模块的package基础路径，用户传入
	 * @param packageLayerName 文件所在的package layer名程，使用约定配置
	 * @return
	 */
	protected static String getTargetFilePath(String projectName, String packageModelPath, String packageLayerName) throws IOException {
		String targetProjectPath = getTargetProjectPath(projectName);
		StringBuilder pathBuilder = new StringBuilder(targetProjectPath);
		pathBuilder.append(JAVA_FILE_PATH)
				.append("/")
				.append(packageModelPath.replaceAll("\\.", "/"));

		if (packageLayerName != null && !"".equals(packageLayerName)) {
			pathBuilder.append("/").append(packageLayerName);
		}

		String path = pathBuilder.toString();
		File directory = new File(path);
		if (!directory.exists()) {
			directory.mkdirs();
		} else if (!directory.isDirectory()) {
			throw new IOException("File exists and is not a directory. Unable to create directory." + path);
		}
		return path;
	}

	public String getPackageModelPath() {
		return packageModelPath;
	}

	public void setPackageModelPath(String packageModelPath) {
		this.packageModelPath = packageModelPath;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public String getControllerProjectName() {
		return controllerProjectName;
	}

	public void setControllerProjectName(String controllerProjectName) {
		this.controllerProjectName = controllerProjectName;
	}

	public String getMapperProjectName() {
		return mapperProjectName;
	}

	public void setMapperProjectName(String mapperProjectName) {
		this.mapperProjectName = mapperProjectName;
	}

	public String getServiceProjectName() {
		return serviceProjectName;
	}

	public void setServiceProjectName(String serviceProjectName) {
		this.serviceProjectName = serviceProjectName;
	}

	public String getModelProjectName() {
		return modelProjectName;
	}

	public void setModelProjectName(String modelProjectName) {
		this.modelProjectName = modelProjectName;
	}

	/**
	 * Builder
	 */
	public static class Builder {
		private String packageModelPath;
		private String modelName;

		private String jdbcDriver;
		private String jdbcUrl;
		private String jdbcUserId;
		private String jdbcPassword;

		private String controllerProjectName;
		private String mapperProjectName;
		private String serviceProjectName;
		private String modelProjectName;

		public Builder setPackageModelPath(String packageModelPath) {
			this.packageModelPath = packageModelPath;
			return this;
		}

		public Builder setModelName(String modelName) {
			this.modelName = modelName;
			return this;
		}

		/**
		 * controller文件所在工程名称，null for current project
		 *
		 * @param controllerProjectName
		 * @return
		 */
		public Builder setControllerProjectName(String controllerProjectName) {
			this.controllerProjectName = controllerProjectName;
			return this;
		}

		/**
		 * Mapper + xml文件所在工程名称，null for current project
		 *
		 * @param mapperProjectName
		 * @return
		 */
		public Builder setMapperProjectName(String mapperProjectName) {
			this.mapperProjectName = mapperProjectName;
			return this;
		}

		/**
		 * Service + ServiceImpl文件所在工程名称，null for current project
		 *
		 * @param serviceProjectName
		 * @return
		 */
		public Builder setServiceProjectName(String serviceProjectName) {
			this.serviceProjectName = serviceProjectName;
			return this;
		}

		/**
		 * model文件所在工程名称，null for current project
		 *
		 * @param modelProjectName
		 * @return
		 */
		public Builder setModelProjectName(String modelProjectName) {
			this.modelProjectName = modelProjectName;
			return this;
		}

		/**
		 * 配置jdbc链接，用于Mybatis-generator
		 *
		 * @param jdbcDriver
		 * @param jdbcUrl
		 * @param jdbcUserId
		 * @param jdbcPassword
		 * @return
		 */
		public Builder setJdbc(String jdbcDriver, String jdbcUrl, String jdbcUserId, String jdbcPassword) {
			this.jdbcDriver = jdbcDriver;
			this.jdbcUrl = jdbcUrl;
			this.jdbcUserId = jdbcUserId;
			this.jdbcPassword = jdbcPassword;
			return this;
		}

		public Generator build() {
			Generator generator = new Generator(this.modelName, this.packageModelPath);

			generator.setControllerProjectName(this.controllerProjectName);
			generator.setMapperProjectName(this.mapperProjectName);
			generator.setServiceProjectName(this.serviceProjectName);
			generator.setModelProjectName(this.modelProjectName);

			generator.setJdbcDriver(this.jdbcDriver);
			generator.setJdbcUrl(this.jdbcUrl);
			generator.setJdbcUserId(this.jdbcUserId);
			generator.setJdbcPassword(this.jdbcPassword);
			return generator;
		}
	}
}
