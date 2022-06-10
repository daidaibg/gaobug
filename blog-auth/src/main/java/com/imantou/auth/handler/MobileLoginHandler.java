package com.gaobug.auth.handler;

import com.gaobug.api.dto.PlatformMobileRegisterDTO;
import com.gaobug.api.dto.PlatformRegister;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.dto.PlatformLoginForm;
import com.gaobug.auth.enums.LoginType;
import com.gaobug.cache.constant.CaptchaCode;
import com.gaobug.cache.util.RedisUtil;
import com.gaobug.response.ResponseWrapped;
import com.gaobug.response.enums.ResultEnum;
import com.gaobug.response.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @Override
    public boolean supports(Integer loginType) {
        return Objects.equals(loginType, LoginType.mobile.getType());
    }

    @Override
    public PlatformUserVO getLoginUser(PlatformLoginForm form) {
        if (!StringUtils.hasText(form.getMobile()) || !StringUtils.hasText(form.getCode())) {
            throw new BusinessException(ResultEnum.ERROR_CAPTCHA_OVERDUE);
        }
        String code = RedisUtil.get(CaptchaCode.MOBILE_CODE_LOGIN + form.getMobile());
        if (!Objects.equals(form.getCode(), code)) {
            throw new BusinessException(ResultEnum.ERROR_CAPTCHA_FAIL);
        }
        PlatformUserVO userByMobile = platformUserClient.getUserByMobile(form.getMobile());
        if (null == userByMobile) {
            PlatformRegister platformRegister = new PlatformMobileRegisterDTO();
            platformRegister.setMobile(form.getMobile());
            ResponseWrapped<PlatformUserVO> responseWrapped = platformUserClient.registerUser(platformRegister);
            if (Objects.equals(responseWrapped.getCode(), HttpStatus.OK.value())) {
                throw new BusinessException(responseWrapped.getMsg());
            }
            return responseWrapped.getData();
        }
        return platformUserClient.getUserByMobile(form.getMobile());
    }
}
