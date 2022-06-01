package com.imantou.api.auth;

import com.imantou.api.vo.JwtTokenVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-auth", contextId = "auth-authorization")
public interface AuthClientApi {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    @GetMapping("/auth/authorization")
    JwtTokenVO authorization();
}
