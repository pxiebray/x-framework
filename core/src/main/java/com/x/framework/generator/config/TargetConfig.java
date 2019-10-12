package com.x.framework.generator.config;

/**
 * 目标文件相关配置
 * 文件路径：由四部分组成{projectPath}/{sourcePath}/{packagePath}/{packageLayer}
 *
 * @author xiepeng
 * @version 1.0
 * @date 2019年10月12日16:32:05
 */
public class TargetConfig {

	/**
	 * 目标工程名称,如demo-service，默认null为当前工程
	 */
	private String projectName;

	/**
	 * 目标路径，默认为/src/main/java
	 */
	private String sourcePath;

	/**
	 * 目标模块package路径，如com.x.test
	 */
	private String packagePath;

	/**
	 * 目标模块package Layer名称，如controller
	 */
	private String packageLayer;

	/**
	 * 默认package Layer值
	 */
	public static final String PACKAGE_LAYER_CONTROLLER = "controller";
	public static final String PACKAGE_LAYER_SERVICE = "service";
	public static final String PACKAGE_LAYER_SERVICE_IMPL = "service/impl";
	public static final String PACKAGE_LAYER_MAPPER = "dao";
	public static final String PACKAGE_LAYER_MODEL = "entity";

	/**
	 * 默认sourcePath路径
	 */
	public static final String SOURCE_PATH_JAVA = "src/main/java";
	public static final String SOURCE_PATH_RESOURCES = "src/main/resources";

	public TargetConfig() {
	}

	public TargetConfig(String projectName) {
		this.projectName = projectName;
	}

	public TargetConfig(String projectName, String sourcePath) {
		this.projectName = projectName;
		this.sourcePath = sourcePath;
	}

	public TargetConfig(String projectName, String sourcePath, String packagePath) {
		this.projectName = projectName;
		this.sourcePath = sourcePath;
		this.packagePath = packagePath;
	}

	public TargetConfig(String projectName, String sourcePath, String packagePath, String packageLayer) {
		this.projectName = projectName;
		this.sourcePath = sourcePath;
		this.packagePath = packagePath;
		this.packageLayer = packageLayer;
	}

	/**
	 * 填充NULL配置
	 *
	 * @param sourcePath
	 * @param packagePath
	 * @param packageLayer
	 */
	public void fillNull(String sourcePath, String packagePath, String packageLayer) {
		if (this.sourcePath == null) {
			this.sourcePath = sourcePath;
		}
		if (this.packagePath == null) {
			this.packagePath = packagePath;
		}
		if (this.packageLayer == null) {
			this.packageLayer = packageLayer;
		}
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getPackageLayer() {
		return packageLayer;
	}

	public void setPackageLayer(String packageLayer) {
		this.packageLayer = packageLayer;
	}
}
