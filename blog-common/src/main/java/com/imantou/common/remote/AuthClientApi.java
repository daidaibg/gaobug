package com.imantou.common.remote;

import com.imantou.common.domain.JwtTokenVO;
import com.imantou.common.domain.LoginForm;
import com.imantou.common.response.ResponseWrapped;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 鉴权模块api
 */
public interface AuthClientApi {
    String AUTH_SERVER = "/auth";

    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    ResponseWrapped<JwtTokenVO> authorization();

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    ResponseWrapped<Object> getCaptcha(String randomId);

    /**
     * 用户登录
     *
     * @param form     登录表单
     * @param randomId 缓存验证的随机id
     * @return 登录令牌
     */
    ResponseWrapped<Object> systemLogin(LoginForm form, String randomId);

}
