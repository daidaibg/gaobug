package com.gaobug.auth.service.Impl;

import com.gaobug.api.dto.UserRegisterDTO;
import com.gaobug.api.user.PlatformUserClient;
import com.gaobug.api.vo.PlatformUserVO;
import com.gaobug.auth.service.RegisterService;
import com.gaobug.base.utils.SnowflakeUtils;
import com.gaobug.cache.constant.CaptchaCode;
import com.gaobug.cache.util.RedisUtil;
import com.gaobug.response.enums.ResultEnum;
import com.gaobug.response.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 注册服务
 *
 * @author gaobug
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private PlatformUserClient platformUserClient;

    @Override
    public PlatformUserVO registerUserByEmail(UserRegisterDTO platformRegister) {
        Assert.hasLength(platformRegister.getEmail(), "邮箱不能为空");
        Assert.hasLength(platformRegister.getPassword(), "密码不能为空");
        Assert.hasLength(platformRegister.getCode(), "验证码不能为空");
        String code = RedisUtil.get(CaptchaCode.CAPTCHA_EMAIL_REGISTER + platformRegister.getEmail());
        if (!Objects.equals(platformRegister.getCode(), code)) {
            throw new BusinessException(ResultEnum.ERROR_CAPTCHA_FAIL);
        }
        PlatformUserVO userRegisterDTO = getUserRegisterDTO(platformRegister, "email" + SnowflakeUtils.next());
        userRegisterDTO.setPassword(platformRegister.getPassword());
        userRegisterDTO.setSalt(null);
        return userRegisterDTO;
    }

    @Override
    public PlatformUserVO registerUserByMobile(UserRegisterDTO platformRegister) {
        return getUserRegisterDTO(platformRegister, "mobile" + SnowflakeUtils.next());
    }

    private PlatformUserVO getUserRegisterDTO(UserRegisterDTO platformRegister, String name) {
        platformRegister.setAccount(name);
        platformRegister.setNickName(name);
        PlatformUserVO platformUserVO = platformUserClient.saveRegisterUser(platformRegister);
        Assert.notNull(platformUserVO, "系统异常，注册失败");
        return platformUserVO;
    }
}
