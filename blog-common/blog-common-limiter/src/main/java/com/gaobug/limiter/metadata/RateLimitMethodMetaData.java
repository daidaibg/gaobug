package com.gaobug.limiter.metadata;


import com.gaobug.base.metadata.AbstractMethodMetaData;
import com.gaobug.utils.HttpContextUtils;
import com.gaobug.utils.MethodUtils;
import com.gaobug.limiter.RateLimit;
import com.gaobug.utils.RequestUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.SortedMap;
import java.util.function.Supplier;


/**
 * 限流注解meta
 *
 * @author hzq
 */
public class RateLimitMethodMetaData extends AbstractMethodMetaData {
    /**
     * 速率限制所用到的注解
     */
    private final RateLimit rateLimit;

    private SortedMap<String, String> sortedArgsMap;

    public RateLimitMethodMetaData(Method method, String[] parameterNames, Supplier<Object[]> argsSupplier,
                                   RateLimit rateLimit) {
        super(method, parameterNames, argsSupplier, MethodUtils.getClassMethodName(method));
        Assert.notNull(argsSupplier, "'RateLimitAnnotation' cannot be null");
        this.rateLimit = rateLimit;
    }

    @Override
    public String[] getKeys() {
        return rateLimit.keys();
    }

    @Override
    public SortedMap<String, String> getArgsMap() {
        if (ObjectUtils.isEmpty(sortedArgsMap)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(sortedArgsMap)) {
                    sortedArgsMap = HttpContextUtils.getRequestSortedParamsMap(RequestUtils.getRequest());
                }
            }
        }
        return sortedArgsMap;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T extends Annotation> T getAnnotation() {
        return (T) rateLimit;
    }
}
