package com.gaobug.verify.metadata;

import com.gaobug.verify.utils.MethodUtils;

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
