package com.x.framework.provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
// 自动配置feign扫描包 使用方零配置 微服务本身不加载自己的API中的feign
@ConditionalOnExpression("#{!environment['spring.application.name'].endsWith('" + ProviderApiAutoConfig.SERVICE_NAME + "')}")
@EnableFeignClients(basePackages = "com.yryz.framework.provider")
public class ProviderApiAutoConfig {
    public static final String SERVICE_NAME = "framework-provider";
    // FeignClient 用placeholder可以方便的进行内部调用 配置key为yryz.service.服务名
    // 配置yryz.service.framework-provider=heying-framework-provider来调用heying-framework-provider服务
    public static final String PLACE_HOLD_SERVICE_NAME = "${yryz.service." + SERVICE_NAME + ":" + SERVICE_NAME + "}";

    public static final String CURRENT_VERSION = "v2";
    public static final String COMPATIBLE_VERSION = "v2,v1";

}
