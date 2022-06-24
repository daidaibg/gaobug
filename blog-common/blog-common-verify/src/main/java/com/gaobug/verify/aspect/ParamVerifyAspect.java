package com.gaobug.verify.aspect;

import com.gaobug.verify.handler.IProceedHandler;
import com.gaobug.verify.metadata.MethodMetaData;
import com.gaobug.verify.metadata.VerifyMethodMetaData;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

/**
 * 实体参数校验注解拦截切面
 *
 * @author hzq
 * @date 2021/11/19
 */
@Aspect
@AllArgsConstructor
public class ParamVerifyAspect implements Ordered {
    private final IProceedHandler verifyHandler;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void paramVerifyCut() {
    }

    @Around("paramVerifyCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 接口方法参数校验
        MethodMetaData methodMetadata = this.buildMethodMetadata(joinPoint);
        return verifyHandler.proceed(joinPoint, methodMetadata);
    }

    private MethodMetaData buildMethodMetadata(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String[] parameterNames = signature.getParameterNames();
        return new VerifyMethodMetaData(method, parameterNames, joinPoint::getArgs);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}