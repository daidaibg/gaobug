package com.imantou.auth.handler;

import com.imantou.api.vo.PlatformUserVO;
import com.imantou.auth.dto.PlatformLoginForm;

/**
 * 登录服务handler接口
 *
 * @author gaobug
 */
public interface LoginHandler {

    /**
     * 是否支持当前登录方式
     *
     * @param loginType 登录类型
     * @return 是否支持
     */
    default boolean supports(Integer loginType) {
        return false;
    }

    /**
     * 用户登录
     *
     * @param form 登录表单
     * @return 登录令牌
     */
    PlatformUserVO getLoginUser(PlatformLoginForm form);

}
