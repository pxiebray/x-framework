package com.x.framework.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Properties;

/**
 * Velocity负责Controller/IService/ServiceImpl/Mapper四个文件的生成
 * 因为这4类文件是业务开发的主体部分，故生成后不允许被生成器再次覆盖，否则易造成业务代码丢失。
 *
 * @author xiepeng
 * @data 2019年10月11日13:33:52
 */
public class VelocityManager {

	//velocity template name
	private static final String fileEncoding = "utf-8";
	private static final String TEMPLATE_CONTROLLER = "template/Controller.vm";
	private static final String TEMPLATE_SERVICE = "template/Service.vm";
	private static final String TEMPLATE_MAPPER = "template/Mapper.vm";
	private static final String TEMPLATE_SERVICE_IMPL = "template/ServiceImpl.vm";



	/**
	 * 配置Velocity引擎从classpath中加载模板
	 */
	static {
		Properties properties = new Properties();
		properties.setProperty("resource.loader", "class");
		properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(properties);
	}

	/**
	 * 创建Controller模板文件
	 *
	 * @param projectName      目标所在工程名称
	 * @param packageModelPath 目标所在模块的package路径
	 * @param modelName        用于构建文件名称，对应模块名并首字母大写
	 * @throws Exception
	 */
	protected static void generatorController(String projectName, String packageModelPath, String modelName) throws Exception {
		String targetFilePath = Generator.getTargetFilePath(projectName, packageModelPath, Generator.PACKAGE_LAYER_CONTROLLER);
		targetFilePath += "/" + modelName + "Controller.java";

		VelocityContext context = new VelocityContext();
		context.put("modelName", modelName);
		context.put("ctime", new Date().toString());
		context.put("packageName", packageModelPath);

		doGenerator(TEMPLATE_CONTROLLER, context, targetFilePath);
	}

	/**
	 * 创建Service模板文件
	 *
	 * @param projectName      目标所在工程名称
	 * @param packageModelPath 目标所在模块的package路径
	 * @param modelName        用于构建文件名称，对应模块名并首字母大写
	 * @throws Exception
	 */
	protected static void generatorService(String projectName, String packageModelPath, String modelName) throws Exception {
		String targetFilePath = Generator.getTargetFilePath(projectName, packageModelPath, Generator.PACKAGE_LAYER_SERVICE);
		targetFilePath += "/I" + modelName + "Service.java";

		VelocityContext context = new VelocityContext();
		context.put("modelName", modelName);
		context.put("ctime", new Date().toString());
		context.put("packageName", packageModelPath);
		context.put("p", packageModelPath);

		doGenerator(TEMPLATE_SERVICE, context, targetFilePath);
	}

	/**
	 * 创建Mapper模板文件
	 *
	 * @param projectName      目标所在工程名称
	 * @param packageModelPath 目标所在模块的package路径
	 * @param modelName        用于构建文件名称，对应模块名并首字母大写
	 * @throws Exception
	 */
	protected static void generatorMapper(String projectName, String packageModelPath, String modelName) throws Exception {
		String targetFilePath = Generator.getTargetFilePath(projectName, packageModelPath, Generator.PACKAGE_LAYER_MAPPER);
		targetFilePath += "/" + modelName + "Mapper.java";

		VelocityContext context = new VelocityContext();
		context.put("modelName", modelName);
		context.put("ctime", new Date().toString());
		context.put("packageName", packageModelPath);
		context.put("s", packageModelPath);

		doGenerator(TEMPLATE_MAPPER, context, targetFilePath);
	}

	/**
	 * 创建ServiceImpl模板文件
	 *
	 * @param projectName      目标所在工程名称
	 * @param packageModelPath 目标所在模块的package路径
	 * @param modelName        用于构建文件名称，对应模块名并首字母大写
	 * @throws Exception
	 */
	protected static void generatorServiceImpl(String projectName, String packageModelPath, String modelName) throws Exception {
		String targetFilePath = Generator.getTargetFilePath(projectName, packageModelPath, Generator.PACKAGE_LAYER_SERVICE_IMPL);
		targetFilePath += "/" + modelName + "ServiceImpl.java";

		VelocityContext context = new VelocityContext();
		context.put("modelName", modelName);
		context.put("ctime", new Date().toString());
		context.put("packageName", packageModelPath);
		context.put("i", packageModelPath);

		doGenerator(TEMPLATE_SERVICE_IMPL, context, targetFilePath);
	}

	/**
	 * do generator
	 *
	 * @param templateName
	 * @param context
	 * @param targetFilePath
	 */
	private static void doGenerator(String templateName, VelocityContext context, String targetFilePath) {
		File targetFile = new File(targetFilePath);
		// 若文件已经存在则跳过
		if (targetFile.exists()) {
			System.out.println("Skip Create, " + targetFilePath);
			return;
		}

		// 生成模板文件
		try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(targetFilePath))) {
			Template template = Velocity.getTemplate(templateName, fileEncoding);
			template.merge(context, writer);
			System.out.println("Create Success，" + targetFilePath);
		} catch (Exception e) {
			System.out.println("Create Failed，" + targetFilePath);
			e.printStackTrace();
		}
	}

}
