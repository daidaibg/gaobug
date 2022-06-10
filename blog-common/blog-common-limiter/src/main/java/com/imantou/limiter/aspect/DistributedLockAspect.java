package com.gaobug.limiter.aspect;

import com.gaobug.base.handler.IProceedHandler;
import com.gaobug.limiter.metadata.DistributedLockMethodMetaData;
import com.gaobug.limiter.DistributedLock;
import com.gaobug.base.metadata.MethodMetaData;

import com.gaobug.utils.MethodUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 分布式锁拦截切面处理类
 *
 * @author hzq
 */
@Aspect
@RequiredArgsConstructor
public class DistributedLockAspect {
    private final Map<String, DistributedLock> REDIS_LOCK_MAP = new ConcurrentHashMap<>();
    private final IProceedHandler redisLockHandler;

    /**
     * 分布式锁注解切面
     *
     * @param pjp {@link ProceedingJoinPoint}
     * @return {@link Object}
     * @throws Throwable 限流异常
     */
    @Around("@annotation(com.gaobug.limiter.DistributedLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodMetaData methodMetadata = this.buildMethodMetadata(pjp);
        return redisLockHandler.proceed(pjp, methodMetadata);
    }

    private MethodMetaData buildMethodMetadata(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        final String classMethodName = MethodUtils.getClassMethodName(method);
        final DistributedLock distributedLock = this.getDistributedLock(method, classMethodName);
        return new DistributedLockMethodMetaData(method, signature.getParameterNames(), joinPoint::getArgs, distributedLock);
    }

    /**
     * 获取分布式锁注解，缓存反射信息
     *
     * @param method          执行方法
     * @param classMethodName 执行类方法名
     * @return 方法对应的注解源信息，如果有，直接返回，如果无，获取放入缓存。
     */
    public DistributedLock getDistributedLock(Method method, String classMethodName) {
        return REDIS_LOCK_MAP.computeIfAbsent(classMethodName, k -> method.getAnnotation(DistributedLock.class));
    }
}
