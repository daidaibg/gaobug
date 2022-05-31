package com.imantou.security.handler;

import com.imantou.security.autoconfigure.SecurityProperties;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import java.lang.reflect.Type;

/**
 * 接口加密处理类
 */
public interface IRestEncryptHandler {

    /**
     * 请求加密消息处理方法
     *
     * @param props         配置信息
     * @param inputMessage  请求加密消息
     * @param parameter     方法参数
     * @param targetType    {@link Type}
     * @param converterType {@link HttpMessageConverter}
     * @return
     */
    HttpInputMessage request(SecurityProperties props, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                             Class<? extends HttpMessageConverter<?>> converterType);

    /**
     * 响应加密消息处理方法
     *
     * @param props                 配置信息
     * @param body                  返回对象
     * @param returnType            方法参数
     * @param selectedContentType   {@link MediaType}
     * @param selectedConverterType {@link HttpMessageConverter}
     * @param request               {@link ServerHttpRequest}
     * @param response              {@link ServerHttpResponse}
     * @return
     */
    Object response(SecurityProperties props, Object body, MethodParameter returnType, MediaType selectedContentType,
                    Class<? extends HttpMessageConverter<?>> selectedConverterType,
                    ServerHttpRequest request, ServerHttpResponse response);

}
