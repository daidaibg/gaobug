package com.imantou.auth.service;

import com.imantou.auth.service.impl.SysUserServiceFallback;
import com.imantou.common.domain.SysUser;
import com.imantou.common.remote.SysUserClientApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Component
@FeignClient(value = "blog-system", fallback = SysUserServiceFallback.class)
public interface SysUserService extends SysUserClientApi {

    @GetMapping(value = SYSTEM_USER_SERVER + "/getUserByName/{userName}")
    SysUser getUserByName(@PathVariable String userName);

    @GetMapping(value = SYSTEM_USER_SERVER + "/getUserPermissions/{userId}")
    Set<String> getUserPermissions(@PathVariable Integer userId);
}
