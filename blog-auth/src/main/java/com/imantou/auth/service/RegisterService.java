package com.imantou.auth.service;

import com.imantou.api.dto.PlatformRegisterDTO;
import com.imantou.response.ResponseWrapped;

/**
 * 登录服务接口
 *
 * @author gaobug
 */
public interface RegisterService {

    /**
     * 平台用户注册
     *
     * @param form 注册表单
     */
    void registerUser(PlatformRegisterDTO form);
}
