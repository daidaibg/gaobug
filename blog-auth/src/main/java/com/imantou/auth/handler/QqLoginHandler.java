package com.imantou.auth.handler;

import com.imantou.api.user.PlatformUserClient;
import com.imantou.auth.dto.PlatformLoginForm;
import com.imantou.auth.vo.PlatformUserContextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * qq登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class QqLoginHandler extends AbstractLoginHandler {
    @Resource
    private PlatformUserClient platformUserClient;

    @Override
    public PlatformUserContextVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
