package com.x.framework.generator;

import com.x.framework.generator.config.TargetConfig;

import java.io.File;

/**
 * 目标文件路径工具
 *
 * @author xiepeng
 * @data 2019年10月11日13:33:52
 */
public class TargetPathUtil {

	/**
	 * 定位目标所在工程路径
	 *
	 * @param projectName 支持绝对路径，null表示使用当前工程，否则在本工程同级目录下寻找
	 * @return
	 */
	protected static String getTargetProjectPath(String projectName) {
		if (projectName != null && (projectName.contains(File.separator) || projectName.contains("/"))) {
			// project name is absolute path
			return projectName;
		}

		// current project path
		String path = TargetPathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (path.contains("/target")) {
			path = path.substring(0, path.indexOf("/target"));
		}

		// change to the target project path
		if (projectName != null && !"".equals(projectName)) {
			path = path.substring(0, path.lastIndexOf("/"));
			path += "/" + projectName;
		}
		return path;
	}

	/**
	 * 获取目标所在工程的资源文件路径
	 * 如{project path}/src/main/java
	 *
	 * @param projectName
	 * @param sourcePath
	 * @return
	 */
	protected static String getTargetFullSourcePath(String projectName, String sourcePath) {
		String projectPath = getTargetProjectPath(projectName);
		if (sourcePath != null && !"".equals(sourcePath)) {
			projectPath = projectPath + "/" + sourcePath;
		}
		return projectPath;
	}

	/**
	 * 获取目标文件完整路径
	 *
	 * @param targetConfig
	 * @return
	 */
	protected static String getTargetFilePath(TargetConfig targetConfig) {
		return getTargetFilePath(targetConfig.getProjectName(), targetConfig.getSourcePath(), targetConfig.getPackagePath(), targetConfig.getPackageLayer());
	}

	/**
	 * 获取目标文件完整路径
	 *
	 * @param projectName
	 * @param sourcePath
	 * @param packagePath
	 * @param packageLayer
	 * @return
	 */
	protected static String getTargetFilePath(String projectName, String sourcePath, String packagePath, String packageLayer) {
		String fullSourcePath = getTargetFullSourcePath(projectName, sourcePath);
		String fullPackagePath = getFullPackagePath(packagePath, packageLayer);

		String filePath = fullSourcePath;
		if (fullPackagePath != null) {
			filePath = fullSourcePath + "/" + fullPackagePath.replaceAll("\\.", "/");
		}
		return filePath;
	}

	/**
	 * 获取package全限定名称
	 *
	 * @param packagePath
	 * @param packageLayer
	 * @return
	 */
	protected static String getFullPackagePath(String packagePath, String packageLayer) {
		String fullPackagePath = packagePath;
		if (packageLayer != null && !"".equals(packageLayer)) {
			fullPackagePath = fullPackagePath + "." + packageLayer;
		}
		return fullPackagePath;
	}
}
