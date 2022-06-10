package com.gaobug.auth.service;

import java.util.Map;

/**
 * 登录服务接口
 *
 * @author gaobug
 */
public interface CaptchaService {

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    Map<String, Object> getCaptcha();

    /**
     * 校验验证码
     *
     * @param randomId 随机id
     * @param captcha  校验内容
     * @return 校验结果
     */
    void verifyCaptcha(String randomId, Integer captcha);

}
