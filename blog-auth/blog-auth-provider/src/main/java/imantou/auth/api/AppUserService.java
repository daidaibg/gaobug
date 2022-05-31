package imantou.auth.api;

import imantou.auth.service.Impl.UserServiceFallbackImpl;
import imantou.common.domain.User;
import imantou.common.remote.AppUserClientApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-app", fallback = UserServiceFallbackImpl.class)
public interface AppUserService extends AppUserClientApi {
    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    @GetMapping(value = APP_USER_SERVER + "/getUserByName/{userName}")
    User getUserByName(@PathVariable String userName);

}
