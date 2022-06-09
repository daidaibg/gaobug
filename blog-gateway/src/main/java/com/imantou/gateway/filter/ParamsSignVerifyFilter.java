package com.imantou.gateway.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.imantou.gateway.constant.GatewayCachedContext;
import com.imantou.gateway.exception.ParamsSignException;
import com.imantou.gateway.handler.IParamsSignHandler;
import com.imantou.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 参数验签过滤器
 *
 * @author hzq
 */
@Slf4j
@Component
public class ParamsSignVerifyFilter implements GlobalFilter, Ordered {
    @Resource
    private IParamsSignHandler paramsSignHandler;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpMethod method = serverHttpRequest.getMethod();
        HttpHeaders headers = serverHttpRequest.getHeaders();
        if (Objects.equals(method, HttpMethod.POST)) {
            // 尝试从exchange的自定义属性中取出缓存到的body
            byte[] body = exchange.getAttribute(GatewayCachedContext.CACHE_REQUEST_BODY);
            // CachedRequestBodyFilter已经排除了不需要校验的格式，为空的body也不做校验
            if (ObjectUtils.isEmpty(body)) {
                return chain.filter(exchange);
            }
            boolean result = paramsSignHandler.checkSign(headers,
                    () -> JacksonUtils.parse(new String(body), new TypeReference<TreeMap<String, String>>() {
                    }));
            if (!result) {
                throw new ParamsSignException("请勿非法篡改参数！");
            }
            // exchange.getAttributes().remove(FilterConstant.CACHED_REQUEST_BODY_OBJECT_KEY);
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    if (body.length > 0) {
                        return Flux.just(dataBufferFactory.wrap(body));
                    }
                    return Flux.empty();
                }
            };
            return chain.filter(exchange.mutate().request(decorator).build());
        }

        if (Objects.equals(method, HttpMethod.GET)) {
            Map<String, String> queryParams = serverHttpRequest.getQueryParams().toSingleValueMap();
            // 没有查询参数也不做校验
            if (ObjectUtils.isEmpty(queryParams)) {
                return chain.filter(exchange);
            }
            boolean result = paramsSignHandler.checkSign(headers, () -> new TreeMap<>(queryParams));
            if (!result) {
                throw new ParamsSignException("请勿非法篡改参数！");
            }
        }
        // 获取原参数
        return chain.filter(exchange);
    }

    /**
     * 执行顺序要在加密过滤器之后
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE + 2;
    }
}