package imantou.gateway.service.impl;

import imantou.common.vo.JwtTokenVO;
import imantou.common.dto.LoginForm;
import imantou.common.exception.RemoteCallException;
import imantou.common.response.ResponseWrapped;
import imantou.gateway.service.AuthConsumerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author imantou
 */
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
    public ResponseWrapped<Object> appUserLogin(LoginForm form, String randomId) {
        return null;
    }

    @Override
    public ResponseWrapped<Object> getAppLoginUserInfo() {
        return null;
    }

    @Override
    public ResponseWrapped<Object> systemLogin(LoginForm form, String randomId) {
        throw new RemoteCallException("登录失败，请稍后再试");
    }
}
