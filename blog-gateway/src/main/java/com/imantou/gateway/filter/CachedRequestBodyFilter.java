package com.gaobug.gateway.filter;


import com.gaobug.gateway.constant.GatewayCachedContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.springframework.http.MediaType.*;


/**
 * 缓存post请求body
 * <p>
 * 只缓存以下几种格式
 * json请求 {@link APPLICATION_JSON}
 * 表单请求 {@link APPLICATION_FORM_URLENCODED_VALUE}
 * 加密请求 {@link TEXT_PLAIN_VALUE}
 *
 * @author hzq
 */
@Slf4j
@Component
public class CachedRequestBodyFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        if (!Objects.equals(method, HttpMethod.POST)) {
            return chain.filter(exchange);
        }
        String contentType = serverHttpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (!StringUtils.hasText(contentType)) {
            log.error("Cache requestBody error: current request contentType is null ");
            return chain.filter(exchange);
        }
        // 缓存特定contentType格式数据
        if (contentType.startsWith(APPLICATION_JSON_VALUE)) {
            return copyRequestBody(exchange, chain);
        } else if (contentType.startsWith(APPLICATION_FORM_URLENCODED_VALUE)) {
            return copyRequestBody(exchange, chain);
        } else if (contentType.startsWith(TEXT_PLAIN_VALUE)) {
            return copyRequestBody(exchange, chain);
        }
        return chain.filter(exchange);
    }

    /**
     * 将requestBody中的内容copy一份，记录到exchange的一个自定义属性中
     */
    private Mono<Void> copyRequestBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 如果已经缓存过，略过
        Object body = exchange.getAttribute(GatewayCachedContext.CACHE_REQUEST_BODY);
        if (null != body) {
            return chain.filter(exchange);
        }
        // 如果没有缓存过，获取字节数组存入exchange的自定义属性中
        return DataBufferUtils.join(exchange.getRequest().getBody())
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return bytes;
                }).defaultIfEmpty(new byte[0])
                .doOnNext(bytes -> exchange.getAttributes().put(GatewayCachedContext.CACHE_REQUEST_BODY, bytes))
                .then(chain.filter(exchange));
    }

    /**
     * 执行顺序要在加密过滤器、验签过滤器之前
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 1;
    }
}