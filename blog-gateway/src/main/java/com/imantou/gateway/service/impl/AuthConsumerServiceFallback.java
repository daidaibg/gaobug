package com.imantou.gateway.service.impl;

import com.imantou.common.domain.JwtTokenVO;
import com.imantou.common.domain.LoginForm;
import com.imantou.common.exception.RemoteCallException;
import com.imantou.common.response.ResponseWrapped;
import com.imantou.gateway.service.AuthConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthConsumerServiceFallback implements AuthConsumerService {

    @Override
    public ResponseWrapped<JwtTokenVO> authorization() {
        throw new RemoteCallException(HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseWrapped<Object> getCaptcha(String randomId) {
        throw new RemoteCallException("获取验证码失败");
    }

    @Override
    public ResponseWrapped<Object> systemLogin(LoginForm form, String randomId) {
        throw new RemoteCallException("登录失败，请稍后再试");
    }
}
