package com.imantou.common.remote;

import com.imantou.common.domain.SysUser;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

public interface SysUserClientService {

    String SYSTEM_USER_SERVER = "/system/user/";

    /**
     * 根据用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    SysUser getUserByName(@PathVariable String userName);

    /**
     * 获取用户权限列表
     *
     * @param userId 用户标识
     * @return 用户权限列表
     */
    Set<String> getUserPermissions(@PathVariable Integer userId);
}
