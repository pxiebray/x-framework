package com.x.framework.generator;

import com.x.framework.generator.config.TargetConfig;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * 基于Velocity的代码生成，
 * 负责Controller/IService/ServiceImpl/Mapper四个文件的生成，重复生成时不覆盖已有文件，避免业务代码丢失。
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

	protected static void generatorController(String modelName, TargetConfig targetConfig) throws Exception {
		String targetFilePath = TargetPathUtil.getTargetFilePath(targetConfig);
		targetFilePath += "/" + modelName + "Controller.java";
		VelocityContext context = buildVelocityContext(modelName, targetConfig, null);
		doGenerator(TEMPLATE_CONTROLLER, context, targetFilePath);
	}

	protected static void generatorService(String modelName, TargetConfig targetConfig) throws Exception {
		String targetFilePath = TargetPathUtil.getTargetFilePath(targetConfig);
		targetFilePath += "/I" + modelName + "Service.java";
		VelocityContext context = buildVelocityContext(modelName, targetConfig, null);
		doGenerator(TEMPLATE_SERVICE, context, targetFilePath);
	}

	protected static void generatorMapper(String modelName, TargetConfig targetConfig) throws Exception {
		String targetFilePath = TargetPathUtil.getTargetFilePath(targetConfig);
		targetFilePath += "/" + modelName + "Mapper.java";
		VelocityContext context = buildVelocityContext(modelName, targetConfig, null);
		doGenerator(TEMPLATE_MAPPER, context, targetFilePath);
	}

	protected static void generatorServiceImpl(String modelName, TargetConfig targetConfig) throws Exception {
		String targetFilePath = TargetPathUtil.getTargetFilePath(targetConfig);
		targetFilePath += "/" + modelName + "ServiceImpl.java";
		VelocityContext context = buildVelocityContext(modelName, targetConfig, null);
		doGenerator(TEMPLATE_SERVICE_IMPL, context, targetFilePath);
	}

	/**
	 * 构建Velocity Context
	 * @param modelName
	 * @param targetConfig
	 * @param props 自定义数据
	 */
	private static VelocityContext buildVelocityContext(String modelName, TargetConfig targetConfig, Map<String, String> props) {
		VelocityContext context = new VelocityContext();
		context.put("modelName", modelName);
		context.put("ctime", new Date().toString());
		context.put("packageName", targetConfig.getPackagePath());

		if (props != null) {
			props.forEach((key, value) -> context.put(key, value));
		}
		return context;
	}

	/**
	 * do generator
	 *
	 * @param templateName
	 * @param context
	 * @param targetFilePath
	 */
	private static void doGenerator(String templateName, VelocityContext context, String targetFilePath) throws Exception {
		File targetFile = new File(targetFilePath);
		if (targetFile.exists()) {
			// skip if target file exist!
			System.out.println("Skip Create, " + targetFilePath);
			return;
		} else {
			// mkdir for parent dirs
			File parent = targetFile.getParentFile();
			if (parent.exists() && !parent.isDirectory()) {
				throw new Exception("目标路径下无法创建文件，" + parent.getPath());
			} else {
				parent.mkdirs();
			}
		}

		// generator file
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
