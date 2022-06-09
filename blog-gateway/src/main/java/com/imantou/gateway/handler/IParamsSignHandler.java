package com.imantou.gateway.handler;

import org.springframework.http.HttpHeaders;

import java.util.SortedMap;
import java.util.function.Supplier;

/**
 * 请求参数验签处理器接口类
 *
 * @author gaobug
 */
public interface IParamsSignHandler {

    /**
     * 验签
     *
     * @param httpHeaders       请求头部 {@link HttpHeaders}
     * @param sortedMapSupplier 请求参数 {@link SortedMap}
     * @return 验签结果
     */
    boolean checkSign(HttpHeaders httpHeaders, Supplier<SortedMap<String, String>> sortedMapSupplier);
}
