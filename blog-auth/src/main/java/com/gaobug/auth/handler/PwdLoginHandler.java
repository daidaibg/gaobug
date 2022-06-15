package com.gaobug.auth.handler;

import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
import com.gaobug.auth.enums.LoginType;
import com.gaobug.response.enums.ResultEnum;
import com.gaobug.response.exception.BusinessException;
import com.gaobug.utils.EncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 密码登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class PwdLoginHandler extends AbstractLoginHandler {
    @Resource
    private PlatformUserClient platformUserClient;

    @Override
    public boolean supports(Integer loginType) {
        return Objects.equals(loginType, LoginType.password.getType());
    }

    @Override
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        if (!StringUtils.hasText(form.getAccount()) || !StringUtils.hasText(form.getPassword())) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        PlatformUserVO user = platformUserClient.getUserByAccountOrEmail(form.getAccount());
        if (null == user) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        if (!Objects.equals(user.getPassword(), EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        return user;
    }

}
