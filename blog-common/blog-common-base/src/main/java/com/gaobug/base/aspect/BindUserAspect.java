package com.gaobug.base.aspect;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gaobug.base.context.JwtContext;
import com.gaobug.response.constant.RequestHeader;
import com.gaobug.utils.HttpContextUtils;
import com.gaobug.utils.JwtUtils;
import com.gaobug.utils.RequestUtils;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录用户信息绑定拦截切面处理类
 *
 * @author hzq
 */
@Aspect
@Component
public class BindUserAspect {

    @Pointcut("@annotation(com.gaobug.base.annotation.BindUser)")
    public void bindUserLogCut() {
    }

    /**
     * 登录用户信息绑定解切面
     */
    @Before("bindUserLogCut()")
    public void doBefore() {
        HttpServletRequest request = RequestUtils.getRequest();
        Assert.notNull(request, "HttpServletRequest can't be null");
        final String jwtUserJsonToken = HttpContextUtils.getHeaderOrParameter(request, RequestHeader.AUTH_JWT_HEADER);
        DecodedJWT decodedJWT = JwtUtils.getDecodedJWT(jwtUserJsonToken);
        JwtContext.putDecodedJWT(decodedJWT);
    }

    @AfterReturning("bindUserLogCut()")
    public void doAfter() {
        JwtContext.remove();
    }

    @AfterThrowing(pointcut = "bindUserLogCut()")
    public void doAfterThrowing() {
        JwtContext.remove();
    }
}
