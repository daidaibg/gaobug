package com.imantou.auth.handler;

import com.imantou.api.dto.PlatformMobileRegisterDTO;
import com.imantou.api.dto.PlatformRegister;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.auth.dto.PlatformLoginForm;
import com.imantou.auth.enums.LoginType;
import com.imantou.cache.constant.CaptchaCode;
import com.imantou.cache.util.RedisUtil;
import com.imantou.response.ResponseWrapped;
import com.imantou.response.enums.ResultEnum;
import com.imantou.response.exception.BusinessException;
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
            if (!responseWrapped.isSuccess()) {
                throw new BusinessException(responseWrapped.getMsg());
            }
            return responseWrapped.getData();
        }
        return platformUserClient.getUserByMobile(form.getMobile());
    }
}
