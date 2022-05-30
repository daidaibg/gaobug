
package com.imantou.security.autoconfigure;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

/**
 * 安全模块启动配置
 */
@Lazy
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({SecurityProperties.class})
@Import(value = {EncryptAutoConfiguration.class})
public class SecurityAutoConfiguration {

}
