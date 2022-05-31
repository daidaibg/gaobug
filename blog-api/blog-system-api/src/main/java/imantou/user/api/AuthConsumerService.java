package imantou.user.api;

import imantou.user.dto.LoginForm;
import imantou.common.response.ResponseWrapped;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 权限模块生产者api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-auth-provider", contextId = "auth")
public interface AuthConsumerService {
    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    @GetMapping("/auth/authorization")
    ResponseWrapped<Object> authorization();

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    @GetMapping("/login/captcha/{randomId}")
    ResponseWrapped<Object> getCaptcha(@PathVariable String randomId);

    /**
     * 用户登录
     *
     * @param form     登录表单
     * @param randomId 缓存验证的随机id
     * @return 登录令牌
     */
    @PostMapping("/login/system/login/{randomId}")
    ResponseWrapped<Object> systemLogin(LoginForm form, @PathVariable String randomId);

}
