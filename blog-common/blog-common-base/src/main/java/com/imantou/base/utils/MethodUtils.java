package com.imantou.base.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


/**
 * @author hzq
 */
public class MethodUtils {

    /**
     * 获取执行类方法名
     *
     * @param method 执行方法
     * @return 类名+方法名
     */
    public static String getClassMethodName(Method method) {
        return String.format("%s.%s", method.getDeclaringClass().getName(), method.getName());
    }

    /**
     * 获取方法指定注解信息
     *
     * @param method          {@link Method}
     * @param annotationClass 注解类
     * @param <T>             注解类
     * @return 指定注解信息
     */
    public static <T extends Annotation> T getAnnotation(Method method, Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }
}
