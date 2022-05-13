package com.imantou.gateway.service;

import com.imantou.common.domain.JwtTokenVO;
import com.imantou.common.remote.AuthClientApi;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.gateway.service.impl.AuthConsumerServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(value = "blog-auth-consumer", fallback = AuthConsumerServiceFallback.class)
public interface AuthConsumerService extends AuthClientApi {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    @GetMapping(AUTH_SERVER + "/auth/authorization")
    ResponseWrapped<JwtTokenVO> authorization();
}
