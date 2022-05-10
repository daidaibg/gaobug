package com.imantou.common.aspect;

import com.imantou.common.annotation.RateLimit;
import com.imantou.common.annotation.handler.IProceedHandler;
import com.imantou.common.utils.MethodUtils;
import com.imantou.common.annotation.metadata.MethodMetaData;
import com.imantou.common.annotation.metadata.RateLimitMethodMetaData;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author hzq
 */
@Aspect
@AllArgsConstructor
public class RateLimitAspect {
    /**
     * 缓存方法上的源注解信息。减少反射的开销
     */
    private static final Map<String, RateLimit> RATE_LIMIT_MAP = new ConcurrentHashMap<>();
    private final IProceedHandler rateLimitHandler;

    /**
     * 限流注解切面
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link Object}
     * @throws Throwable 限流异常
     */
    @Around("@annotation(com.imantou.common.annotation.RateLimit)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodMetaData methodMetadata = this.buildMethodMetadata(pjp);
        return rateLimitHandler.proceed(pjp, methodMetadata);
    }

    private MethodMetaData buildMethodMetadata(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        String classMethodName = MethodUtils.getClassMethodName(method);
        RateLimit rateLimit = this.getRateLimit(method, classMethodName);
        return new RateLimitMethodMetaData(method, parameterNames, joinPoint::getArgs, rateLimit);
    }

    /**
     * 获取执行速率限制注解，缓存反射信息
     *
     * @param method          执行方法
     * @param classMethodName 执行类方法名
     * @return 方法对应的注解源信息，如果有，直接返回，如果无，获取放入缓存。
     */
    public RateLimit getRateLimit(Method method, String classMethodName) {
        return RATE_LIMIT_MAP.computeIfAbsent(classMethodName, k -> method.getAnnotation(RateLimit.class));
    }
}
