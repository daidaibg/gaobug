package com.gaobug.auth.handler;

import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
import com.gaobug.auth.enums.LoginType;
import com.gaobug.auth.service.RegisterService;
import com.gaobug.response.enums.ResultEnum;
import com.gaobug.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 手机登录
 *
 * @author gaobug
 */
@Slf4j
@Service
public class MobileLoginHandler extends AbstractLoginHandler {
    @Resource
    private PlatformUserClient platformUserClient;
    @Resource
    private RegisterService registerService;

    @Override
    public boolean supports(Integer loginType) {
        return Objects.equals(loginType, LoginType.mobile.getType());
    }

    @Override
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        if (!StringUtils.hasText(form.getMobile()) || !StringUtils.hasText(form.getCode())) {
            throw new BusinessException(ResultEnum.ERROR_CAPTCHA_OVERDUE);
        }
//        String code = RedisUtil.get(CaptchaCode.MOBILE_CODE_LOGIN + form.getMobile());
        if (!Objects.equals(form.getCode(), "1234")) {
            throw new BusinessException(ResultEnum.ERROR_CAPTCHA_FAIL);
        }
        PlatformUserVO userByMobile = platformUserClient.getUserByMobile(form.getMobile());
        if (null == userByMobile) {
            UserRegisterDTO platformRegister = new UserRegisterDTO();
            platformRegister.setMobile(form.getMobile());
            return registerService.registerUserByMobile(platformRegister);
        }
        return platformUserClient.getUserByMobile(form.getMobile());
    }
}
