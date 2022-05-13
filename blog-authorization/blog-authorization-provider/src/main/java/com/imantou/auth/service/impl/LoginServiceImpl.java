package com.imantou.auth.service.impl;

import cn.hutool.core.lang.generator.Generator;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.service.SysUserService;
import com.imantou.common.domain.AuthTokenVO;
import com.imantou.common.domain.SysUser;
import com.imantou.common.domain.UserContext;
import com.imantou.common.enums.ResultEnum;
import com.imantou.common.exception.BusinessException;
import com.imantou.common.exception.CaptchaException;
import com.imantou.common.utils.EncryptUtils;
import com.imantou.common.utils.ImageUtil;
import com.imantou.common.utils.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    /**
     * 滑动验证码允许偏差值
     */
    private static final Integer DEVIATION = 15;
    @Resource
    private SysUserService userService;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private Generator<Long> snowflakeGenerator;

    @Override
    public Map<String, Object> getCaptcha(String randomId) {
        Map<String, Object> resultMap = ImageUtil.getCaptcha();
        resultMap.put("randomId", randomId);
        redisHelper.set(randomId, resultMap.get("xWidth"), 20000L, TimeUnit.MILLISECONDS);
        resultMap.remove("xWidth");
        return resultMap;
    }

    @Override
    public AuthTokenVO systemLogin(String randomId, LoginForm form) {
        if (null == form.getCaptcha()) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        Object objCaptcha = redisHelper.get(randomId);
        if (Objects.isNull(objCaptcha)) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_EXPIRE);
        }
        // 验证滑动验证码滑动距离 偏差值允许在15

        final int captcha = Integer.parseInt(objCaptcha.toString());
        if (captcha - DEVIATION >= form.getCaptcha() || captcha + DEVIATION <= form.getCaptcha()) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        SysUser user = userService.getUserByName(form.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        // 缓存用户信息
        UserContext userContext = new UserContext();
        BeanUtils.copyProperties(user, userContext);
        String token = snowflakeGenerator.next().toString();
        redisHelper.set(token, userContext);
        return new AuthTokenVO(token);
    }

}
