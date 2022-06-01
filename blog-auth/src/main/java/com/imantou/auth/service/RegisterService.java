package com.imantou.auth.service;

import com.imantou.response.ResponseWrapped;
import com.imantou.auth.dto.PlatformRegisterDTO;

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
    ResponseWrapped<Object> registerUser(PlatformRegisterDTO form);
}
