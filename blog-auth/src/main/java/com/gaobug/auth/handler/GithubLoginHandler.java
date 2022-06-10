package com.gaobug.auth.handler;

import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * github登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class GithubLoginHandler extends AbstractLoginHandler {

    @Override
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        return null;
    }
}
