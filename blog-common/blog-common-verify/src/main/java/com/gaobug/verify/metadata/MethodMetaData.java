package com.gaobug.verify.metadata;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.SortedMap;

public interface MethodMetaData {

    /**
     * 获得当前运行时方法名
     * {@link Method}
     *
     * @return 运行时方法名
     */
    String getClassMethodName();

    /**
     * 获得当前运行时方法名
     *
     * @return 运行时方法
     */
    Method getMethod();

    /**
     * 获得当前运行时方法参数
     *
     * @return 参数名称数组
     */
    String[] getParameterNames();

    /**
     * 获得注解上的方法参数
     *
     * @return 参数名称数组
     */
    default String[] getKeys() {
        return null;
    }

    /**
     * 获得运行时方法的参数
     *
     * @return 参数数组
     */
    Object[] getArgs();

    /**
     * 获得运行时方法的参数
     *
     * @return 参数数组
     * @throws IOException IO异常
     */
    default SortedMap<String, String> getArgsMap(){
        return null;
    }

    /**
     * 获得当前运行的参数
     *
     * @param <T> 当前运行的注解泛型
     * @return 获取到的泛型的值
     */
    default <T extends Annotation> T getAnnotation() {
        return null;
    }
}
