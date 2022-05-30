package com.imantou.auth.service.impl;

import com.imantou.auth.service.SysUserService;
import com.imantou.common.domain.SysUser;
import com.imantou.common.exception.BusinessException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class SysUserServiceFallback implements SysUserService {

    @Override
    public SysUser getUserByName(String userName) {
        throw new BusinessException("获取用户信息失败！");
    }

    @Override
    public Set<String> getUserPermissions(Integer userId) {
        throw new BusinessException("获取用户权限失败！");
    }
}
