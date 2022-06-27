package com.gaobug.verify.handler;

import com.gaobug.verify.metadata.MethodMetaData;
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