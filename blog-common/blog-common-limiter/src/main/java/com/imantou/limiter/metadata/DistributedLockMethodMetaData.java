package com.gaobug.limiter.metadata;

import com.gaobug.limiter.DistributedLock;
import com.gaobug.base.metadata.AbstractMethodMetaData;
import com.gaobug.utils.MethodUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Supplier;


/**
 * @author hzq
 */
public class DistributedLockMethodMetaData extends AbstractMethodMetaData {

    /**
     * 分布式所用到的注解
     */
    private final DistributedLock distributedLock;

    public DistributedLockMethodMetaData(Method method, String[] parameterNames, Supplier<Object[]> argsSupplier,
                                         DistributedLock distributedLock) {
        super(method, parameterNames, argsSupplier, MethodUtils.getClassMethodName(method));
        Assert.notNull(argsSupplier, "'distributedLockAnnotation' cannot be null");
        this.distributedLock = distributedLock;
    }

    @Override
    public String[] getKeys() {
        return distributedLock.keys();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Annotation> T getAnnotation() {
        return (T) distributedLock;
    }
}
