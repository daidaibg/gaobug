package com.imantou.auth.handler;

import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.auth.dto.PlatformLoginForm;
import com.imantou.auth.enums.LoginType;
import com.imantou.base.utils.EncryptUtils;
import com.imantou.response.enums.ResultEnum;
import com.imantou.response.exception.BusinessException;
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
        PlatformUserVO user = platformUserClient.getUserByName(form.getAccount());
        if (null == user) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        if (!Objects.equals(user.getPassword(), EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        return user;
    }

}
