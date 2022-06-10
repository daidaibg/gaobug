package com.gaobug.auth.handler;

import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 微信登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class WechatLoginHandler extends AbstractLoginHandler {

    @Override
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
