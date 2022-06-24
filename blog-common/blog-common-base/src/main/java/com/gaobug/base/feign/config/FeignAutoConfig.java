package com.gaobug.base.feign.config;

import com.gaobug.base.feign.decoder.FeignErrorDecoder;
import com.gaobug.base.feign.factory.DefaultErrorDecoderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FeignAutoConfig {

    @Bean
    @Primary
    public FeignErrorDecoder feignErrorDecoder(){
        return new FeignErrorDecoder();
    }

    @Bean
    @Primary
    public DefaultErrorDecoderFactory defaultErrorDecoderFactory(){
        return new DefaultErrorDecoderFactory(feignErrorDecoder());
    }
}
