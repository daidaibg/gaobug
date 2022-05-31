package com.imantou.auth.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.imantou.advice.enums.ResultEnum;
import com.imantou.advice.exception.BusinessException;
import com.imantou.api.user.PlatformUserClient;
import com.imantou.api.user.SystemUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.api.vo.SystemUserVO;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.vo.AuthTokenVO;
import com.imantou.auth.vo.PlatformUserContextVO;
import com.imantou.auth.vo.UserContext;
import com.imantou.base.utils.EncryptUtils;
import com.imantou.base.utils.SnowflakeUtils;
import com.imantou.base.utils.UserContextUtils;
import com.imantou.cache.constant.RedisToken;
import com.imantou.cache.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 登录服务
 *
 * @author gaobug
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private SystemUserClient systemUserClient;
    @Resource
    private PlatformUserClient platformUserClient;


    @Override
    public AuthTokenVO platformUserLogin(LoginForm form) {
        PlatformUserVO user = platformUserClient.getUserByName(form.getUsername());
        if (!Objects.equals(user.getPassword(), EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        // 缓存用户信息
        PlatformUserContextVO userContext = new PlatformUserContextVO();
        BeanUtils.copyProperties(user, userContext);
        String token = SnowflakeUtils.nextStr();
        RedisUtil.set(RedisToken.PLATFORM_AUTH_BUCKET + token, userContext, 43200L);
        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
    }

    @Override
    public AuthTokenVO systemLogin(String randomId, LoginForm form) {
        SystemUserVO user = systemUserClient.getUserByName(form.getUsername());
        //账号不存在、密码错误
        if (!Objects.equals(user.getPassword(), EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }
        // 缓存用户信息
        UserContext userContext = new UserContext();
        BeanUtils.copyProperties(user, userContext);
        String token = SnowflakeUtils.nextStr();
        RedisUtil.set(token, userContext, 43200L);
        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
    }

    @Override
    public PlatformUserContextVO getPlatformLoginUserInfo() throws ExecutionException, InterruptedException {
        String authToken = UserContextUtils.getAuthToken();
//        PlatformUserContextVO userContext = RedisUtil.get(RedisToken.PLATFORM_AUTH_BUCKET + authToken);
//        if (userContext == null) {
//            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
//        }
        //获取用户信息
        CompletableFuture<PlatformUserContextVO> userContextFuture = CompletableFuture
                .supplyAsync(() -> RedisUtil.get(RedisToken.PLATFORM_AUTH_BUCKET + authToken));
        if (userContextFuture.get() == null) {
            throw new BusinessException(ResultEnum.LOGON_TOKEN_EXPIRE);
        }
        CompletableFuture<PlatformUserContextVO> userAllOfFuture = userContextFuture.thenApply(userContextResult -> {
            // 阅读数
            CompletableFuture<Void> readFuture = CompletableFuture.runAsync(() -> {
                userContextResult.setReadToday("5");
                userContextResult.setReadYesterday("30");
            });
            // 点赞数
            CompletableFuture<Void> likeFuture = CompletableFuture.runAsync(() -> {
                userContextResult.setLikeToday("20");
                userContextResult.setLikeYesterday("18");
            });
            CompletableFuture<Void> myFuture = CompletableFuture.runAsync(() -> {
                userContextResult.setMyArticle("60");
                userContextResult.setMyAttention("160");
                userContextResult.setMyCollection("24");
            });
            try {
                CompletableFuture.allOf(readFuture, likeFuture, myFuture)
                        .thenRun(() -> userContextResult.setLastTime(new Date()))
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return userContextResult;
        });
        return userAllOfFuture.get();
    }
}