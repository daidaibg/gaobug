package com.imantou.auth.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.generator.Generator;
import com.imantou.advice.enums.ResultEnum;
import com.imantou.advice.exception.BusinessException;
import com.imantou.advice.response.ResponseWrapped;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.vo.AuthTokenVO;
import com.imantou.auth.vo.PlatformUserContextVO;
import com.imantou.cache.util.RedisUtil;
import com.imantou.exception.utils.EncryptUtils;
import com.imantou.api.user.PlatformUserService;
import com.imantou.api.vo.PlatformUserVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 登录服务
 *
 * @author gaobug
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    //    @Resource
//    private SystemUserService systemUserService;
    @Resource
    private PlatformUserService platformUserService;
    @Resource
    private Generator<Long> snowflakeGenerator;


    @Override
    public AuthTokenVO platformUserLogin(LoginForm form) {
        ResponseWrapped<PlatformUserVO> responseWrapped = platformUserService.getUserByName(form.getUsername());
        PlatformUserVO platformUser = responseWrapped.getData();
        Assert.notNull(platformUser, ResultEnum.ERROR_USER_PASSWORD.getMsg());
        if (!platformUser.getPassword().equals(EncryptUtils.sha256(form.getPassword(), platformUser.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        // 缓存用户信息
        PlatformUserContextVO userContext = new PlatformUserContextVO();
        BeanUtils.copyProperties(platformUser, userContext);
        String token = snowflakeGenerator.next().toString();
        RedisUtil.set(token, userContext, 43200L);
        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
    }

    @Override
    public AuthTokenVO systemLogin(String randomId, LoginForm form) {
//        ResponseWrapped<SystemUserVO> responseWrapped = systemUserService.getUserByName(form.getUsername());
//        SystemUserVO user = responseWrapped.getData();
//        //账号不存在、密码错误
//        if (user == null || !user.getPassword().equals(EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
//            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
//        }
//        // 缓存用户信息
//        UserContext userContext = new UserContext();
//        BeanUtils.copyProperties(user, userContext);
//        String token = snowflakeGenerator.next().toString();
//        RedisUtil.set(token, userContext, 43200L);
//        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
        return null;
    }

    @Override
    public PlatformUserContextVO getPlatformLoginUserInfo() {
        return null;
    }
}
