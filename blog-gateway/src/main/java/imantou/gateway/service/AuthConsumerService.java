package imantou.gateway.service;

import imantou.common.vo.JwtTokenVO;
import imantou.common.remote.AuthClientApi;
import imantou.common.response.ResponseWrapped;
import imantou.gateway.service.impl.AuthConsumerServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 鉴权模块
 *
 * @author gaobug
 */
@Component
@FeignClient(value = "blog-auth-consumer", fallback = AuthConsumerServiceFallback.class)
public interface AuthConsumerService extends AuthClientApi {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    @Override
    @GetMapping(AUTH_SERVER + "/auth/authorization")
    ResponseWrapped<JwtTokenVO> authorization();
}
