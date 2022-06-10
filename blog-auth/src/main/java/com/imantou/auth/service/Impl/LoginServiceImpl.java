package com.imantou.auth.service.Impl;

import cn.hutool.core.date.DateUtil;
import com.imantou.api.user.SystemUserClient;
import com.imantou.api.vo.PlatformUserVO;
import com.imantou.api.vo.SystemUserVO;
import com.imantou.auth.dto.LoginForm;
import com.imantou.auth.dto.PlatformLoginForm;
import com.imantou.auth.handler.LoginHandler;
import com.imantou.auth.service.LoginService;
import com.imantou.auth.vo.AuthTokenVO;
import com.imantou.base.utils.SnowflakeUtils;
import com.imantou.cache.constant.AuthToken;
import com.imantou.cache.context.PlatformUserContext;
import com.imantou.cache.util.RedisUtil;
import com.imantou.response.enums.ResultEnum;
import com.imantou.response.exception.BusinessException;
import com.imantou.response.exception.NotContentException;
import com.imantou.utils.EncryptUtils;
import com.imantou.utils.UserContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
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
    private List<LoginHandler> loginHandlers;

    @Override
    public AuthTokenVO platformUserLogin(PlatformLoginForm form) {
        String token = loginHandlers.stream()
                .filter(loginHandler -> loginHandler.supports(form.getLoginType()))
                .findFirst()
                .map(loginHandler -> {
                    PlatformUserVO user = loginHandler.getLoginUser(form);
                    PlatformUserContext userContext = new PlatformUserContext();
                    BeanUtils.copyProperties(user, userContext);
                    String snowflakeToken = SnowflakeUtils.nextStr("platform");
                    // 缓存用户登录信息
                    RedisUtil.set(AuthToken.AUTH_TOKEN_BUCKET + snowflakeToken, userContext, 43200000L);
                    return snowflakeToken;
                }).orElseThrow(() -> new BusinessException("暂不支持当前登录方式"));
        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
    }

    @Override
    public AuthTokenVO systemLogin(String randomId, LoginForm form) {
        SystemUserVO user = systemUserClient.getUserByName(form.getUsername());
        //账号不存在、密码错误
        if (!Objects.equals(user.getPassword(), EncryptUtils.sha256(form.getPassword(), user.getSalt()))) {
            throw new BusinessException(ResultEnum.ERROR_USER_PASSWORD);
        }

        String token = SnowflakeUtils.nextStr();

        return new AuthTokenVO(token, DateUtil.offsetHour(new Date(), 12));
    }

    @Override
    public PlatformUserContext getPlatformLoginUserInfo() throws ExecutionException, InterruptedException {
        //获取用户信息
        final String authToken = UserContextUtils.getAuthToken();
        if (!StringUtils.hasText(authToken)) {
            throw new NotContentException();
        }
        PlatformUserContext userContext = RedisUtil.get(AuthToken.AUTH_TOKEN_BUCKET + authToken);
        if (null == userContext) {
            throw new NotContentException();
        }
        CompletableFuture<PlatformUserContext> userAllOfFuture = CompletableFuture.supplyAsync(() -> {
            // 阅读数
            CompletableFuture<Void> readFuture = CompletableFuture.runAsync(() -> {
                userContext.setReadToday("5");
                userContext.setReadYesterday("30");
            });
            // 点赞数
            CompletableFuture<Void> likeFuture = CompletableFuture.runAsync(() -> {
                userContext.setLikeToday("20");
                userContext.setLikeYesterday("18");
            });
            CompletableFuture<Void> myFuture = CompletableFuture.runAsync(() -> {
                userContext.setMyArticle("60");
                userContext.setMyAttention("160");
                userContext.setMyCollection("24");
            });
            try {
                CompletableFuture.allOf(readFuture, likeFuture, myFuture)
                        .thenRun(() -> userContext.setLastTime(new Date()))
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return userContext;
        });
        return userAllOfFuture.get();
    }
}
