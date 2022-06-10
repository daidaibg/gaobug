package com.gaobug.auth.handler;

import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
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
