package com.imantou.api.user;

import com.imantou.api.vo.PlatformUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 后台管理用户api
 *
 * @author gaobug
 */
@FeignClient(value = "blog-platform", contextId = "platform-user")
public interface PlatformUserClient {
    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @GetMapping(value = "/platform/user/getUserByName/{userName}")
    PlatformUserVO getUserByName(@PathVariable String userName);

}
