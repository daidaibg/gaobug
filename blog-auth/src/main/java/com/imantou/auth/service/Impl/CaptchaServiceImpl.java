package com.imantou.auth.service.Impl;

import cn.hutool.core.lang.generator.Generator;
import com.imantou.advice.enums.ResultEnum;
import com.imantou.advice.exception.CaptchaException;
import com.imantou.auth.service.CaptchaService;
import com.imantou.cache.util.RedisUtil;
import com.imantou.base.utils.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 登录服务
 *
 * @author gaobug
 */
@Slf4j
@Service
public class CaptchaServiceImpl implements CaptchaService {
    /**
     * 滑动验证码允许偏差值
     */
    private static final Integer DEVIATION = 15;

    @Resource
    private Generator<Long> snowflakeGenerator;

    @Override
    public Map<String, Object> getCaptcha() {
        Map<String, Object> resultMap = ImageUtil.getCaptcha();
        String randomId = snowflakeGenerator.next().toString();
        resultMap.put("randomId", randomId);
        RedisUtil.set(randomId, resultMap.get("xWidth"), 100L);
        resultMap.remove("xWidth");
        return resultMap;
    }

    @Override
    public void verifyCaptcha(String randomId, Integer captchaCode) {
        if (null == captchaCode) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        final int captcha = RedisUtil.get(randomId);
        // 验证滑动验证码滑动距离 偏差值允许在15
        if (captcha - DEVIATION >= captchaCode || captcha + DEVIATION <= captchaCode) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
    }
}
