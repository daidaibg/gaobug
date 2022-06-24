package com.gaobug.base.feign.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * ErrorDecoder
 */
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("methodKey:{},response:{}", methodKey, response);
        log.info("body:{}", response.body());
        return FeignException.errorStatus(methodKey, response);
    }
}
