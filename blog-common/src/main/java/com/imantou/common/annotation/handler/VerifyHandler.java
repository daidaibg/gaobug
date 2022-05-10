package com.imantou.common.annotation.handler;

import com.imantou.common.annotation.metadata.MethodMetaData;
import com.imantou.common.annotation.strategy.IVerifyStrategy;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

/**
 * 参数校验handler
 *
 * @author hzq
 * @date 2021/11/24
 */
@AllArgsConstructor
public class VerifyHandler implements IProceedHandler {

    private List<IVerifyStrategy> verifyStrategyList;

    @Override
    public Object proceed(ProceedingJoinPoint pjp, MethodMetaData methodMetadata) throws Throwable {
        for (IVerifyStrategy verify : verifyStrategyList) {
            verify.doCheck(methodMetadata);
        }
       return pjp.proceed();
    }
}
