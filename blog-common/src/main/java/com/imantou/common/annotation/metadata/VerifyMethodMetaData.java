package com.imantou.common.annotation.metadata;

import com.imantou.common.utils.MethodUtils;

import java.lang.reflect.Method;
import java.util.function.Supplier;


/**
 * @author hzq
 */
public class VerifyMethodMetaData extends AbstractMethodMetaData {

    public VerifyMethodMetaData(Method method, String[] parameterNames, Supplier<Object[]> argsSupplier) {
        super(method, parameterNames, argsSupplier, MethodUtils.getClassMethodName(method));
    }

}
