package com.imantou.security.autoconfigure;

import com.imantou.security.adapter.DefaultAuthConfigAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 权限适配器启动配置类
 *
 * @author gaobug
 */
@Lazy
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = {DefaultAuthConfigAdapter.class})
public class AuthAdapterConfiguration {

}
