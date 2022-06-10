package com.imantou.gateway.filter;

import com.alibaba.nacos.common.utils.JacksonUtils;
import com.imantou.cache.constant.AuthToken;
import com.imantou.cache.context.PlatformUserContext;
import com.imantou.cache.util.RedisUtil;
import com.imantou.gateway.adapter.PathPatternsConfigAdapter;
import com.imantou.response.ResponseWrapped;
import com.imantou.response.constant.RequestHeader;
import com.imantou.response.exception.BusinessException;
import com.imantou.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 鉴权过滤器
 *
 * @author gaobug
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Resource
    private List<PathPatternsConfigAdapter> pathPatternsAdapters;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String requestPath = request.getPath().value();
        String appid = headers.getFirst(RequestHeader.APP_ID_HEADER);
        // 校验路径
        if (isRelease(appid, requestPath)) {
            return chain.filter(exchange);
        }
        // 校验是否携带登录令牌
        String authToken = headers.getFirst(RequestHeader.AUTH_TOKEN_HEADER);
        if (!StringUtils.hasText(authToken)) {
            return this.doLoginVoidMono(exchange);
        }
        // 校验登录令牌有效性
        String authTokenBucket = AuthToken.AUTH_TOKEN_BUCKET + authToken;
        PlatformUserContext user = RedisUtil.get(authTokenBucket);
        if (null == user) {
            return this.doLoginVoidMono(exchange);
        }
        // 颁发jwt身份令牌
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("id", user.getId());
        payloadClaims.put("username", user.getUsername());
        String jwtToken = JwtUtils.createToken(authToken, payloadClaims);
        if (!StringUtils.hasText(jwtToken)) {
            throw new BusinessException("身份信息令牌颁发失败");
        }
        // 认证通过，请求头设置jwt身份信息令牌
        ServerWebExchange serverWebExchange = exchange
                .mutate()
                .request(exchange
                        .getRequest()
                        .mutate()
                        .header(RequestHeader.AUTH_JWT_HEADER, jwtToken)
                        .build())
                .build();
        return chain.filter(serverWebExchange);
    }

    /**
     * 校验路径是否在放行名单
     * <p>
     * 是否需要登录、访问权限)都在此方法中校验
     *
     * @param appid       应用标识
     * @param requestPath 请求路径
     * @return 是否放行
     */
    private boolean isRelease(String appid, String requestPath) {
        AtomicBoolean isRelease = new AtomicBoolean(false);
        pathPatternsAdapters.stream()
                .filter(pathPatternsConfigAdapter -> pathPatternsConfigAdapter.supports(appid))
                .findFirst()
                .ifPresent(pathPatternsConfigAdapter -> isRelease.set(pathPatternsConfigAdapter.isRelease(requestPath)));
        return isRelease.get();
    }

    /**
     * 返回未登录结果
     *
     * @param exchange 响应交互
     * @return 响应视图
     */
    private Mono<Void> doLoginVoidMono(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseWrapped<Object> responseWrapped = ResponseWrapped.error(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = response.bufferFactory().wrap(JacksonUtils.toJsonBytes(responseWrapped));
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}