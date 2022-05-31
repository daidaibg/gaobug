package imantou.user.api;


import imantou.common.response.ResponseWrapped;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-app", contextId = "platform-user")
public interface PlatformUserService {
    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @GetMapping(value = "/getUserByName/{userName}")
    ResponseWrapped<Object> getUserByName(@PathVariable String userName);

}
