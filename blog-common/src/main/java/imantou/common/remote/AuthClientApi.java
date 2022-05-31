package imantou.common.remote;

import imantou.common.vo.JwtTokenVO;
import imantou.common.dto.LoginForm;
import imantou.common.response.ResponseWrapped;

/**
 * 鉴权模块api
 *
 * @author gaobug
 */
public interface AuthClientApi {
    String AUTH_SERVER = "/auth";

    /**
     * 用户鉴权
     *
     * @return 请求结果
     */
    ResponseWrapped<JwtTokenVO> authorization();

    /**
     * 获取验证码
     *
     * @param randomId 随机id
     * @return 验证码
     */
    ResponseWrapped<Object> getCaptcha(String randomId);

    /**
     * app用户登录
     *
     * @param form     登录表单
     * @param randomId 缓存验证的随机id
     * @return
     */
    ResponseWrapped<Object> appUserLogin(LoginForm form, String randomId);

    /**
     * 获取登录用户信息
     *
     * @return 登录用户信息
     */
    ResponseWrapped<Object> getAppLoginUserInfo();

    /**
     * 用户登录
     *
     * @param form     登录表单
     * @param randomId 缓存验证的随机id
     * @return 登录令牌
     */
    ResponseWrapped<Object> systemLogin(LoginForm form, String randomId);


}
