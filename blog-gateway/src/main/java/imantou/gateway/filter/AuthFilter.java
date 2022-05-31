package imantou.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.imantou.advice.response.ResponseWrapped;
import com.imantou.api.auth.AuthClientApi;
import com.imantou.api.vo.AuthTokenVO;
import com.imantou.api.vo.JwtTokenVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Lazy;
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
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 身份鉴权过滤器
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Lazy
    @Resource
    private AuthClientApi authClientApi;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        URI uri = request.getURI();
//        if (uri.getPath().startsWith("/app")){
//            return chain.filter(exchange);
//        }
//        if (uri.getPath().startsWith("/auth/login")){
//            return chain.filter(exchange);
//        }
//        HttpHeaders headers = request.getHeaders();
//        String authToken = headers.getFirst(AuthTokenVO.AUTH_TOKEN);
//        if (!StringUtils.hasText(authToken)) {
//            return this.doLoginVoidMono(exchange);
//        }
//        ResponseWrapped<JwtTokenVO> responseWrapped = authClientApi.authorization();
//        // 权限验证逻辑，忽略，这里直接返回验证不通过
//        if (!responseWrapped.isSuccess() && !Objects.isNull(responseWrapped.getData())) {
//            return this.doLoginVoidMono(exchange);
//        }
//        // 认证通过，设jwt身份信息令牌
//        request.getHeaders().add(JwtTokenVO.AUTHORITY_TOKEN, String.valueOf(responseWrapped.getData()));
        return chain.filter(exchange);
    }

    private Mono<Void> doLoginVoidMono(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        ResponseWrapped<Object> responseWrapped = ResponseWrapped.error(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = response.bufferFactory().wrap(JSON.toJSONString(responseWrapped).getBytes(StandardCharsets.UTF_8));
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return 10;
    }
}