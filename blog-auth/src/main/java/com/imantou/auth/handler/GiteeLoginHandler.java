package com.imantou.auth.handler;

import com.imantou.auth.dto.PlatformLoginForm;
import com.imantou.auth.vo.PlatformUserContextVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * gitee登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class GiteeLoginHandler extends AbstractLoginHandler {

    @Override
    public PlatformUserContextVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
