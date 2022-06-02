package com.imantou.auth.service.Impl;

import com.imantou.api.dto.PlatformRegisterDTO;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.auth.service.RegisterService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 注册服务
 *
 * @author gaobug
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private PlatformUserClient platformUserClient;

    @Override
    public void registerUser(PlatformRegisterDTO form) {
         platformUserClient.registerUser(form);
    }
}
