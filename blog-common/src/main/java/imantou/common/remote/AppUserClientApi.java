package imantou.common.remote;


import imantou.common.domain.User;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 前台用户api
 *
 * @author gaobug
 */
public interface AppUserClientApi {
    String APP_USER_SERVER = "/app/user";

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    User getUserByName(@PathVariable String userName);
}
