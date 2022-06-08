package com.imantou.verify.strategy;


import com.imantou.base.metadata.MethodMetaData;
import com.imantou.utils.ParseDataUtils;
import com.imantou.verify.Verify;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 参数校验-接口方法参数校验
 *
 * @author hzq
 * @date 2021/11/22
 */
public class MethodParamVerifyStrategy extends AbstractVerifyStrategy {

    @Override
    public void doCheck(MethodMetaData methodMetadata) {
        Object[] args = methodMetadata.getArgs();
        //得到拦截的方法
        Method method = methodMetadata.getMethod();
        final String[] parameterNames = methodMetadata.getParameterNames();
        //获取方法参数注解，返回二维数组是因为某些参数可能存在多个注解
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (!ObjectUtils.isEmpty(parameterAnnotations)) {
            this.doCheck(args, parameterNames,parameterTypes , parameterAnnotations);
        }
    }

    /**
     * @param args             参数数组
     * @param paramNames       方法参数名数组
     * @param paramTypes       参数类型数组
     * @param paramAnnotations 参数注解数组
     */
    private void doCheck(Object[] args, String[] paramNames, Class<?>[] paramTypes, Annotation[][] paramAnnotations) {
        for (int i = 0; i < paramAnnotations.length; i++) {
            for (int j = 0; j < paramAnnotations[i].length; j++) {
                Annotation objectAnnotation = paramAnnotations[i][j];
                if (objectAnnotation instanceof Verify) {
                    Verify verify = (Verify) objectAnnotation;
                    String explain = StringUtils.hasLength(verify.value()) ? verify.value() : paramNames[i];
                    // 执行校验
                    this.doCheck(verify, explain, verify.errorMsg(), args[i]);
                    // 处理有默认值的参数
                    if (StringUtils.hasLength(verify.defaultValue()) && ObjectUtils.isEmpty(args[i])) {
                        args[i] = ParseDataUtils.parseValue(paramTypes[i], verify.defaultValue());
                    } else {
                        // 替换枚举值
                        if (!ObjectUtils.isEmpty(args[i]) && !ObjectUtils.isEmpty(verify.replace())) {
                            String replaceAfter = this.doReplace(verify.replace(), args[i]);
                            args[i] = ParseDataUtils.parseValue(paramTypes[i], replaceAfter);
                        }
                    }
                }
            }
        }
    }
}
