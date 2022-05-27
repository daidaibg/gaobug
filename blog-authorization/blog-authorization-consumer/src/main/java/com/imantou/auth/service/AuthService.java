package com.imantou.auth.service;

import com.imantou.auth.service.impl.AuthServiceFallback;
import com.imantou.common.vo.JwtTokenVO;
import com.imantou.common.dto.LoginForm;
import com.imantou.common.remote.AuthClientApi;
import com.imantou.common.response.ResponseWrapped;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "blog-auth-provider", fallback = AuthServiceFallback.class)
public interface AuthService extends AuthClientApi {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    @GetMapping(AUTH_SERVER + "/auth/authorization")
    ResponseWrapped<JwtTokenVO> authorization();

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    @GetMapping(AUTH_SERVER + "/login/captcha/{randomId}")
    ResponseWrapped<Object> getCaptcha(@PathVariable String randomId);

    /**
     * 用户登录
     *
     * @param form     登录表单
     * @param randomId 缓存验证的随机id
     * @return 登录令牌
     */
    @PostMapping(value = AUTH_SERVER + "/login/system/login/{randomId}")
    ResponseWrapped<Object> systemLogin(LoginForm form, @PathVariable String randomId);
}
