package com.imantou.auth.service;

import com.imantou.auth.dto.LoginForm;
import com.imantou.common.vo.AuthTokenVO;


import java.util.Map;

public interface LoginService {

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    Map<String, Object> getCaptcha(String randomId);

    /**
     * 用户登录
     *
     * @param form 登录表单
     * @return 登录令牌
     */
    AuthTokenVO systemLogin(String randomId, LoginForm form);

}
