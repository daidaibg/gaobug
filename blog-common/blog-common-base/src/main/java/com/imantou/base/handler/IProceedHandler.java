package com.imantou.base.handler;


import com.imantou.base.metadata.MethodMetaData;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author hzq
 */
public interface IProceedHandler {
    /**
     * 继续执行
     *
     * @param pjp            {@link ProceedingJoinPoint}
     * @param methodMetadata {@link MethodMetaData}
     * @return Object
     * @throws Throwable
     */
    Object proceed(ProceedingJoinPoint pjp, MethodMetaData methodMetadata) throws Throwable;
}
