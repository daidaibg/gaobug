package imantou.user.service;

import imantou.user.dto.LoginForm;
import imantou.common.vo.AuthTokenVO;


import java.util.Map;

/**
 * 登录服务接口
 *
 * @author gaobug
 */
public interface LoginService {

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    Map<String, Object> getCaptcha(String randomId);

    /**
     * 校验验证码
     *
     * @param randomId 随机id
     * @param captcha  校验内容
     * @return 校验结果
     */
    Boolean verifyCaptcha(String randomId, Integer captcha);

    /**
     * 用户登录
     *
     * @param randomId 验证码key
     * @param form     登录表单
     * @return 登录令牌
     */
    AuthTokenVO appUserLogin(String randomId, LoginForm form);

    /**
     * 用户登录
     *
     * @param randomId 验证码key
     * @param form     登录表单
     * @return 登录令牌
     */
    AuthTokenVO systemLogin(String randomId, LoginForm form);

}
