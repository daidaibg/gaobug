package com.imantou.auth.handler;

import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.auth.dto.PlatformLoginForm;
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
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
