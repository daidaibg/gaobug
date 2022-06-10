package com.gaobug.auth.service.Impl;

import com.gaobug.api.dto.PlatformRegisterDTO;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.auth.service.RegisterService;
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
