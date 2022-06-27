package com.gaobug.base.feign.factory;

import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.FeignErrorDecoderFactory;

/**
 * FeignErrorDecoderFactory
 */
public class DefaultErrorDecoderFactory implements FeignErrorDecoderFactory {
    private final ErrorDecoder feignErrorDecoder;

    public DefaultErrorDecoderFactory(ErrorDecoder feignErrorDecoder) {
        this.feignErrorDecoder = feignErrorDecoder;
    }

    @Override
    public ErrorDecoder create(Class<?> type) {
        return feignErrorDecoder;
    }
}
