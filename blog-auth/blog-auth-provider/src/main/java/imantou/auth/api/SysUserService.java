package imantou.auth.api;

import imantou.auth.service.Impl.UserServiceFallbackImpl;
import imantou.common.domain.SysUser;
import imantou.common.remote.SysUserClientApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-system", fallback = UserServiceFallbackImpl.class)
public interface SysUserService extends SysUserClientApi {
    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    @GetMapping(value = SYSTEM_USER_SERVER + "/getUserByName/{userName}")
    SysUser getUserByName(@PathVariable String userName);

    /**
     * 获取用户权限
     *
     * @param userId 用户标识
     * @return 用户权限集合
     */
    @Override
    @GetMapping(value = SYSTEM_USER_SERVER + "/getUserPermissions/{userId}")
    Set<String> getUserPermissions(@PathVariable Integer userId);
}
