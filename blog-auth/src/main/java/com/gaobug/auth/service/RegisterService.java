package com.gaobug.auth.service;

import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.api.vo.PlatformUserVO;

/**
 * 登录服务接口
 *
 * @author gaobug
 */
public interface RegisterService {

    /**
     * 邮箱注册
     *
     * @param platformRegister 注册表单
     */
    PlatformUserVO registerUserByEmail(UserRegisterDTO platformRegister);

    /**
     * 手机注册
     *
     * @param platformRegister 注册表单
     */
    PlatformUserVO registerUserByMobile(UserRegisterDTO platformRegister);
}
