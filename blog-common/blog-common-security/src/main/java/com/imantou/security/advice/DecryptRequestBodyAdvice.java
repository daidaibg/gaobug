package com.imantou.security.advice;

import com.imantou.security.annotation.Encrypted;
import com.imantou.security.handler.IRestEncryptHandler;
import com.imantou.security.autoconfigure.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * 接口请求解密处理切点
 */
@Slf4j
@RestControllerAdvice
public class DecryptRequestBodyAdvice extends RequestBodyAdviceAdapter {
    private final SecurityProperties securityProperties;
    private final IRestEncryptHandler restEncryptHandler;

    public DecryptRequestBodyAdvice(SecurityProperties securityProperties, IRestEncryptHandler restEncryptHandler) {
        this.securityProperties = securityProperties;
        this.restEncryptHandler = restEncryptHandler;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return Encrypted.class.isAssignableFrom((Class<?>) targetType);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) {
        return restEncryptHandler.request(securityProperties, inputMessage, parameter, targetType, converterType);
    }
}
