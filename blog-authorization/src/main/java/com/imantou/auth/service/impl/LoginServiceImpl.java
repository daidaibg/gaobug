package com.imantou.auth.service.impl;

import cn.hutool.core.lang.generator.Generator;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.service.SysUserService;
import com.imantou.auth.vo.JwtVO;
import com.imantou.common.domain.SysUser;
import com.imantou.common.enums.ResultEnum;
import com.imantou.common.exception.CaptchaException;
import com.imantou.common.exception.BusinessException;
import com.imantou.common.utils.ImageUtil;
import com.imantou.common.utils.RedisHelper;
import com.imantou.common.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private SysUserService userService;
    @Resource
    private RedisHelper redisHelper;
    @Resource
    private Generator<Long> snowflakeGenerator;

    @Override
    public Map<String, Object> getCaptcha(String randomId) {
        Map<String, Object> resultMap = ImageUtil.createImage(new File("/opt/blog/image/captcha/captcha.jpg"));
        resultMap.put("randomId", randomId);
        redisHelper.set(randomId, resultMap.get("xWidth"), 20000L, TimeUnit.MILLISECONDS);
        resultMap.remove("xWidth");
        return resultMap;
    }

    @Override
    public JwtVO systemLogin(LoginForm form, String randomId) {
        if (null == form.getCaptcha()) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        Object objCaptcha = redisHelper.get(randomId);
        if (Objects.isNull(objCaptcha)) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_EXPIRE);
        }
        final int captcha = Integer.parseInt(objCaptcha.toString());
        if (captcha - 15 >= form.getCaptcha() || captcha + 15 <= form.getCaptcha()) {
            throw new CaptchaException(ResultEnum.LOGON_CAPTCHA_FAILED);
        }
        SysUser user = userService.getUserByName(form.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(ShiroUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        Long token = snowflakeGenerator.next();
        redisHelper.set(token.toString(), user);
        log.info("user:{}", user);
        JwtVO jwtVO = new JwtVO();
        jwtVO.setToken(token);
        return jwtVO;
    }

}
