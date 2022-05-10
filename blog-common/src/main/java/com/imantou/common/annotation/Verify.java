package com.imantou.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验
 *
 * @author hzq
 * @date 2021/11/22
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {
    /**
     * 字段说明
     */
    String value() default "";

    /**
     * 自定义错误提示消息
     * 非必填
     */
    String errorMsg() default "";

    /**
     * 填充默认值
     * <p>
     * 字段非必填时填充默认值
     */
    String defaultValue() default "";

    /**
     * 正则校验表达式
     */
    String regex() default "";

    /**
     * 枚举值替换 下划线分隔
     */
    String[] replace() default {};

    /**
     * 校验字段是可否必填
     */
    boolean required() default false;

    /**
     * 字段最大长度
     */
    int maxLength() default -1;

    /**
     * 字段最小长度
     */
    int minLength() default -1;

    /**
     * 是否嵌套对象
     * <p>
     * bean对象内嵌套校验对象时需要设为true
     * 数据结构内嵌套校验对象时需要设为true
     */
    boolean nested() default false;
}