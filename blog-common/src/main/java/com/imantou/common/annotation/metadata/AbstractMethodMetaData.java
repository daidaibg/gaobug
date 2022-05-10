package com.imantou.common.annotation.metadata;

import com.imantou.common.utils.MethodUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * @author hzq
 */
public abstract class AbstractMethodMetaData implements MethodMetaData{
    /**
     * 方法
     */
    protected final Method method;
    /**
     * 当前运行时方法中的参数名
     */
    protected final String[] parameterNames;
    /**
     * 当前运行时方法中的参数
     */
    protected final Supplier<Object[]> argsSupplier;
    /**
     * 当前方法的全称
     * 使用{@link MethodUtils#getClassMethodName(Method)}方法获得
     */
    protected final String classMethodName;

    public AbstractMethodMetaData(Method method, String[] parameterNames, Supplier<Object[]> argsSupplier, String classMethodName) {
        Assert.notNull(method, "'method' cannot be null");
        Assert.notNull(argsSupplier, "'argsSupplier' cannot be null");
        this.method = method;
        this.parameterNames = parameterNames;
        this.argsSupplier = argsSupplier;
        this.classMethodName = classMethodName;
    }

    @Override
    public String getClassMethodName() {
        return classMethodName;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public String[] getParameterNames() {
        return this.parameterNames;
    }

    @Override
    public Object[] getArgs() {
        return argsSupplier.get();
    }
}
