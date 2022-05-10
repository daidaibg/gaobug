package com.imantou.common.annotation;

import com.imantou.common.annotation.metadata.MethodMetaData;
import com.imantou.common.annotation.strategy.DefaultKeyGenerateStrategy;
import com.imantou.common.annotation.strategy.IKeyGenerateStrategy;
import com.imantou.common.annotation.strategy.Strategy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @author hzq
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RateLimit {

    /**
     * 唯一标示，支持SpEL表达式
     */
    String key() default "";

    /**
     * 限定阈值
     * <p>
     * 时间间隔 interval 范围内超过该数量会触发锁
     */
    long count();

    /**
     * 时间间隔，默认 20秒
     * <p>
     * 例如 5s 五秒，6m 六分钟，7h 七小时，8d 八天
     */
    String interval() default "20s";

    /**
     * 限制策略
     * 默认的key生成策略{@link DefaultKeyGenerateStrategy}作为前缀
     * 需要实现{@link IKeyGenerateStrategy}接口注入到spring中
     * 通过{@link IKeyGenerateStrategy#getKey(MethodMetaData, String)}返回的key将会作为后缀加在默认得到的key后
     */
    Strategy[] strategy() default Strategy.DEFAULT;

    /**
     * parameter限制策略时需填写限制参数的属性值
     */
    String[] keys() default {};

    /**
     * 提示消息，非必须
     */
    String message() default "";

    /**
     * 是否抛出异常提示
     */
    boolean throwError() default true;
}
