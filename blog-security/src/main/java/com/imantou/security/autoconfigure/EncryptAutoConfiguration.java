package com.imantou.security.autoconfigure;

import com.imantou.security.advice.DecryptRequestBodyAdvice;
import com.imantou.security.advice.EncryptResponseBodyAdvice;
import com.imantou.security.handler.DefaultRestEncryptHandler;
import com.imantou.security.handler.IRestEncryptHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * 接口加密处理启动配置
 */
@Lazy
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = SecurityProperties.PREFIX, name = "privateKey")
public class EncryptAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public DefaultRestEncryptHandler restEncryptHandler() {
        return new DefaultRestEncryptHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public DecryptRequestBodyAdvice decryptRequestBodyAdvice(SecurityProperties securityProperties,
                                                             IRestEncryptHandler restEncryptHandler) {
        return new DecryptRequestBodyAdvice(securityProperties, restEncryptHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    public EncryptResponseBodyAdvice encryptResponseBodyAdvice(SecurityProperties securityProperties,
                                                               IRestEncryptHandler restEncryptHandler) {
        securityProperties.checkValid();
        return new EncryptResponseBodyAdvice(securityProperties, restEncryptHandler);
    }
}
