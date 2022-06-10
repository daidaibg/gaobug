package com.gaobug.store.autoconfigure;


import com.gaobug.store.FileStorage;
import com.gaobug.store.platform.LocalStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 装配存储
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    private final OssProperties properties;

    public OssAutoConfiguration(OssProperties ossProperties) {
        this.properties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public FileStorage fileStorage() {
        return new LocalStorage();
    }

}
