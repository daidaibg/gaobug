package com.imantou.auth.handler;

import com.imantou.api.vo.PlatformUserVO;
import com.imantou.auth.dto.PlatformLoginForm;
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
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
