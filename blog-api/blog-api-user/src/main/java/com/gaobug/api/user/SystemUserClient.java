package com.gaobug.api.user;

import com.gaobug.api.vo.SystemUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-system", contextId = "system-user")
public interface SystemUserClient {
    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @GetMapping(value = "/getUserByName/{userName}")
    SystemUserVO getUserByName(@PathVariable String userName);

    /**
     * 获取用户权限
     *
     * @param userId 用户标识
     * @return 用户权限集合
     */
    @GetMapping(value = "/getUserPermissions/{userId}")
    Set<String> getUserPermissions(@PathVariable Integer userId);
}
